package com.ob.work.seckill.service;

import com.ob.work.seckill.dto.SeckillGoodsReqDTO;
import com.ob.work.seckill.dto.SeckillGoodsResDTO;
import com.ob.work.seckill.entities.SeckillGoods;
import com.ob.work.seckill.redisdao.SeckillGoodsRedisDao;
import com.ob.work.seckill.repository.SeckillGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:14
 * @Description:
 */
@Service
public class SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository goodsRepository;

    @Autowired
    private SeckillGoodsRedisDao goodsRedisDao;

    /**
     * 新增热点商品
     *
     * @param goodsReqDTO
     * @return
     */
    public SeckillGoodsResDTO addSeckillGoods(SeckillGoodsReqDTO goodsReqDTO) {
        SeckillGoods seckillGoods = goodsReqDTO.seckillGoodsConverter();
        seckillGoods.setOnSaleTime(System.currentTimeMillis());
        seckillGoods.setExpireTime(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        goodsRepository.save(seckillGoods);
        cacheWarmUp(seckillGoods);
        return seckillGoods.convertToSeckillGoodsResDTO();
    }

    /**
     * 简单缓存预热
     *
     * @param goods
     */
    @SuppressWarnings("unchecked")
    private void cacheWarmUp(SeckillGoods goods) {
        long expireTime = goods.getExpireTime() - System.currentTimeMillis();
        goodsRedisDao.saveValueExpireTime(goods.getId(), goods.getId(), expireTime, TimeUnit.MILLISECONDS);
    }


}
