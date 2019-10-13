package com.ob.modelexample.amqp.service;

import com.ob.modelexample.amqp.mqconfig.directexchange.DirectRabbitConfig;
import com.ob.common.config.rabbitmq.RabbitProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/8/26 15:15
 * @Description:
 */
@Slf4j
@Service
public class RabbitConfirmService {

    @Autowired
    private RabbitProducer rabbitProducer;

    public void sendMsg(String msg) {
        rabbitProducer.sendMsg(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_EXCHANGE_ROUTING_KEY_TWO, msg);
    }

}
