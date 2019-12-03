package com.ob.work.seckill.controller;

import com.ob.common.constant.Constants;
import com.ob.work.seckill.dto.SeckillGoodsReqDTO;
import com.ob.work.seckill.dto.SeckillGoodsResDTO;
import com.ob.work.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:13
 * @Description:
 */
@RestController
@RequestMapping(value = Constants.VERSION_01 + "/seckill_goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 添加抢购商品
     *
     * @param goodsReqDTO
     */
    @RequestMapping(method = RequestMethod.POST)
    public SeckillGoodsResDTO addSeckillGoods(@Valid @RequestBody SeckillGoodsReqDTO goodsReqDTO) {
        goodsReqDTO.checkProperties();
        return seckillGoodsService.addSeckillGoods(goodsReqDTO);
    }

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{seckill_goods_id}", method = RequestMethod.GET)
    public SeckillGoodsResDTO getSeckillGoodsInfo(@PathVariable("seckill_goods_id") String id) {
        return seckillGoodsService.getGoodsById(id);
    }

    /**
     * 重建缓存
     *
     * @param id
     * @param saveDto
     * @return
     */
    @RequestMapping(value = "/{seckill_goods_id}", method = RequestMethod.PUT)
    public SeckillGoodsResDTO updateSeckillGoodsInfo(@PathVariable("seckill_goods_id") String id,
                              @Valid @RequestBody SeckillGoodsReqDTO saveDto) {
        return seckillGoodsService.updateSeckillGoods(id, saveDto.getGoodsName());
    }

}
