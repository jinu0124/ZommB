package com.ssafy.commb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setStringValue(String key, List<String> value, Long expTime) {
        redisTemplate.opsForList().rightPush(key, value.get(0));
        redisTemplate.opsForList().rightPush(key, value.get(1));
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }

    public void setStringValue(String key, Long expTime){
        redisTemplate.opsForList().rightPush(key, "valid");
        redisTemplate.expire(key, expTime, TimeUnit.MILLISECONDS);
    }

    public String getStringValue(String key){
        String ret = (String) redisTemplate.opsForList().rightPop(key);
        System.out.println(ret);
        return ret;
    }

    public List<Object> getListValue(String key){
        List<Object> ret = redisTemplate.opsForList().range(key, 0, -1);
        System.out.println(ret);
        return ret;
    }

    public void del(String key){
        redisTemplate.delete(key);
    }
}
