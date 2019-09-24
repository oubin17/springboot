package com.ob.work.trade.service;

import com.ob.common.base.service.CustomService;
import com.ob.common.constant.Constants;
import com.ob.work.trade.domain.Goods;
import com.ob.work.trade.dto.GoodsUpdateDto;
import com.ob.work.trade.enums.GoodsStateEnum;
import com.ob.work.trade.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:35
 * @Description:
 */
@Service
public class GoodsService extends CustomService<Goods, String> {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ValueOperations valueOperations;

    /**
     * 简化流程,创建商品即开售,抢购期为1天
     *
     * @param saveDto
     */
    public Goods addGoodsInfo(GoodsUpdateDto saveDto) {
        Goods goods = new Goods();
        goods.setGoodsName(saveDto.getGoodsName());
        goods.setRemainingQuantity(saveDto.getRemainingQuantity());
        goods.setOnSaleTime(System.currentTimeMillis());
        goods.setExpireTime(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        goods.setState(GoodsStateEnum.ONSALE.getState());
        goodsRepository.save(goods);
        cacheWarmUp(goods);
        return goods;
    }

    /**
     * 简单缓存预热
     *
     * @param goods
     */
    @SuppressWarnings("unchecked")
    private void cacheWarmUp(Goods goods) {
        long expireTime = goods.getExpireTime() - System.currentTimeMillis();
        valueOperations.set(Constants.GOODS_ID_KEY + goods.getId(), goods.getRemainingQuantity(), expireTime, TimeUnit.MILLISECONDS);
    }


}
