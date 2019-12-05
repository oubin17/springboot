package com.ob.work.seckill.service;

import com.ob.common.config.rabbitmq.RabbitProducer;
import com.ob.common.context.SessionContext;
import com.ob.common.util.JsonUtil;
import com.ob.work.seckill.dto.SeckillOrderResDTO;
import com.ob.work.seckill.entities.SeckillOrder;
import com.ob.work.seckill.enums.OrderStateEnum;
import com.ob.work.seckill.mqconfig.order.SeckillOrderCreateMq;
import com.ob.work.seckill.mqconfig.order.SeckillOrderDelayCheckMq;
import com.ob.work.seckill.repository.SeckillOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: oubin
 * @Date: 2019/12/3 19:27
 * @Description:
 */
@Slf4j
@Service
public class SeckillOrderService {

    @Autowired
    private SeckillOrderRepository orderRepository;

    @Autowired
    private SeckillGoodsInventoryService inventoryService;

    @Autowired
    private RabbitProducer rabbitProducer;

    /**
     * 创建订单
     *
     * @param seckillGoodsId
     * @return
     */
    public SeckillOrderResDTO createOrder(String seckillGoodsId) {
        Boolean haveQuantity = inventoryService.decQuantity(seckillGoodsId);
        if (haveQuantity) {
            //TODO 异步下单:先预下单，把下单的事件发送到MQ，由MQ消费，消费成功更改预下单状态
            SeckillOrder order = preCreateOrder(seckillGoodsId);
            rabbitProducer.sendMsg(SeckillOrderCreateMq.SECKILL_ORDER_CREATE_DIRECT_EXCHANGE,
                    SeckillOrderCreateMq.SECKILL_ORDER_CREATE_BINDING_KEY,
                    JsonUtil.toJson(order.convertToSeckillOrderMQDTO()));
            //同时发送消息到延迟队列延迟检查
            rabbitProducer.sendMsg(SeckillOrderDelayCheckMq.SECKILL_ORDER_DELAY_CHECK_EXCHANGE,
                    SeckillOrderDelayCheckMq.SECKILL_ORDER_DELAY_CHECK_ROUTING_KEY,
                    JsonUtil.toJson(order.convertToSeckillOrderMQDTO()));
            return order.convertToSeckillOrderResDTO();
        } else {
            return null;
        }

    }

    /**
     * 预下单:MQ消费下单请求后更改下单状态为待付款
     *
     * @param seckillGoodsId
     * @return
     */
    private SeckillOrder preCreateOrder(String seckillGoodsId) {
        SeckillOrder order = new SeckillOrder();
        order.setGoodsId(seckillGoodsId);
        order.setUserId(SessionContext.currentUserId());
        order.setState(OrderStateEnum.PRE_CREATE.getState());
        orderRepository.save(order);
        return order;
    }




}
