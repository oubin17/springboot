package com.ob.work.seckill.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:28
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeckillGoodsDTO extends InventoryDTO {

    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
    @JsonProperty("goods_name")
    private String goodsName;

    /**
     * 商品上架时间
     */
    @JsonProperty("on_sale_time")
    @NotNull
    private Long onSaleTime;

    /**
     * 商品过期时间
     */
    @JsonProperty("expire_time")
    @NotNull
    private Long expireTime;

    public void checkProperties() {
        if (onSaleTime <= System.currentTimeMillis()) {
            throw new BizException(ErrorCode.ON_SALE_TIME_NOT_CORRECT);
        }
        if (expireTime <= System.currentTimeMillis() || expireTime <= onSaleTime) {
            throw new BizException(ErrorCode.EXPIRE_TIME_NOT_CORRECT);
        }

    }
}
