package com.ob.work.trade.service;

import com.ob.common.base.service.CustomService;
import com.ob.work.trade.domain.Goods;
import com.ob.work.trade.dto.GoodsUpdateDto;
import com.ob.work.trade.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:35
 * @Description:
 */
@Service
public class GoodsService extends CustomService<Goods, String> {

    @Autowired
    private GoodsRepository goodsRepository;

    public void addGoodsInfo(GoodsUpdateDto saveDto) {
        Goods goods = new Goods();
        goods.setGoodsName(saveDto.getGoodsName());
        goods.setRemainingQuantity(saveDto.getRemainingQuantity());
        goodsRepository.save(goods);
    }

}
