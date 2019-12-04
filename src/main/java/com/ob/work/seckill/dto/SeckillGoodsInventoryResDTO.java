package com.ob.work.seckill.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: oubin
 * @Date: 2019/12/3 19:37
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SeckillGoodsInventoryResDTO extends SeckillGoodsInventoryDTO {

    /**
     * id
     */
    private String id;
}
