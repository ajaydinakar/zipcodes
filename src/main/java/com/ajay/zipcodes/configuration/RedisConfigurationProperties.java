package com.ajay.zipcodes.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfigurationProperties {
    private   String host;
    private String port;
    private String password;
    private String max_active;
    private String max_idle;
    private String min_idle;

}



