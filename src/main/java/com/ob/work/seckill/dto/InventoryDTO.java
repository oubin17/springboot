package com.ob.work.seckill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: oubin
 * @Date: 2019/12/3 18:59
 * @Description:
 */
@Data
public class InventoryDTO {

    /**
     * 库存数量
     */
    @NotNull
    @JsonProperty("remaining_quantity")
    @Min(value = 1, message = "库存数量不能小于0")
    private Integer remainingQuantity;
}
