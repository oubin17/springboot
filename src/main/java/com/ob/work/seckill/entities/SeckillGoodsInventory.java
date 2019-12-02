package com.ob.work.seckill.entities;

import com.ob.common.base.domain.IdStrategy;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Author: oubin
 * @Date: 2019/12/2 08:49
 * @Description: 商品库存表
 */
@Data
@Entity
@Table(name = "t_seckill_goods_inventory")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class SeckillGoodsInventory {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 36)
    private String id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品剩余数量
     */
    @Column(name = "remaining_quantity")
    private Integer remainingQuantity;
}
