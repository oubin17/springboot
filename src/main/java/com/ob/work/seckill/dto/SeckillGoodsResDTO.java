package com.ob.work.seckill.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:39
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SeckillGoodsResDTO extends SeckillGoodsDTO {

    /**
     * 主键id
     */
    private String id;
}
