package com.ob.other.paymenttype;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author: oubin
 * @Date: 2020/9/22 17:28
 * @Description:
 */
@Service
public class PaymentTypeService {

    @Autowired
    private ThreadPoolExecutor paymentThreadPool;


    public List<String> fetchEnablePayType() {

        Map<String, Future<ConsultResult>> futureMap = Maps.newHashMap();
        for (PaymentTypeEnum payType : PaymentTypeEnum.values()) {
            Future<ConsultResult> submit = paymentThreadPool.submit(() -> RemoteCallUtil.checkPayTypeIsEnabled(payType.getTypeName()));
            futureMap.put(payType.name(), submit);
        }
        List<String> enablePayType = Lists.newArrayList();
        Set<Map.Entry<String, Future<ConsultResult>>> entries = futureMap.entrySet();
        for (Map.Entry<String, Future<ConsultResult>> entry : entries) {
            try {
                if (null != entry.getValue().get(1, TimeUnit.SECONDS)) {
                    enablePayType.add(entry.getKey());
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        return enablePayType;
    }
}
