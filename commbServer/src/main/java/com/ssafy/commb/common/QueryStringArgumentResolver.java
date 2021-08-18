package com.ssafy.commb.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

// Annotation 비교 후 처리 로직 만들기
@Component
public class QueryStringArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(QueryStringArgResolver.class) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) throws Exception {

        final HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String json = qs2json(request.getQueryString());

        json = URLDecoder.decode(json, "UTF-8");

        final Object a = mapper.readValue(json, methodParameter.getParameterType());
        return a;
    }


    private String qs2json(String a) {
        String res = "{\"";

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '=') {
                res += "\"" + ":" + "\"";
            } else if (a.charAt(i) == '&') {
                res += "\"" + "," + "\"";
            } else {
                res += a.charAt(i);
            }
        }
        res += "\"" + "}";
        return res;
    }
}
