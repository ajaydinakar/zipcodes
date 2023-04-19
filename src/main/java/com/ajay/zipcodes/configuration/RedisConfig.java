package com.ajay.zipcodes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig  {

//this works redis with jedis
//        @Bean
//        JedisConnectionFactory jedisConnectionFactory()
//        {
//            RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
//            redisStandaloneConfiguration.setHostName("127.0.0.1");
//            redisStandaloneConfiguration.setPort(6379);
//           redisStandaloneConfiguration.setPassword("password");
//            JedisClientConfiguration.JedisClientConfigurationBuilder configurationBuilder=JedisClientConfiguration.builder();
//            JedisPoolConfig poolConfig=new JedisPoolConfig();
//            poolConfig.setMaxTotal(100);
//            poolConfig.setMaxIdle(-1);
//            poolConfig.setMinIdle(-1);
//            configurationBuilder.usePooling().poolConfig(poolConfig);
//            JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(redisStandaloneConfiguration,configurationBuilder.build());
//            JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
//            factory.setPassword("password");
//            factory.setHostName("127.0.0.1");
//            factory.setPort(6379);

//return  jedisConnectionFactory;
//        }
//        @Bean("jedis")
//        RedisTemplate<String,Object> redisTemplate()
//        {
//            RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//            redisTemplate.setConnectionFactory(jedisConnectionFactory());
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
//            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//            redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//            redisTemplate.setEnableTransactionSupport(true);
//            return  redisTemplate;
//
//        }

    // we dont need the factory bean for lettuce , spring provides it , just mention spring.redis.variables in application.yaml
    @Bean("lettuce")
    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return  redisTemplate;

    }

    //        @Bean
//        public LettuceConnectionFactory redisConnectionFactory() {
//
//            return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
//        }
    }

