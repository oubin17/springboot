package com.ob.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/7/6 09:49
 * @Description:
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setKeyToRedis(String key) {
        redisTemplate.opsForValue().set(key, key);
    }

    public String getKeyFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
