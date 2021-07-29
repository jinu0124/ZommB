package com.ssafy.commb.jwt;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;
import java.util.Map;

public interface SecurityService {
    @Transactional
    public Map<String, Object> createToken(int userId);

    // AccessToken 발행
    public String createAccessToken(String userId, Long expTime, String secretKey);

    // RefreshToken 발행
    public String createRefreshToken(Long expTime, String secretKey);
    // SignKey 생성
    public Key makeKey(String secretKey);

    // Redis save
    public void save(String token, List<String> userIdAccToken, Long expTime);

    public String get(String token);

    // Redis select
    public List<Object> find(String token);

    public void del(String token);

    // 토큰 Validation & Get Subject
    public String decodeToken(String token, String secretKey);

    // AccessToken 의 무제한 발행을 막아야한다!  // 1가지 해결 필요 : 유효하지만 만료된 Access토큰과 유효한 RefreshToken을 보냈을때 1번만 발행해야하는데 계속 발행 가능
    // RefreshToken 받아서 AccessToken 재발행
    public Map<String, Object> validRefreshToken(String acToken, String rfToken);
}
