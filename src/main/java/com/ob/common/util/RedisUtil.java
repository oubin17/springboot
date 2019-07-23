package com.ob.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/7/9 08:53
 * @Description:
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void deletekey(String... key) {
        if (key != null && key.length > 0) {
            redisTemplate.delete(CollectionUtils.arrayToList(key));
        }
    }

    // ============================String=============================

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ============================Map=============================

    /**
     * get
     *
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 根据key获取所有的键值对
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置值
     *
     * @param key
     * @param item
     * @param value
     * @param time
     */
    public void hmset(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().putIfAbsent(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public long hincr(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    // ============================set=============================

    /**
     * 获取key中set的所有值
     *
     * @param key
     * @return
     */
    public Set<Object> sget(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // ===============================list=================================

    /**
     * 获取长度内list
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lget(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     *
     * @param key
     * @param index
     * @return
     */
    public Object lgetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 右插入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lrightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
        return true;
    }

    // ===============================zset=================================

    /**
     * 添加元素到zset
     *
     * @param key
     * @param item
     * @param score
     */
    public void zAdd(String key, String item, double score) {
        redisTemplate.opsForZSet().add(key, item, score);
    }
}
