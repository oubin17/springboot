package com.ob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author: oubin
 * @date: 2019/3/27 15:18
 * @Description:
 */
@EnableTransactionManagement
@ComponentScan("com")
@EnableJpaRepositories(basePackages = "com")
@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "com")
@EnableMongoAuditing
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
