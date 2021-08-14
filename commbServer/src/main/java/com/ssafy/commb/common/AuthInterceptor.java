package com.ssafy.commb.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssafy.commb.jwt.SecurityService;
import io.swagger.annotations.Api;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Api("Interceptor")
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityService securityService;

    @Value("${security.secretkey}")
    private String secretKey;

    @Value("${security.accesstoken}")
    private String accessToken;

    @Value("${security.refreshtoken}")
    private String refreshToken;

    /**
     * @ 회원 토큰 검증 PreHandler
     * @param request : 요청
     * @param response : 반환
     * @param Handler : 핸들러
     * @return True, False with response
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {

        System.out.println("(" + request.getMethod() + ")PreHandler / Request Url : " + request.getRequestURI());

        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        /**
         * @ 토큰 받기
         */
        String acToken = request.getHeader(accessToken);
        String rfToken;
        if(request.getHeader(refreshToken) == null) rfToken = null;
        else rfToken = request.getHeader(refreshToken);

        /**
         * @ AccessToken이 없을때 / Access, Refresh Token 둘 다 있을 때 / Access Token 만 있을 때
         */
        if(acToken == null || acToken.length() < 24) {
            map.put("status", 401);
            map.put("msg", "There is no Token");
        }
        else if(rfToken != null){
            map = securityService.validRefreshToken(acToken, rfToken);
            if((int) map.get("status") == 200) {
                response.setHeader(refreshToken, rfToken);
                response.setHeader(accessToken, (String) map.get("token"));
                request.setAttribute("userId", map.get("userId"));
            }
        }
        else{
            String ret = securityService.decodeToken(acToken, secretKey);
            if(ret.equals("expire")) {
                map.put("msg", "AccessToken has been expired");
                map.put("status", 401);
            }
            else if(ret.equals("invalid")){
                map.put("msg", "AccessToken is invalid");
                map.put("status", 403);
            }
            else {
                request.setAttribute("userId", Integer.parseInt(ret));
                map.put("status", 200);
            }
        }
        jsonObject.addProperty("token", (String) map.get("token"));
        jsonObject.addProperty("msg", (String) map.get("msg"));

        response.setStatus((int) map.get("status"));
        if((int) map.get("status") == 200) return true;

        response.getWriter().write(gson.toJson(jsonObject));
        return false;
    }
}
