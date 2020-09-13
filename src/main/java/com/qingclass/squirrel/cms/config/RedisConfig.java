package com.qingclass.squirrel.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置类
 *
 * @author zcc ON 2018/3/19
 **/
@Configuration
@EnableCaching//开启注解
public class RedisConfig {
    @Bean(name = "jedisPool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig config,
                               @Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port,
                               @Value("${spring.redis.timeout}") int timeout, @Value("${spring.redis.password}") String password) {
        if(!StringUtils.isEmpty(password)) {
            return new JedisPool(config, host, port, timeout, password);
        }else {
            return new JedisPool(config,host,port,timeout);
        }

    }

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.pool.max-active}") int maxTotal,
                                           @Value("${spring.redis.pool.max-idle}") int maxIdle,
                                           @Value("${spring.redis.pool.max-wait}") int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }
}