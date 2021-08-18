package com.ssafy.commb.config.security;

import com.ssafy.commb.model.OauthProvider;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.OauthProviderRepository;
import com.ssafy.commb.repository.UserRepository;
import com.ssafy.commb.service.RedisService;
import com.ssafy.commb.service.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CustomOAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    RedisService redisService;

    @Autowired
    OauthProviderRepository oauthProviderRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${dynamic.front.path}")
    private String dynamicFrontPath;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
                                        , Authentication authentication) throws IOException {

        OAuth2User oAuth2user = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2user.getAttributes();

//      이미 가입한 Oauth 사용자인지 확인
        Optional<OauthProvider> oauthProvider = oauthProviderRepository.findById((Integer) attributes.get("id"));

        User user = null;
        // 존재하지 않으면 회원가입
        if(!oauthProvider.isPresent()){
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) account.get("profile");

            //User 생성
            user = new User();
            user.setEmail((String) account.get("email"));
            user.setName((String) profile.get("nickname"));
            user.setNickname((String) profile.get("nickname"));
            user.setFileUrl((String) profile.get("profile_image_url"));
            user.setRole("USR");
            // 유저권한 정하기

            // user 저장 및 flush
            userRepository.saveAndFlush(user);

            // Oauth 관련 정보저장
            OauthProvider oauthUser = new OauthProvider();
            oauthUser.setId((Integer) attributes.get("id"));
            oauthUser.setUser(user);
            oauthUser.setProviderName("Kakao");

            oauthProviderRepository.save(oauthUser);
        }
        // 존재하면 회원접근
        else{
            user = oauthProvider.get().getUser();
        }
        // 안가 code 발급
        String code = UUID.randomUUID().toString();

        // Redis 등록
        long expire = 60000;
        redisService.setStringValue(code, Integer.toString(user.getId()), expire);

        // getRedirectStrategy().sendRedirect(request, response, "/oauth/redirect?code="+code);
        response.sendRedirect(dynamicFrontPath + "oauth/redirect?code="+code);
    }
}
