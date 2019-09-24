package com.ob.work.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:31
 * @Description:
 */
@Data
public class GoodsUpdateDto {

    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
    @JsonProperty("goods_name")
    private String goodsName;

    /**
     * 商品剩余数量
     */
    @Min(value = 1, message = "商品数量不能小于1")
    @JsonProperty("remaining_quantity")
    private Integer remainingQuantity;

    /**
     * 商品上架时间
     */
    @JsonProperty("on_sale_time")
    private Long onSaleTime;

    /**
     * 商品过期时间
     */
    @JsonProperty("expire_time")
    private Long expireTime;
}
