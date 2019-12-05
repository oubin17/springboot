package com.ob.work.seckill.mqconfig.order;

import com.ob.common.util.JsonUtil;
import com.ob.work.seckill.dto.mq.SeckillOrderMQDTO;
import com.ob.work.seckill.enums.OrderStateEnum;
import com.ob.work.seckill.repository.SeckillOrderMQMsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/12/4 20:01
 * @Description:
 */
@Slf4j
@Component
public class SeckillOrderConfirmMqConsumer {

    @Autowired
    private SeckillOrderMQMsgRepository msgRepository;

    /**
     * 记录创建订单的mq消息
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillOrderConfirmMq.SECKILL_ORDER_CONFIRM_QUEUE)
    @RabbitHandler
    public void process(Message msg) {
        SeckillOrderMQDTO seckillOrderMQDTO = JsonUtil.byteArrayToBean(msg.getBody(), SeckillOrderMQDTO.class);
        try {
            if (null != seckillOrderMQDTO) {
                seckillOrderMQDTO.setOrderState(OrderStateEnum.CREATE_SUCCEED.getState());
                msgRepository.save(seckillOrderMQDTO.convertToSeckillOrderMQ());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("记录ms消息失败：{}", seckillOrderMQDTO);
        }

    }
}
