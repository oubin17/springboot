package com.ob.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static int k = 1000;

    private final RedisLock redisLock;

    @Autowired
    public RedisService(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    /**
     * 秒杀系统
     */
    public void seckill() {

        String key = "redis:lock";
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

}
