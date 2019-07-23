package com.ob.redis.controller;

import com.ob.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: oubin
 * @Date: 2019/7/22 17:35
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void seckill() {
        redisService.seckill();
    }

    @RequestMapping(value = "/key/{key}", method = RequestMethod.GET)
    public String getKey(@PathVariable(value = "key") String key) {
        return redisService.getKeyFromRedis(key);
    }

}
