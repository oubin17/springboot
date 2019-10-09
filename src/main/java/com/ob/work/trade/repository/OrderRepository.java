package com.ob.work.trade.repository;

import com.ob.work.trade.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: oubin
 * @Date: 2019/9/23 14:14
 * @Description:
 */
public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * 失效订单
     *
     * @param orderId
     * @param state
     * @param lastModifiedTime
     * @return
     */
    @Modifying
    @Query(value = "update t_order t set t.state = :state, t.last_modified_time = :last_modified_time where t.id=:id and t.state = 0", nativeQuery = true)
    int expireOrder(@Param("id") String orderId, @Param("state") Integer state, @Param("last_modified_time") Long lastModifiedTime);

}
