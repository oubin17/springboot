package com.ob.work.trade.service;

import com.ob.common.config.rabbitmq.RabbitProducer;
import com.ob.common.context.RequestContext;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import com.ob.common.util.JsonUtil;
import com.ob.modelexample.redis.service.RedisLock;
import com.ob.work.trade.constant.Constants;
import com.ob.work.trade.entity.Goods;
import com.ob.work.trade.entity.Order;
import com.ob.work.trade.enums.OrderStateEnum;
import com.ob.work.trade.messagequeue.OrderMqConfig;
import com.ob.work.trade.repository.GoodsRepository;
import com.ob.work.trade.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author: oubin
 * @Date: 2019/9/23 14:09
 * @Description:
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private ValueOperations valueOperations;

    @Autowired
    private RabbitProducer rabbitProducer;

    /**
     * 使用redis分布式锁控制并发
     *
     * @param goodsId
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public Order createOrderWithRedisLock(String goodsId) {
        Integer num = (Integer) valueOperations.get(Constants.GOODS_ID_KEY + goodsId);
        if (null != num && num <= 0) {
            return null;
        }
        String key = Constants.GOODS_ID_LOCK + ":" + goodsId;
        String value = UUID.randomUUID().toString();
        try {
            boolean lock = redisLock.lock(key, value);
            if (lock) {
                Order order;
                if (null == valueOperations.get(Constants.GOODS_ID_KEY + goodsId)) {
                    Goods goods = updateGoodsRemainingQuantity(goodsId);
                    if (null == goods) {
                        return null;
                    }
                    order = addGoodsOrder(goodsId);
                    valueOperations.set(Constants.GOODS_ID_KEY + goodsId, goods.getRemainingQuantity());
                } else {
                    order = createOrderWithSQL(goodsId);
                    if (null != order) {
                        valueOperations.increment(Constants.GOODS_ID_KEY + goodsId, -1);
                    }
                }
                rabbitProducer.sendMsg(OrderMqConfig.ORDER_DELAY_EXCHANGE, OrderMqConfig.ORDER_DELAY_EXCHANGE_ROUTING_KEY, JsonUtil.toJson(order));
                return order;
            }
        } catch (Exception e) {
            log.info("当前线程创建订单失败,商品ID:{}", goodsId);
            throw new BizException(ErrorCode.BAD_REQUEST, "创建订单失败");
        } finally {
            redisLock.unlock(key, value);
        }
        return null;
    }

    /**
     * sql控制并发
     *
     * @param goodsId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrderWithSQL(String goodsId) {
        goodsRepository.strictFind(goodsId);
        int existGoods = goodsRepository.createOrder(goodsId);
        if (existGoods > 0) {
            return addGoodsOrder(goodsId);
        } else {
            return null;
        }
    }

    /**
     * 乐观锁控制并发
     *
     * @param goodsId
     * @return
     */
    public Order createOrderWithOptimisticLock(String goodsId) {
        Goods goods = goodsRepository.findFirstByIdAndRemainingQuantityGreaterThan(goodsId, 0);
        if (null == goods) {
            return null;
        }
        goods.setRemainingQuantity(goods.getRemainingQuantity() - 1);
        goodsRepository.saveAndFlush(goods);
        return addGoodsOrder(goodsId);
    }

    /**
     * 更新商品剩余数量
     *
     * @param goodsId
     * @return
     */
    private Goods updateGoodsRemainingQuantity(String goodsId) {
        Goods goods = goodsRepository.findFirstByIdAndRemainingQuantityGreaterThan(goodsId, 0);
        if (null == goods) {
            valueOperations.set(goodsId, -1);
            return null;
        }
        goods.setRemainingQuantity(goods.getRemainingQuantity() - 1);
        goodsRepository.saveAndFlush(goods);
        return goods;
    }

    /**
     * 只负责创建订单
     *
     * @param goodsId
     * @return
     */
    private Order addGoodsOrder(String goodsId) {
        Order order = new Order();
        order.setUserId(RequestContext.currentUserId());
        order.setUserName(RequestContext.currentUserId());
        order.setGoodsId(goodsId);
        order.setState(OrderStateEnum.UNPAID.getState());
        return orderRepository.save(order);
    }
}
