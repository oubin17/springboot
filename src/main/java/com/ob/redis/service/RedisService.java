package com.ob.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: oubin
 * @Date: 2019/7/6 09:49
 * @Description:
 */
@Slf4j
@Service
public class RedisService {

    private static final long DEFAULT_EXPIRE_TIME = 20000L;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static int k = 1000;

    @Autowired
    private RedisLock redisLock;

    /**
     * 秒杀系统
     */
    public void seckill() {

        String key = "test-redis-key";
        String value = UUID.randomUUID().toString();
        try {
            boolean lock = redisLock.lock(key, value, DEFAULT_EXPIRE_TIME);
            if (lock) {
                if (k > 0) {
                    log.info("当前K的值 {}", k--);
                }
                redisLock.unlock(key, value);
            }
        } catch (Exception e) {
            log.info("当前线程{}获取锁异常...", Thread.currentThread().getName());
        }
    }

    public void setKeyToRedis(String key) {
        redisTemplate.opsForValue().set(key, key);
    }

    public String getKeyFromRedis(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

}
