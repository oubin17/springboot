package com.ob.work.seckill.repository;

import com.ob.work.seckill.entities.SeckillGoodsInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/12/3 19:04
 * @Description:
 */
public interface SeckillGoodsInventoryRepository extends JpaRepository<SeckillGoodsInventory, String> {

    /**
     * 商品库存-1
     *
     * @param goodsId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update t_seckill_goods_inventory t set t.remaining_quantity = t.remaining_quantity - 1 where t.remaining_quantity > 0 and t.goods_id=:id", nativeQuery = true)
    int decQuantity(@Param("id") String goodsId);
}
