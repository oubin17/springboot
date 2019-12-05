package com.ob.work.seckill.enums;

/**
 * @Author: oubin
 * @Date: 2019/12/3 19:46
 * @Description:
 */
public enum OrderStateEnum {

    /**
     * 预下单
     */
    PRE_CREATE(0),

    /**
     * 待付款
     */
    UNPAID(1),

    /**
     * 已付款
     */
    PAID(2),

    /**
     * 已过期
     */
    EXPIRED(3),

    /**
     * 被取消
     */
    CANCELLED(4),

    /**
     * 订单超时未创建
     */
    CREATE_TIMEOUT(5),

    /**
     * 订单成功创建
     */
    CREATE_SUCCEED(6),
    ;

    private Integer state;

    OrderStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
