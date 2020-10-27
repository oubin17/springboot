package com.ob.other.paymenttype;

/**
 * @Author: oubin
 * @Date: 2020/9/22 16:28
 * @Description:
 */
public class PaymentRemoteService {

    /**
     * 假设远程服务实现方案：具体实现方案可使用策略模式，用map保存payment和bean的关联关系
     *
     * @param paymentType
     * @return
     */
    public ConsultResult isEnabled(String paymentType) {
        ConsultResult consultResult;
        if (null == paymentType) {
            consultResult = new ConsultResult(false, "");
        } else {
            consultResult = new ConsultResult(paymentType.hashCode() % 2 == 0, "");
        }
        return consultResult;
    }

}
