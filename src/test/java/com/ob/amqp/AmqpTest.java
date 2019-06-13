package com.ob.amqp;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: oubin
 * @Date: 2019/6/6 14:03
 * @Description:
 */
public class AmqpTest {

    public void init() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        factory.setPort(5672);
    }

}
