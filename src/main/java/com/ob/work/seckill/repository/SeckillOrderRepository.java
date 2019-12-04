package com.ob.work.seckill.repository;

import com.ob.common.base.repository.CustomRepository;
import com.ob.work.seckill.entities.SeckillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: oubin
 * @Date: 2019/12/4 15:19
 * @Description:
 */
@Repository
public interface SeckillOrderRepository extends CustomRepository<SeckillOrder, String>, JpaRepository<SeckillOrder, String> {
}
