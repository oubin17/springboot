package com.ob.work.trade.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.base.domain.BaseStateDomain;
import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: oubin
 * @Date: 2019/9/23 14:18
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_order")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class Order extends BaseStateDomain<String> {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("goods_id")
    private String goodsId;

}
