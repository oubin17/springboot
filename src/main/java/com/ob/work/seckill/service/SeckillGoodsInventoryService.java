package com.ob.work.seckill.service;

import com.ob.work.seckill.redisdao.SeckillGoodsInventoryRedisDao;
import com.ob.work.seckill.repository.SeckillGoodsInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/12/4 19:05
 * @Description:
 */
@Service
public class SeckillGoodsInventoryService {

    @Autowired
    private SeckillGoodsInventoryRedisDao inventoryRedisDao;

    @Autowired
    private SeckillGoodsInventoryRepository inventoryRepository;


    /**
     * 返回是否还有剩余库存:如果redis找不到，找数据库，写缓存
     *
     * @param goodsId
     * @return
     */
    public Boolean decQuantity(String goodsId) {
        Long val = inventoryRedisDao.decValue(goodsId);
        if (null != val && val >= 0) {
            //这里说明成功抢到商品，需要将数据库库存-1
            int left = inventoryRepository.decQuantity(goodsId);
            if (left > 0) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
