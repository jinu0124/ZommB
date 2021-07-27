package com.ssafy.commb.security;

import com.ssafy.commb.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class SecurityService {
    @Autowired
    private RedisService redisService;

    private static final String SECRET_KEY = "jinwooIDjinwooIDjinwooIDjinwooIDjinwooIDjinwooID";     // 암/복호 키(회원 비밀번호로 사용 보통)

    // 로그인 서비스할때 같이 수행
    // 토큰 만들기 : Parameter (유저ID(일반적), password(key로 사용))
    // AccessToken
    public String createAccessToken(String userId, Long expTime) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        Key signingKey = makeKey(SECRET_KEY);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);
        System.out.println(userId);
        // 추가로 클레임, 헤더 등 다양한 정보를 더 넣을 수 있음
        return Jwts.builder()
                .setSubject(userId)                          // userId & 발행일자를 담아서 암호화
                .setIssuedAt(new Date())
                .signWith(signingKey, SignatureAlgorithm.HS256)   // key, key 암호화 알고리즘 사용
                .setExpiration(expireTime).compact();       // 만료 시점 지정 & compact to String
    }

    // RefreshToken
    public String createRefreshToken(Long expTime) {
        if (expTime < 0L) throw new RuntimeException("만료시간이 0보다 커야합니다.");

        String randomStr = "";
        for(int i=0; i<64; i++) {
            Random random = new Random();
            randomStr += String.valueOf((char) random.nextInt(26) + 65);
        }
        Key signingKey = makeKey(randomStr);

        Date expireTime = new Date(System.currentTimeMillis() + expTime);

        // 추가로 클레임, 헤더 등 다양한 정보를 더 넣을 수 있음
        return Jwts.builder()
                .setSubject(randomStr)                      // 랜덤문자열 & 발행일자를 담아서 암호화
                .setIssuedAt(new Date())
                .signWith(signingKey, SignatureAlgorithm.HS256)   // key, key 암호화 알고리즘 사용
                .setExpiration(expireTime).compact();       // 만료 시점 지정 & compact to String
    }

    public Key makeKey(String secretKey){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;       // HS256 암호화 알고리즘 사용
        byte[] securityByte = DatatypeConverter.parseBase64Binary(secretKey);   // String 형태의 키를 Byte 형태로 인코딩

        return new SecretKeySpec(securityByte, signatureAlgorithm.getJcaName());   // 암호화된 Key 생성
    }


    public void saveToken(String token, String userId, Long expTime){
        redisService.setStringValue(token, userId, expTime);
    }

    public String findToken(String token){
        String userId = redisService.getStringValue(token);
        if (userId == null) return "expire";
        System.out.println(userId);
        return userId;
    }

    // 토큰 Validation 할 때 수행
    // 토큰 Body.subject 반환받기
    public String getTokenBody(String token){
        // 클레임 : Payload 에 들어있는 값
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        }catch(Exception e){
            return "expire";
        }
    }
}

