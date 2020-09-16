package com.ob.work.trade.service;

import com.ob.common.aspect.redisexception.RedisConnectionException;
import com.ob.work.trade.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2020/9/11 09:23
 * @Description:
 */
@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    /**
     * "5250b2d0-0cdc-4bfe-99e7-eb908e9ba070"
     * @param key
     * @param value
     */
    public void setGoodValue(String key, Integer value) {
//        redisTemplate.opsForValue().set(key, value);
        for (int i = 0; i < value; i++) {
            redisTemplate.opsForList().leftPush(key, 1);
        }
    }

    /**
     * 有可能存在redis服务不可用，连接异常的问题，有两个解决方案：
     * 1）基于数据库的乐观锁
     * 2）基于分布式系统的分布式锁
     * @param key
     */
    @RedisConnectionException
    public int listValue(String key) {
        Object o = redisTemplate.opsForList().rightPop(key);
        if (o != null) {
            return goodsService.descDbValue(key);
        }
        return -1;
    }





    @Transactional(rollbackFor = Exception.class)
    public void descValue(String key) {
        redisTemplate.opsForValue().increment(key, -1);
        goodsRepository.createOrder(key);
    }

}
