package com.ob.work.seckill.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Author: oubin
 * @Date: 2019/12/5 09:56
 * @Description:
 */
@Data
@Entity
@Table(name = "t_seckill_order_mq_msg")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class SeckillOrderMQMsg {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 36)
    private String id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单状态
     */
    @JsonProperty("order_state")
    @Column(name = "order_state")
    private Integer orderState;

    /**
     * 订单预创建时间
     */
    @JsonProperty("order_pre_create_time")
    @Column(name = "order_pre_create_time")
    private Long orderPreCreateTime;

    /**
     * 订单创建时间
     */
    @JsonProperty("order_create_time")
    @Column(name = "order_create_time")
    private Long orderCreateTime;

}
