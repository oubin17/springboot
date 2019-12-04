package com.ob.work.seckill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: oubin
 * @Date: 2019/12/4 19:13
 * @Description:
 */
@Data
public class SeckillOrderResDTO {

    /**
     * 订单id
     */
    private String id;

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
     * 订单状态
     */
    private Integer state;
}
