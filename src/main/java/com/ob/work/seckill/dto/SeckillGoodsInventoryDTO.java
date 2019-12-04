package com.ob.work.seckill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: oubin
 * @Date: 2019/12/3 19:36
 * @Description:
 */
@Data
public class SeckillGoodsInventoryDTO {

    /**
     * 用户id
     */
    @JsonProperty("user_id")
    private String userId;

    /**
     * 商品id
     */
    @JsonProperty("goods_id")
    private String goodsId;

    /**
     * 用于记录状态
     */
    @JsonProperty("state")
    private Integer state;

}
