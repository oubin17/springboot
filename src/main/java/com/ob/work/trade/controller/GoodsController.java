package com.ob.work.trade.controller;

import com.ob.common.constant.Constants;
import com.ob.work.trade.entity.Goods;
import com.ob.work.trade.dto.GoodsUpdateDto;
import com.ob.work.trade.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:24
 * @Description:
 */
@RestController
@RequestMapping(value = Constants.VERSION_01 + "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 添加抢购商品
     *
     * @param saveDto
     */
    @RequestMapping(method = RequestMethod.POST)
    public Goods addGoodsInfo(@Valid @RequestBody GoodsUpdateDto saveDto) {
        return goodsService.addGoodsInfo(saveDto);
    }

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{goods_id}", method = RequestMethod.GET)
    public Goods getGoodsInfo(@PathVariable("goods_id") String id) {
        return goodsService.getGoodsById(id);
    }

    /**
     * 重建缓存
     *
     * @param id
     * @param saveDto
     * @return
     */
    @RequestMapping(value = "/{goods_id}", method = RequestMethod.PUT)
    public Goods updateGoodsInfo(@PathVariable("goods_id") String id,
                              @Valid @RequestBody GoodsUpdateDto saveDto) {
        return goodsService.updateGoodsByName(id, saveDto.getGoodsName());
    }

    /**
     * 用redis事务控制redis并发写顺序
     *
     * @param id
     */
    @RequestMapping(value = "/expire_time/{goods_id}", method = RequestMethod.PUT)
    public void changeGoodsExpireTime(@PathVariable("goods_id") String id) {
        goodsService.updateGoodsExpireTime(id);
    }

}
