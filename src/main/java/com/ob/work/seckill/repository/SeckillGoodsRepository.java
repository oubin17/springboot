package com.ob.work.seckill.repository;

import com.ob.common.base.repository.CustomRepository;
import com.ob.work.seckill.entities.SeckillGoods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: oubin
 * @Date: 2019/12/2 14:03
 * @Description:
 */
public interface SeckillGoodsRepository extends CustomRepository<SeckillGoods, String>, JpaRepository<SeckillGoods, String> {

}
