package com.ssafy.commb.jwt;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;
import java.util.Map;

public interface SecurityService {
    @Transactional
    public Map<String, Object> createToken(int userId);

    public String createAccessToken(String userId, Long expTime, String secretKey);

    public String createRefreshToken(Long expTime, String secretKey);

    public Key makeKey(String secretKey);

    public void save(String token, List<String> userIdAccToken, Long expTime);

    public String get(String token);

    public List<Object> find(String token);

    public void del(String token);

    public String decodeToken(String token, String secretKey);

    public Map<String, Object> validRefreshToken(String acToken, String rfToken);
}
