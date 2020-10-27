package com.ob.other.paymenttype;

/**
 * @Author: oubin
 * @Date: 2020/9/22 16:22
 * @Description:
 */
public class ConsultResult {

    // 用户有多种支付方式（余额、红包、优惠券，代金券等），假如每种支付方式通过调用远程服务获取可用性。
    // 在外部资源环境不变情况下，请设计程序以最短响应时间获得尽可能多的可用支付方式列表。
    // 假定支付方式可用性咨询接口定义：PaymentRemoteService
    // 接口方法:isEnabled(String paymentType);
    // 返回结果：ConsultResult

    public ConsultResult(boolean isEnable, String errorCode) {
        this.isEnable = isEnable;
        this.errorCode = errorCode;
    }

    /**
     * 咨询结果是否可用
     */
    private boolean isEnable;
    /**
     * 错误码
     */
    private String errorCode;

    public boolean getIsEnable() {
        return isEnable;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
