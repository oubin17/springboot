package com.ob.work.trade.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.base.domain.BaseStateDomain;
import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:03
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_goods")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class Goods extends BaseStateDomain<String> {

    /**
     * 商品名称
     */
    @JsonProperty("goods_name")
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 商品剩余数量
     */
    @JsonProperty("remaining_quantity")
    @Column(name = "remaining_quantity")
    private Integer remainingQuantity;

    /**
     * 商品上架时间
     */
    @JsonProperty("on_sale_time")
    @Column(name = "on_sale_time")
    private Long onSaleTime;

    /**
     * 商品过期时间
     */
    @JsonProperty("expire_time")
    @Column(name = "expire_time")
    private Long expireTime;

    @Version
    @Column(name = "version")
    private Integer version;
}
