package com.ob.work.seckill.dto;

import com.ob.common.convert.BeanConvert;
import com.ob.work.seckill.entities.SeckillGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:35
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SeckillGoodsReqDTO extends SeckillGoodsDTO {


    public SeckillGoods seckillGoodsConverter() {
        SeckillGoodsConverter seckillGoodsConverter = new SeckillGoodsConverter();
        return seckillGoodsConverter.convert(this);
    }

    private static class SeckillGoodsConverter implements BeanConvert<SeckillGoodsReqDTO, SeckillGoods> {
        @Override
        public SeckillGoods convert(SeckillGoodsReqDTO goodsReqDTO) {
            SeckillGoods seckillGoods = new SeckillGoods();
            BeanUtils.copyProperties(goodsReqDTO, seckillGoods);
            return seckillGoods;
        }
    }
}
