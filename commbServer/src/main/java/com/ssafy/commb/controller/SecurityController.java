package com.ssafy.commb.controller;

import com.ssafy.commb.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

// AccessToken이 만료되면 RefreshToken 확인
// 1. RefreshToken이 살아있다면 새로운 AccessToken 발행하여 클라에 반환
// 2. RefreshToken도 만료되었다면 인증 불가 HttpStatus.valueOf(401)

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    private static final Long ACCESS_TOKEN_EXP_TIME = 2 * 60 * 1000L;     // 2분
    private static final Long REFRESH_TOKEN_EXP_TIME = 10 * 60 * 1000L;     // 30분

    @Transactional
    @GetMapping("/create/token")
    public Map<String, Object> createToken(@RequestParam int userId){
        String refreshToken = securityService.createRefreshToken(REFRESH_TOKEN_EXP_TIME);                      // Redis에 저장 & 클라이언트에 제공
        String accessToken = securityService.createAccessToken(String.valueOf(userId), ACCESS_TOKEN_EXP_TIME);  // Redis에 저장 & 클라이언트에 제공

        Map<String, Object> map = new HashMap<>();
        map.put("rfToken", refreshToken);
        map.put("acToken", accessToken);
        securityService.saveToken( (String) map.get("rfToken"), String.valueOf(userId), REFRESH_TOKEN_EXP_TIME/1000 );    // RefreshToken DB에 저장

        return map;
    }

    // AccessToken 받아서 살아있는지 검사
    @GetMapping("/valid/access")
    public ResponseEntity<Map<String, String>> validAccessToken(@RequestParam String token){
        String userId = securityService.getTokenBody(token);

        Map<String, String> map = new HashMap<>();

        if(userId.equals("expire")) {
            map.put("msg", "needRefreshToken");
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.valueOf(100));
        }
        map.put("userId", userId);
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    // RefreshToken 받아서 AccessToken 발행 및 반환
    @GetMapping("/valid/refresh")
    public ResponseEntity<Map<String, Object>> validRefreshToken(@RequestParam(value="token") String token){
        String userId = securityService.findToken(token);       // redis 인메모리 DB로부터 key(refreshToken)를 통해 userId를 가져온다.

        if(userId.equals("expire")) return new ResponseEntity(HttpStatus.valueOf(401));        // expire => 토큰 만료로 인증 불가 return


        System.out.println(userId);
        String accessToken = securityService.createAccessToken(userId, ACCESS_TOKEN_EXP_TIME);  // AccessToken 재발행

        Map<String, Object> map = new HashMap<>();
        map.put("acToken", accessToken);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

}
