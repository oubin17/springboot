package com.ob.work.seckill.dto.mq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ob.common.convert.BeanConvert;
import com.ob.work.seckill.entities.SeckillOrderMQMsg;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author: oubin
 * @Date: 2019/12/5 08:45
 * @Description: 订单MQ消息对象，业务方需保证订单id唯一
 */
@Data
public class SeckillOrderMQDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单预创建时间
     */
    @JsonProperty("order_pre_create_time")
    private Long orderPreCreateTime;

    /**
     * 订单创建时间
     */
    @JsonProperty("order_create_time")
    private Long orderCreateTime;

    /**
     * 订单状态
     */
    @JsonProperty("order_state")
    private Integer orderState;

    public SeckillOrderMQMsg convertToSeckillOrderMQ () {
        SeckillOrderMQMsgConverter converter = new SeckillOrderMQMsgConverter();
        return converter.convert(this);
    }

    private static class SeckillOrderMQMsgConverter implements BeanConvert<SeckillOrderMQDTO, SeckillOrderMQMsg> {

        @Override
        public SeckillOrderMQMsg convert(SeckillOrderMQDTO seckillOrderMQDTO) {
            SeckillOrderMQMsg orderMQMsg = new SeckillOrderMQMsg();
            BeanUtils.copyProperties(seckillOrderMQDTO, orderMQMsg);
            return orderMQMsg;
        }
    }
}
