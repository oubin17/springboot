package com.ob.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @Author: oubin
 * @Date: 2019/7/22 15:57
 * @Description: https://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/
 */
@Slf4j
@Component
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    /**
     * 当key不存在的时候，我们进行set操作；若key存在，则不做任何操作
     */
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * 给key加一个过期的设置，具体时间由第五个参数给出
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    private final RedisTemplate<String, Object> redisTemplate;

    private final JedisPool jedisPool;

    @Autowired
    public RedisLock(JedisPool jedisPool, RedisTemplate<String, Object> redisTemplate) {
        this.jedisPool = jedisPool;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加锁：1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2. 已有锁存在，不做任何操作。
     *
     * @param key 用key来当锁，key唯一
     * @param value requestId可以使用UUID.randomUUID().toString()方法生成
     * @param expireTime 过期时间
     * @return
     */
    public boolean lock(String key, String value, long expireTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("获取锁异常{}", e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 解锁：首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
            return RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("释放锁异常{}", e.getMessage());
        }
        return false;
    }



    /**
     * 加锁: 这种实现方式并不是很好，value超时时间设置太长的话，
     *
     * @param key 商品id
     * @param value 当前时间加上超时时间
     * @return
     */
    public boolean lockNotRecommend(String key, String value) {
        //如果返回true,表示当前线程设置成功,获取到锁，其实该命令就是setnx命令
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //避免死锁，且只能让一个线程拿到锁
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (StringUtils.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValues = (String) redisTemplate.opsForValue().getAndSet(key, value);

            //只会让一个线程拿到锁
            //如果旧的value和currentValue相等，只会有一个线程达到条件，因为第二个线程拿到oldValue已经和currentValue不一样了
            return StringUtils.isNotEmpty(oldValues) && oldValues.equals(currentValue);
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlockNotRecommend(String key, String value) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.info("Redis分布式锁 解锁异常, {}", e);
        }

    }

}
