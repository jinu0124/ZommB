package com.ssafy.commb.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssafy.commb.jwt.SecurityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Api("Interceptor")
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityService securityService;

    @Value("${security.secretkey}")
    private String secretKey;

    // Authorization : Bearer <TOKEN>
    // 로그인 Authorization
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
        System.out.println("In Interceptor PreHandler");

        Map<String, Object> map = new HashMap<>();
        boolean handle = false;
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        String acToken = request.getParameter("acToken");
        String rfToken = request.getParameter("rfToken");
//        String acToken = request.getHeader("access-token");
//        String rfToken = request.getHeader("refresh-token");

        if(acToken == null || acToken.length() < 24) {                  // Access 토큰이 없을 때
            map.put("status", 401);
            map.put("msg", "There is no Token");
            handle = false;
        }
        else if(rfToken != null && rfToken.length() >= 24){             // Access 토큰과 Refresh 토큰이 둘 다 있을 때
            map = securityService.validRefreshToken(acToken, rfToken);
            if((int) map.get("status") == 200) {
                response.setHeader("access-token", (String) map.get("token"));  // Access 토큰이 성공적으로 재 발행 되었을 때
                handle = true;
            }
            else handle = false;
        }
        else{                                                           // Access 토큰만 있을 때
            String ret = securityService.decodeToken(acToken, secretKey);
            if(ret.equals("expire")) {
                map.put("msg", "AccessToken has been expired");
                map.put("status", 100);
                handle = false;
            }
            else if(ret.equals("invalid")){
                map.put("msg", "AccessToken is invalid");
                map.put("status", 403);
                handle = false;
            }
            else{
                map.put("status", 200);         // AccessToken이 유효할 때
                handle = true;
            }
        }
        jsonObject.addProperty("token", (String) map.get("token"));
        jsonObject.addProperty("msg", (String) map.get("msg"));


        response.setStatus((int) map.get("status"));
        if(!handle) {
            response.getWriter().write(gson.toJson(jsonObject));
        }

        return handle;
    }
}
