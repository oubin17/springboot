package com.ob.redis;

import com.ob.SpringbootApplication;
import com.ob.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: oubin
 * @Date: 2019/7/6 09:48
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test001() {
        String key = "redis test";
        redisService.setKeyToRedis(key);
        System.out.println(redisService.getKeyFromRedis(key));
    }
}
