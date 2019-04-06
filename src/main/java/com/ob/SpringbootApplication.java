package com.ob;

import com.ob.base.repository.CustomJpaRepositoryImpl;
import com.ob.base.repository.CustomMongoRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author: oubin
 * @date: 2019/3/27 15:18
 * @Description:
 */
@ComponentScan("com")
@EnableJpaRepositories(basePackages = "com.ob", repositoryBaseClass = CustomJpaRepositoryImpl.class)
@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "com.ob", repositoryBaseClass = CustomMongoRepositoryImpl.class)
@EnableMongoAuditing
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
