package com.ob.work.seckill.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.base.domain.BaseDomain;
import com.ob.common.base.domain.IdStrategy;
import com.ob.common.convert.BeanConvert;
import com.ob.work.seckill.dto.SeckillGoodsResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @Author: oubin
 * @Date: 2019/12/2 08:43
 * @Description: 商品信息明细表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_seckill_goods")
@GenericGenerator(name = "id", strategy = IdStrategy.UUID)
public class SeckillGoods extends BaseDomain<String> {

    /**
     * 商品名称
     */
    @JsonProperty("goods_name")
    @Column(name = "goods_name")
    private String goodsName;

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

    public SeckillGoodsResDTO convertToSeckillGoodsResDTO() {
        SeckillGoodsResDTOConverter seckillGoodsResDTOConverter = new SeckillGoodsResDTOConverter();
        return seckillGoodsResDTOConverter.convert(this);
    }

    private static class SeckillGoodsResDTOConverter implements BeanConvert<SeckillGoods, SeckillGoodsResDTO> {
        @Override
        public SeckillGoodsResDTO convert(SeckillGoods seckillGoods) {
            SeckillGoodsResDTO goodsResDTO = new SeckillGoodsResDTO();
            BeanUtils.copyProperties(seckillGoods, goodsResDTO);
            goodsResDTO.setId(seckillGoods.getId());
            return goodsResDTO;
        }
    }

}
