package com.ob.other.beanname;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/7/11 09:27
 * @Description:
 */
@Component
public class ConfigX {

    @Bean
    public B b() {
        return new B("b");
    }
}
