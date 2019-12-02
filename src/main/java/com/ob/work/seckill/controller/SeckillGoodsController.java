package com.ob.work.seckill.controller;

import com.ob.common.constant.Constants;
import com.ob.work.seckill.service.SeckillGoodsService;
import com.ob.work.seckill.dto.SeckillGoodsReqDTO;
import com.ob.work.seckill.dto.SeckillGoodsResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
