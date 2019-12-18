package com.ob.common.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: oubin
 * @Date: 2019/12/18 11:41
 * @Description:
 */
@Data
@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

    @Value("${druid.driverClassName}")
    private String driverClassName;

    @Value("${druid.url}")
    private String url;

    @Value("${druid.username}")
    private String username;

    @Value("${druid.password}")
    private String password;

    @Value("${druid.initialSize}")
    private int initialSize;

    @Value("${druid.minIdle}")
    private int minIdle;

    @Value("${druid.maxActive}")
    private int maxActive;

    @Value("${druid.maxWait}")
    private int maxWait;

    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${druid.validationQuery}")
    private String validationQuery;

    @Value("${druid.removeAbandoned}")
    private boolean removeAbandoned;

    @Value("${druid.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    @Value("${druid.logAbandoned}")
    private boolean logAbandoned;

    @Value("${druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    /**
     * 自定义数据源
     *
     * @return
     */
    @Bean
    public DataSource datasource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setRemoveAbandoned(removeAbandoned);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setLogAbandoned(logAbandoned);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(datasource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(datasource());
    }

    @Bean
    public Connection connection() throws SQLException {
        return jdbcTemplate().getDataSource().getConnection();
    }

}
