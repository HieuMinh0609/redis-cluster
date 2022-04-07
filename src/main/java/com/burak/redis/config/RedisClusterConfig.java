package com.burak.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@RequiredArgsConstructor
public class RedisClusterConfig {

        private final ClusterConfigProperties clusterConfigurationProperties;

        @Bean
        public JedisConnectionFactory redisConnectionFactory() {



            RedisClusterConfiguration redisClusterConfiguration =
            new RedisClusterConfiguration(clusterConfigurationProperties.getNodes());

            redisClusterConfiguration.setPassword(RedisPassword.of("1234") );

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setMaxWaitMillis(10*1000);

            JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, poolConfig);



            return redisConnectionFactory;
        }

        @Bean
        public RedisTemplate<String, String> redisTemplate() {
            RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory());
            redisTemplate.setKeySerializer(jackson2JsonRedisSerializer());
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
            redisTemplate.setEnableTransactionSupport(true);
            return redisTemplate;
        }

        @Bean
        public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
            return new Jackson2JsonRedisSerializer(String.class);
        }
}
