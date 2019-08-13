package com.ob.amqp;

import com.ob.SpringbootApplication;
import com.ob.amqp.service.RabbitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @Author: oubin
 * @Date: 2019/6/6 14:03
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
public class AmqpTest {

    @Resource
    private RabbitService rabbitService;

    @Test
    public void test001() {
        rabbitService.topicQueue1("队列1");
    }

    @Test
    public void test002() {
        rabbitService.topicQueue2("队列2");
    }

    @Test
    public void test003() {
        rabbitService.fanoutQueue("fanout msg!!!");
    }

}
