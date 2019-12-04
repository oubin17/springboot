package com.ob.work.seckill.redisdao;

import com.ob.common.base.redisdao.RedisBaseDao;
import com.ob.common.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/4 09:21
 * @Description:
 */
@Repository
public class SeckillGoodsInventoryRedisDao {

    @Autowired
    private RedisBaseDao redisBaseDao;

    /**
     * key前缀
     */
    private static final String KEY_PREFIX = "seckill_goods_inventory";

    /**
     * key类型
     */
    private static final String VALUE_KEY = "goods_inventory";

    /**
     * key锁
     */
    private static final String LOCK_KEY = "lock_key";

    /**
     * value类型的redis key
     *
     * @param key
     * @return seckill_goods_inventory:goods_inventory:{key}
     */
    public String seckillGoodsInventoryKey(String key) {
        return KEY_PREFIX + RedisConstants.REDIS_CON + VALUE_KEY + RedisConstants.REDIS_CON + key;
    }

    /**
     * 获取分布式锁的key
     *
     * @param key
     * @return seckill_goods_inventory:lock_key:{key}
     */
    public String seckillGoodsInventoryKeyLock(String key) {
        return KEY_PREFIX + RedisConstants.REDIS_CON + LOCK_KEY + RedisConstants.REDIS_CON + key;
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
        redisBaseDao.setKeyWithExpireTime(seckillGoodsInventoryKey(key), value, expireTime, timeUnit);
    }

    /**
     * 返回剩余库存数量
     *
     * @param key
     * @return
     */
    public Long decValue(String key) {
        return redisBaseDao.incValue(seckillGoodsInventoryKey(key), -1);
    }

}
