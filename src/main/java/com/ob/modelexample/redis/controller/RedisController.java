package com.ob.modelexample.redis.controller;

import com.ob.modelexample.redis.service.RedisService;
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

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void seckill() {
        redisService.seckill();
    }

}