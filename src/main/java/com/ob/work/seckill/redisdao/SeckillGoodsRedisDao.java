package com.ob.work.seckill.redisdao;

import com.ob.common.base.redisdao.RedisBaseDao;
import com.ob.common.constant.RedisConstants;
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


    private static final String KEY_PREFIX = "seckill_goods";

    private static final String VALUE_KEY = "good_id_value";

    private static final String HASH_KEY = "goods_id_hash";

    /**
     * value类型的redis key
     *
     * @param key
     * @return
     */
    private String valueRedisKey(String key) {
        return getValueKeyPrefix() + RedisConstants.REDIS_CON + key;
    }

    /**
     * value类型的redis key前缀
     * @return
     */
    private String getValueKeyPrefix() {
        return KEY_PREFIX + RedisConstants.REDIS_CON + VALUE_KEY;
    }

    /**
     * hash类型的redis key
     * @return
     */
    private String getHashKey() {
        return KEY_PREFIX + RedisConstants.REDIS_CON + HASH_KEY;
    }

    public void saveValue(String id, String value) {
        redisBaseDao.addValue(valueRedisKey(id), value);
    }

    public void saveValueExpireTime(String id, String value, long expireTime, TimeUnit timeUnit) {
        redisBaseDao.setKeyWithExpireTime(id, value, expireTime, timeUnit);
    }

}
