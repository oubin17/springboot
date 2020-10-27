package com.ob.other.payment;

import com.ob.SpringbootApplication;
import com.ob.other.paymenttype.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @Author: oubin
 * @Date: 2020/9/22 18:40
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
public class PaymentTest {

    @Resource
    private PaymentTypeService paymentTypeService;

    @Test
    public void test001() {

        Thread thread1 = new Thread(() -> paymentTypeService.fetchEnablePayType());
        Thread thread2 = new Thread(() -> paymentTypeService.fetchEnablePayType());
        Thread thread3 = new Thread(() -> paymentTypeService.fetchEnablePayType());
        Thread thread4 = new Thread(() -> paymentTypeService.fetchEnablePayType());
        Thread thread5 = new Thread(() -> paymentTypeService.fetchEnablePayType());
        Thread thread6 = new Thread(() -> paymentTypeService.fetchEnablePayType());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();








//        for (int i = 0; i < 10; i++) {
//            List<String> list = paymentTypeService.fetchEnablePayType();
//            log.info("time : " + i);
//            for (String type : list) {
//                log.info(type);
//            }
//        }

    }
}
