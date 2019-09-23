package com.ob.work.trade.repository;

import com.ob.work.trade.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: oubin
 * @Date: 2019/9/23 14:14
 * @Description:
 */
public interface OrderRepository extends JpaRepository<Order, String> {

}
