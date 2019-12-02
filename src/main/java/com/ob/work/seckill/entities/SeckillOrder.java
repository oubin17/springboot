package com.ob.work.seckill.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.base.domain.BaseStateDomain;
import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: oubin
 * @Date: 2019/12/2 08:49
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_seckill_order")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class SeckillOrder extends BaseStateDomain<String> {

    /**
     * 用户id
     */
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private String userId;

    /**
     * 商品id
     */
    @JsonProperty("goods_id")
    @Column(name = "goods_id")
    private String goodsId;
}
