package com.ssafy.commb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public boolean setStringValue(String key, String value, Long expTime){
        if(expTime < 0) return false;
        redisTemplate.opsForHash().put("token", key, value);       // timeout Sec
        redisTemplate.expire(key, 1, TimeUnit.MINUTES);
        return true;
    }

    public String getStringValue(String key){
        String retValue =  (String) redisTemplate.opsForHash().get("token", key);
        System.out.println(redisTemplate.opsForHash().get("token", key));
        if (retValue == null) return "expire";
        return retValue;
    }
}
