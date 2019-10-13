package com.ob.modelexample.business.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/4/23 16:29
 * @Description: 异步线程和调用线程不要写在同一个类当中
 */
@Component
@Slf4j
public class AsyncUtils {

    @Async("taskExecutor")
    public void asyncFunc() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步线程测试...");
    }
}
