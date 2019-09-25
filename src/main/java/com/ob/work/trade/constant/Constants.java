package com.ob.work.trade.constant;

/**
 * @Author: oubin
 * @Date: 2019/9/25 10:32
 * @Description:
 */
public final class Constants {

    /**
     * redis 中key为商品id
     */
    public static final String GOODS_ID_KEY = "goods:id:";

    /**
     * 商品key的分布式锁id
     */
    public static final String GOODS_ID_LOCK = "goods:id:lock:";

    /**
     * 商品详情key
     */
    public static final String GOODS_INFO_KEY = "goods:info:";
}
