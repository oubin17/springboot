package com.ob.work.trade.repository;

import com.ob.common.base.repository.CustomRepository;
import com.ob.work.trade.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:20
 * @Description:
 */
public interface GoodsRepository extends CustomRepository<Goods, String>, JpaRepository<Goods, String> {

    /**
     * 使用sql操作原子性创建一笔订单
     *
     * @param goodsId
     * @return
     */
    @Modifying
    @Query(value = "update t_goods t set t.remaining_quantity = t.remaining_quantity - 1, t.version = t.version + 1 where t.remaining_quantity > 0 and t.id=:id", nativeQuery = true)
    int createOrder(@Param("id") String goodsId);

    /**
     * 使用数据库乐观锁创建一笔订单
     *
     * @param id
     * @param remainingQuantity
     * @return
     */
    Goods findFirstByIdAndRemainingQuantityGreaterThan(String id, Integer remainingQuantity);

    /**
     * 更新商品属性
     *
     * @param goodsId
     * @param goodsName
     * @return
     */
    @Modifying
    @Query(value = "update t_goods t set t.goods_name = :goods_name, t.version = t.version + 1 where t.id=:id", nativeQuery = true)
    int updateByGoodsName(@Param("id") String goodsId, @Param("goods_name") String goodsName);
}
