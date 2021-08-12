package com.ssafy.commb.jwt;

import com.ssafy.commb.service.RedisServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private RedisServiceImpl redisService;

    @Value("${security.expire.accesstoken}")
    private Long ACCESS_TOKEN_EXP_TIME;

    @Value("${security.expire.refreshtoken}")
    private Long REFRESH_TOKEN_EXP_TIME;

    @Value("${security.secretkey}")
    private String SECRET_KEY;

    /**
     * 로그인 후 토큰 발행
     * @param userId : 유저 ID
     * @return : Access, Refresh Token
     */
    @Transactional
    public Map<String, Object> createToken(int userId){
        String accessToken = createAccessToken(String.valueOf(userId), ACCESS_TOKEN_EXP_TIME, SECRET_KEY);
        String refreshToken = createRefreshToken(REFRESH_TOKEN_EXP_TIME, accessToken);

        List<String> userIdAccToken = new ArrayList<>();
        userIdAccToken.add(String.valueOf(userId));
        userIdAccToken.add(accessToken);
        save(refreshToken, userIdAccToken, REFRESH_TOKEN_EXP_TIME);                                                         // RefreshToken DB에 저장

        Map<String, Object> map = new HashMap<>();
        map.put("acToken", accessToken);
        map.put("rfToken", refreshToken);

        return map;
    }

    /**
     * Access Token 발행
     * @param userId : 유저 ID
     * @param expTime : 만료시간
     * @param secretKey : 암호화 Key
     * @return : 발행 토큰
     */
    public String createAccessToken(String userId, Long expTime, String secretKey) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        Key signingKey = makeKey(secretKey);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);
        // 추가로 클레임, 헤더 등 다양한 정보를 더 넣을 수 있음
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .setExpiration(expireTime).compact();
    }

    /**
     * Refresh Token 발행
     * @param expTime : 만료시간
     * @param secretKey : 암호화 Key
     * @return : 발행 토큰
     */
    public String createRefreshToken(Long expTime, String secretKey) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        Key signingKey = makeKey(secretKey);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);

        return Jwts.builder()
                .setSubject("valid")
                .setIssuedAt(new Date())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .setExpiration(expireTime).compact();
    }

    /**
     * Signing Key 생성
     * @param secretKey : 암호화 키
     * @return : Signing Secret Key
     */
    public Key makeKey(String secretKey){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;       // HS256 암호화 알고리즘 사용
        byte[] securityByte = DatatypeConverter.parseBase64Binary(secretKey);   // String 형태의 키를 Byte 형태로 인코딩

        return new SecretKeySpec(securityByte, signatureAlgorithm.getJcaName());   // 암호화된 Key 생성
    }

    // Redis
    public void save(String token, List<String> userIdAccToken, Long expTime){
        redisService.setStringValue(token, userIdAccToken, expTime);
    }

    public String get(String token){
        return redisService.getStringValue(token);
    }

    public List<Object> find(String token){
        List<Object> userId = redisService.getListValue(token);
        return userId;
    }

    public void del(String token){
        redisService.del(token);
    }

    /**
     * 토큰 검증 & Subject 반환
     * @param token : 토큰
     * @param secretKey : 암/복호화 키 (대칭키)
     * @return : Subject
     */
    public String decodeToken(String token, String secretKey){
        // 클레임 : Payload 에 들어있는 값
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        }catch(ExpiredJwtException e1){
            e1.printStackTrace();
            return "expire";
        }catch(JwtException e2){
            e2.printStackTrace();
            return "invalid";
        }
    }

    /**
     * Refresh Token 검증 및 새 Access Token 반환 -> Access Token 을 함께 받아서 만료되었다면 Refresh Token 유효성을 검증한 후 AccessToken 반환
     * @param acToken : Access Token
     * @param rfToken : Refresh Token
     * @return : Renew Access Token
     */
    public Map<String, Object> validRefreshToken(String acToken, String rfToken){
        Map<String, Object> map = new HashMap<>();

        String acUserId = decodeToken(acToken, SECRET_KEY);
        List<Object> userIdAccToken = find(rfToken);

        if(userIdAccToken.size() < 2) {
            map.put("msg", "RefreshToken has been expired");
            map.put("status", 401);
        }
        else if(acUserId.equals(userIdAccToken.get(0))) {
            map.put("msg", "AccessToken Already Valid.");
            map.put("status", 403);
        }
        else if(acUserId.equals("expire")) {
            map.put("token", createAccessToken(String.valueOf(userIdAccToken.get(0)), ACCESS_TOKEN_EXP_TIME, SECRET_KEY));
            map.put("status", 200);
            map.put("msg", "Access Token Updated Complete");
            map.put("userId", userIdAccToken.get(0));
        }
        else {
            map.put("msg", "AccessToken Not Valid.");
            map.put("status", 403);
        }

        return map;
    }
}

