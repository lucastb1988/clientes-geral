package com.clientesgeral.infrastructure.cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(CacheConfigurationProperties.class)
public class CacheConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Value("${spring.redis.cluster.nodes}")
    private List<String> nodes = new ArrayList<>();

    @Bean
    public JedisConnectionFactory redisConnectionFactory(final CacheConfigurationProperties properties) {
        final RedisClusterConfiguration cluster = new RedisClusterConfiguration(nodes);
        return new JedisConnectionFactory(cluster);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(final RedisConnectionFactory cf) {
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory,
        final CacheConfigurationProperties properties) {
        logger.info("caches configurados {}", properties);

        return RedisCacheManagerBuilder
                        .fromConnectionFactory(redisConnectionFactory)
                        .withInitialCacheConfigurations(properties.cacheConfiguration()).build();
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(500);
        jedisPoolConfig.setMaxIdle(15);
        jedisPoolConfig.setMinIdle(3);
        jedisPoolConfig.setMaxWaitMillis(500);
        return jedisPoolConfig;
    }

}
