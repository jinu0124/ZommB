package com.ssafy.commb.config;

import com.ssafy.commb.common.AuthInterceptor;
import com.ssafy.commb.common.QueryStringArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// Config
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    private final QueryStringArgumentResolver argumentResolver;

    @Override
    public void addArgumentResolvers(
            final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver);
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/security/**", "/books/**",
                        "/daily-events/**", "/weekly-events/**", "/my-events/**",
                        "/feeds/**", "/follows/**", "/users/**")
                .excludePathPatterns("/security/create/**", "/users/login"
                , "/users", "/users/email", "/users/confirm-email", "/users/checkEmailComplete",
                        "/users/social/kakao", "/users/find-password", "/users/update-password");
        //
    }
}