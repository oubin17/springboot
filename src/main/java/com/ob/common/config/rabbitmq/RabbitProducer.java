package com.ob.common.config.rabbitmq;

import com.ob.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: oubin
 * @Date: 2019/8/26 14:41
 * @Description:
 */
@Slf4j
@Component
public class RabbitProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    /**
     * mandatory：设置为true，监听器会接收到路由不可达的消息，如果是false，broker端直接删除该消息
     *
     * @param rabbitTemplate
     */
    @Autowired
    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setMandatory(true);
    }

    /**
     * id + 时间戳 全局唯一  用于ack保证唯一一条消息 这里先用uuid表示
     *
     * @param message
     */
    public void sendMsg(Object message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(JsonUtil.toJson(message), correlationData);
    }

    /**
     * 向交换机发送message
     *
     * @param message
     * @param exchange
     * @param routingKey
     */
    public void sendMsg(String exchange, String routingKey, Object message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    /**
     * 消息成功发送到broker时回调的方法
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调ID:{}", correlationData);
        if (ack) {
            log.info("消息成功发送到broker");
        } else {
            log.error("消息发送至broker失败{}", cause);
        }
    }

    /**
     * 当根据路由键无法路由到队列的时候回调的方法
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息不可达...");
        log.info("return message:{}, exchange:{}, routingKey:{}, replyCode:{}, replyText:{}",
                message.toString(),
                exchange,
                routingKey,
                replyCode,
                replyText);
    }
}
