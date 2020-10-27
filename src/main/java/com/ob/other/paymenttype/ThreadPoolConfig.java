package com.ob.other.paymenttype;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: oubin
 * @Date: 2020/9/22 17:06
 * @Description:
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 参数可以写入配置文件中
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor paymentThreadPool() {
        ThreadFactory paymentThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        return new ThreadPoolExecutor(5,
                9,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                paymentThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
