package com.ob.work.seckill.redisdao;

import com.ob.common.base.redisdao.RedisBaseDao;
import com.ob.common.constant.RedisConstants;
import com.ob.work.seckill.entities.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/2 17:23
 * @Description:
 */
@Repository
public class SeckillGoodsRedisDao {

    @Autowired
    private RedisBaseDao redisBaseDao;

    /**
     * key前缀
     */
    private static final String KEY_PREFIX = "seckill_goods";

    /**
     * key类型
     */
    private static final String VALUE_KEY = "goods_id_value";

    /**
     * key锁
     */
    private static final String LOCK_KEY = "lock_key";

    /**
     * 默认过期时间
     */
    private static final Long DEFAULT_EXPIRE_TIME = 60 * 60 * 24L;

    /**
     * value类型的redis key
     *
     * @param key
     * @return seckill_goods:good_id_value:{key}
     */
    public String seckillGoodsKey(String key) {
        return KEY_PREFIX + RedisConstants.REDIS_CON + VALUE_KEY + RedisConstants.REDIS_CON + key;
    }

    /**
     * 获取分布式锁的key
     *
     * @param key
     * @return seckill_goods:lock_key:{key}
     */
    public String seckillGoodsKeyLock(String key) {
        return KEY_PREFIX + RedisConstants.REDIS_CON + LOCK_KEY + RedisConstants.REDIS_CON + key;
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public SeckillGoods getValue(String key) {
        return (SeckillGoods) redisBaseDao.getValue(seckillGoodsKey(key));
    }

    /**
     * 设置value的同时设置value的过期时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     */
    public void saveValueExpireTime(String key, Object value, long expireTime, TimeUnit timeUnit) {
        redisBaseDao.setKeyWithExpireTime(seckillGoodsKey(key), value, expireTime, timeUnit);
    }

    /**
     * 设置value并设置默认过期时间
     *
     * @param key
     * @param value
     */
    public void saveValueDefaultExpireTime(String key, Object value) {
        redisBaseDao.setKeyWithExpireTime(seckillGoodsKey(key), value, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     */
    public void delete(String key) {
        redisBaseDao.delete(seckillGoodsKey(key));
    }

    public void saveValue(String id, SeckillGoods value) {
        redisBaseDao.addValue(seckillGoodsKey(id), value);
    }
}
