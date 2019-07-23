package com.ob.other.beanname;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/7/11 09:08
 * @Description:
 */
@Component
public class Config {

//    @Bean
//    public A a() {
//        return new A();
//    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(A.class)
    public A a() {
        return new A("AAA");
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(B.class)
    public B b() {
        return new B("BBB");
    }
}
