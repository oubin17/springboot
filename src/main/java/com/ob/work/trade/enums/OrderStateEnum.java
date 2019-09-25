package com.ob.work.trade.enums;

/**
 * @Author: oubin
 * @Date: 2019/9/24 16:06
 * @Description:
 */
public enum OrderStateEnum {

    /**
     * 待付款
     */
    UNPAID(0),

    /**
     * 已付款
     */
    PAID(1),

    /**
     * 已过期
     */
    EXPIRED(2);

    private int state;

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
