package com.ob.other.paymenttype;

/**
 * @Author: oubin
 * @Date: 2020/9/22 17:04
 * @Description: "balance", "red packet", "discount coupon", "voucher"
 */
public enum PaymentTypeEnum {

    /**
     * 余额
     */
    BALANCE(0, "balance"),

    /**
     * 红包
     */
    RED_PACKET(1, "red packet"),

    /**
     * 优惠券
     */
    DISCOUNT_COUPON(2, "discount coupon"),

    /**
     * 代金券
     */
    VOUCHER(3, "voucher");

    private int type;

    private String typeName;

    PaymentTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

}
