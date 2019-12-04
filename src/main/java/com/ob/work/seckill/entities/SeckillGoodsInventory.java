package com.ob.work.seckill.entities;

import com.ob.common.base.domain.IdStrategy;
import com.ob.common.convert.BeanConvert;
import com.ob.work.seckill.dto.SeckillGoodsInventoryResDTO;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

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

    public SeckillGoodsInventoryResDTO convertTOSeckillGoodsInventoryResDTO() {
        SeckillGoodsInventoryResDTOConverter converter = new SeckillGoodsInventoryResDTOConverter();
        return converter.convert(this);
    }

    private static class SeckillGoodsInventoryResDTOConverter implements BeanConvert<SeckillGoodsInventory, SeckillGoodsInventoryResDTO> {
        @Override
        public SeckillGoodsInventoryResDTO convert(SeckillGoodsInventory seckillGoodsInventory) {
            SeckillGoodsInventoryResDTO resDTO = new SeckillGoodsInventoryResDTO();
            BeanUtils.copyProperties(seckillGoodsInventory, resDTO);
            return resDTO;
        }
    }
}
