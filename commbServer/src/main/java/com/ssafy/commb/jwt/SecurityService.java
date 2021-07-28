package com.ssafy.commb.jwt;

import com.ssafy.commb.service.RedisService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Service
public class SecurityService {
    @Autowired
    private RedisService redisService;

    @Value("${security.expire.accesstoken}")
    private Long ACCESS_TOKEN_EXP_TIME;     // test : 1분             // 30분 기준

    @Value("${security.expire.refreshtoken}")
    private Long REFRESH_TOKEN_EXP_TIME;     // test : 5분          // 7일 기준

    @Value("${security.secretkey}")
    private String SECRET_KEY;   // 암/복호 키(회원 비밀번호로 사용 보통)

    // 로그인 후 토큰 발행
    @Transactional
    public Map<String, Object> createToken(int userId){
        String accessToken = createAccessToken(String.valueOf(userId), ACCESS_TOKEN_EXP_TIME, SECRET_KEY);  // Redis에 저장 & 클라이언트에 제공
        String refreshToken = createRefreshToken(REFRESH_TOKEN_EXP_TIME, accessToken);                      // Redis에 저장 & 클라이언트에 제공

        List<String> userIdAccToken = new ArrayList<>();
        userIdAccToken.add(String.valueOf(userId));
        userIdAccToken.add(accessToken);
        save(refreshToken, userIdAccToken, REFRESH_TOKEN_EXP_TIME);    // RefreshToken DB에 저장

        Map<String, Object> map = new HashMap<>();
        map.put("acToken", accessToken);
        map.put("rfToken", refreshToken);

        return map;
    }

    // AccessToken 발행
    public String createAccessToken(String userId, Long expTime, String secretKey) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        Key signingKey = makeKey(secretKey);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);
        // 추가로 클레임, 헤더 등 다양한 정보를 더 넣을 수 있음
        return Jwts.builder()
                .setSubject(userId)                          // userId & 발행일자를 담아서 암호화
                .setIssuedAt(new Date())
                .signWith(signingKey, SignatureAlgorithm.HS256)   // key, key 암호화 알고리즘 사용
                .setExpiration(expireTime).compact();       // 만료 시점 지정 & compact to String
    }

    // RefreshToken 발행
    public String createRefreshToken(Long expTime, String secretKey) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        Key signingKey = makeKey(secretKey);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);

        // 추가로 클레임, 헤더 등 다양한 정보를 더 넣을 수 있음
        return Jwts.builder()
                .setSubject("valid")
                .setIssuedAt(new Date())                     // 발행일자를 담아서 암호화
                .signWith(signingKey, SignatureAlgorithm.HS256)   // key, key 암호화 알고리즘 사용
                .setExpiration(expireTime).compact();       // 만료 시점 지정 & compact to String
    }

    // SignKey 생성
    public Key makeKey(String secretKey){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;       // HS256 암호화 알고리즘 사용
        byte[] securityByte = DatatypeConverter.parseBase64Binary(secretKey);   // String 형태의 키를 Byte 형태로 인코딩

        return new SecretKeySpec(securityByte, signatureAlgorithm.getJcaName());   // 암호화된 Key 생성
    }

    // Redis save
    public void save(String token, List<String> userIdAccToken, Long expTime){
        redisService.setStringValue(token, userIdAccToken, expTime);
    }

    // Redis select
    public List<Object> find(String token){
        List<Object> userId = redisService.getStringValue(token);
        return userId;
    }

    public void del(String token){
        redisService.del(token);
    }

    // 토큰 Validation & Get Subject
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

    // AccessToken 의 무제한 발행을 막아야한다!  // 1가지 해결 필요 : 유효하지만 만료된 Access토큰과 유효한 RefreshToken을 보냈을때 1번만 발행해야하는데 계속 발행 가능
    // RefreshToken 받아서 AccessToken 발행/RefreshToken 재발행 및 반환
    public Map<String, Object> validRefreshToken(String acToken,
                                 String rfToken){

        Map<String, Object> map = new HashMap<>();

        String acUserId = decodeToken(acToken, SECRET_KEY);    // AccessToken을 통해 userId를 추출한다. -> 만료라면 "expire" 반환
        List<Object> userIdAccToken = find(rfToken);         // redis로 부터 key(refreshToken)를 통해 user가 처음 발급했던 AccessToken = RefreshToken의 SecretKey를 가져온다.
        System.out.println(acUserId.equals(userIdAccToken.get(0)));

        if(userIdAccToken.size() < 2) {
            map.put("msg", "Not Valid Refresh Token");
            map.put("status", 401);
        }
        // RefreshToken 인증은 성공했지만 AccessToken이 만료되지 않은 경우 = AccessToken이 살아있는데 재발급 받으려는 경우 : 발급 불가 반환
        else if(acUserId.equals(userIdAccToken.get(0))) {
            map.put("msg", "AccessToken Already Valid.");
            map.put("status", 403);    // 발급 불가
        }
        // RefreshToken 유효 & AccessToken 유효한 값이지만 만료
        else {
            map.put("token", createAccessToken((String) userIdAccToken.get(0), ACCESS_TOKEN_EXP_TIME, SECRET_KEY));
            map.put("status", 200);
            map.put("msg", "Access Token Updated Complete");
        }

        return map;
    }

}

