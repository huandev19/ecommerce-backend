package com.v8n.modules.identity.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // ── Shared connection parameters ──
    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.username:}")
    private String username;

    @Value("${spring.data.redis.password:}")
    private String password;

    // ── Helper to create a LettuceConnectionFactory for a given DB index ──
    private RedisConnectionFactory createConnectionFactory(int database) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);
        if (!username.isBlank()) {
            config.setUsername(username);
        }
        if (!password.isBlank()) {
            config.setPassword(RedisPassword.of(password));
        }
        return new LettuceConnectionFactory(config);
    }

    // ── Helper to create a String-serialized RedisTemplate ──
    private RedisTemplate<String, String> createTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    // ════════════════════════════════════════════════════
    //  DB 0 — Default (token blacklist, general cache)
    // ════════════════════════════════════════════════════
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        return createConnectionFactory(0);
    }

    @Bean
    @Primary
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        return createTemplate(redisConnectionFactory);
    }

    // ════════════════════════════════════════════════════
    //  DB 1 — Cart
    // ════════════════════════════════════════════════════
    @Bean
    public RedisConnectionFactory redisConnectionFactoryDb1() {
        return createConnectionFactory(1);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateDb1(
            RedisConnectionFactory redisConnectionFactoryDb1) {
        return createTemplate(redisConnectionFactoryDb1);
    }

    // ════════════════════════════════════════════════════
    //  DB 2 — Session
    // ════════════════════════════════════════════════════
    @Bean
    public RedisConnectionFactory redisConnectionFactoryDb2() {
        return createConnectionFactory(2);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateDb2(
            RedisConnectionFactory redisConnectionFactoryDb2) {
        return createTemplate(redisConnectionFactoryDb2);
    }
}
