package com.ajay.zipcodes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig  {

    private  RedisConfigurationProperties configPropertis;

    public RedisConfig(RedisConfigurationProperties configPropertis) {
        this.configPropertis = configPropertis;
    }

    @Bean
        JedisConnectionFactory jedisConnectionFactory()
        {
            RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(configPropertis.getHost());
            redisStandaloneConfiguration.setPort(Integer.parseInt(configPropertis.getPort()));
         //  redisStandaloneConfiguration.setPassword(configPropertis.getPassword()); if password is set
            JedisClientConfiguration.JedisClientConfigurationBuilder configurationBuilder=JedisClientConfiguration.builder();
            JedisPoolConfig poolConfig=new JedisPoolConfig();
            poolConfig.setMaxTotal(Integer.parseInt(configPropertis.getMax_active()));
            poolConfig.setMaxIdle(Integer.parseInt(configPropertis.getMax_idle()));
            poolConfig.setMinIdle(Integer.parseInt(configPropertis.getMin_idle()));
            configurationBuilder.usePooling().poolConfig(poolConfig);
            JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(redisStandaloneConfiguration,configurationBuilder.build());

            // we can do like this if threads  not needed

            //JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
//            factory.setPassword("password");
//            factory.setHostName("127.0.0.1");
//            factory.setPort(6379);

return  jedisConnectionFactory;
        }
        @Bean("jedis")
        RedisTemplate<String,Object> redisTemplate()
        {
            RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
            redisTemplate.setConnectionFactory(jedisConnectionFactory());
            RedisSerializationContext.SerializationPair<Object> jsonSerializer = RedisSerializationContext.SerializationPair
                    .fromSerializer(new GenericJackson2JsonRedisSerializer());
            redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
            redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate.setEnableTransactionSupport(true);
            return  redisTemplate;

        }

    // we dont need the factory bean for lettuce , spring provides it , just mention spring.redis.variables in application.yaml
//    @Bean("lettuce")
//    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
//    {
//        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setEnableTransactionSupport(true);
//        return  redisTemplate;
//
//    }

    //this bean not needed as spring data redis  gives default one for us
    //       @Bean
//        public LettuceConnectionFactory redisConnectionFactory() {
//
//            return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
//        }
    }

