package com.ob.work.seckill.repository;

import com.ob.work.seckill.entities.SeckillOrderMQMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: oubin
 * @Date: 2019/12/5 10:12
 * @Description:
 */
@Repository
public interface SeckillOrderMQMsgRepository extends JpaRepository<SeckillOrderMQMsg, String> {
}
