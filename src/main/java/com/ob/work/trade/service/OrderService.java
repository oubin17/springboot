package com.ob.work.trade.service;

import com.ob.common.context.RequestContext;
import com.ob.work.trade.domain.Goods;
import com.ob.work.trade.domain.Order;
import com.ob.work.trade.repository.GoodsRepository;
import com.ob.work.trade.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/9/23 14:09
 * @Description:
 */
@Service
public class OrderService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * sql控制并发
     *
     * @param goodsId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(String goodsId) {
        goodsRepository.strictFind(goodsId);
        int existGoods = goodsRepository.createOrder(goodsId);
        if (existGoods > 0) {
            Order order = new Order();
            order.setUserId(RequestContext.currentUserId());
            order.setUserName(RequestContext.currentUserId());
            order.setGoodsId(goodsId);
            return orderRepository.save(order);
        }
        return null;
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
        Order order = new Order();
        order.setUserId(RequestContext.currentUserId());
        order.setUserName(RequestContext.currentUserId());
        order.setGoodsId(goodsId);
        return orderRepository.save(order);
    }
}
