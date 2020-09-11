package com.ob.work.seckill.controller;

import com.ob.common.aspect.limit.Limit;
import com.ob.common.aspect.limit.LimitType;
import com.ob.common.constant.Constants;
import com.ob.work.seckill.dto.SeckillOrderResDTO;
import com.ob.work.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: oubin
 * @Date: 2019/12/3 19:26
 * @Description:
 */
@RestController
@RequestMapping(value = Constants.VERSION_01 + "/seckill_order")
public class SeckillOrderController {

    private final SeckillOrderService orderService;

    @Autowired
    public SeckillOrderController(SeckillOrderService orderService) {
        this.orderService = orderService;
    }

    @Limit(count = 3, expire = 10, name = "抢购商品限流", limitType = LimitType.IP)
    @RequestMapping(value = "/{seckill_goods_id}", method = RequestMethod.POST)
    public SeckillOrderResDTO createOrder(@PathVariable(value = "seckill_goods_id") String goodsId) {
        return orderService.createOrder(goodsId);
    }
}
