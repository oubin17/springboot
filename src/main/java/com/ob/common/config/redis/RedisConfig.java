package com.ob.common.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: oubin
 * @Date: 2019/7/8 15:43
 * @Description:
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        if (StringUtils.isEmpty(password)) {
            return new JedisPool(jedisPoolConfig, host, port, timeout);
        } else {
            return new JedisPool(jedisPoolConfig, host, port, timeout, password);
        }
    }

    /**
     * 自定义redisTemplate
     *
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置redis连接工厂
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jacksonSerializer.setObjectMapper(objectMapper);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        //key采用String的序列化方式
        template.setKeySerializer(stringSerializer);
        //value采用GenericJackson2JsonRedisSerializer序列化方式
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//        //hash的key采用jackson序列化方式
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        //设置支持事务
//        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();
        return template;

    }

    /**
     * redis操作value
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * redis操作hash
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * redis操作list
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * redis操作set
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * redis操作zset
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    /**
     * 配置jedis连接池
     *
     * @param hostName
     * @param password
     * @param port
     * @param maxActive
     * @param maxIdle
     * @param minIdle
     * @param maxWait
     * @param database
     * @return
     */
    public RedisConnectionFactory redisConnectionFactory(String hostName, String password, int port,
                                                         int maxActive, int maxIdle, int minIdle, long maxWait, int database) {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setDatabase(database);
        // 进行连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        jedisConnectionFactory.setPoolConfig(poolConfig);
        // 初始化连接池配置
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    /**
     * 返回redis连接工厂
     *
     * @param hostName
     * @param password
     * @param port
     * @param database
     * @param maxActive
     * @param maxIdle
     * @param minIdle
     * @param maxWait
     * @return
     */
    public RedisConnectionFactory redisConnectionFactorySelfConfig(
            @Value("${spring.redis.host}") String hostName,
            @Value("${spring.redis.password}") String password,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.database}") int database,
            @Value("${spring.redis.pool.max-active}") int maxActive,
            @Value("${spring.redis.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.pool.min-idle}") int minIdle,
            @Value("${spring.redis.pool.max-wait}") long maxWait) {
        return this.redisConnectionFactory(
                hostName, password, port, maxActive, maxIdle, minIdle, maxWait,
                database);
    }
}
