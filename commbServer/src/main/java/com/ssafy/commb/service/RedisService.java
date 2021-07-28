package com.ssafy.commb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean setStringValue(String key, List<String> value, Long expTime){
        if(expTime < 0) return false;
        redisTemplate.opsForList().rightPush(key, value.get(0));
        redisTemplate.opsForList().rightPush(key, value.get(1));
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
        return true;
    }

    public List<Object> getStringValue(String key){
        List<Object> ret = redisTemplate.opsForList().range(key, 0, -1);
        System.out.println(ret);
        return ret;
    }

    public void del(String key){
        redisTemplate.delete(key);
    }
}
