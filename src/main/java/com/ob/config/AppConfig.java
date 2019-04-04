package com.ob.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: oubin
 * @date: 2019/4/4 11:06
 * @Description:
 */
@EnableTransactionManagement
@EnableCaching
@Configuration
public class AppConfig {
}
