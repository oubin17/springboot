package com.ob.work.trade.enums;

/**
 * @Author: oubin
 * @Date: 2019/9/24 11:35
 * @Description:
 */
public enum GoodsStateEnum {

    /**
     * 未开卖
     */
    UNSOLD(0),

    /**
     * 销售中
     */
    ONSALE(1),

    /**
     * 已过期
     */
    EXPIRED(2);

    private int state;

    GoodsStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
