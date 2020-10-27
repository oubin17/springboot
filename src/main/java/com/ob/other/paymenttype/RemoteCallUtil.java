package com.ob.other.paymenttype;

/**
 * @Author: oubin
 * @Date: 2020/9/22 17:33
 * @Description:
 */
public class RemoteCallUtil {

    private RemoteCallUtil() {

    }

    /**
     * 通过远程调用PaymentRemoteService获取返回值，这里简单返回结果，还需要补足状态码信息
     *
     * @param paymentType
     * @return
     */
    public static ConsultResult checkPayTypeIsEnabled(String paymentType) {
        PaymentRemoteService paymentRemoteService = new PaymentRemoteService();
        ConsultResult consultResult;
        try {
            consultResult = paymentRemoteService.isEnabled(paymentType);
            if (null == consultResult) {
                consultResult = new ConsultResult(false, "");
            }
        } catch (Exception e) {
            consultResult = new ConsultResult(false, "");
        }
        return consultResult;

    }
}
