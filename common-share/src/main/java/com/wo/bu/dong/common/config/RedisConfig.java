package com.wo.bu.dong.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
@Component
public class RedisConfig {
    private String     server;
    private int        port;
    private String     password;
    private int        connectionTimeout;

    private final Pool pool = new Pool();

    @Getter
    @Setter
    public static class Pool {
        private int     maxTotal;
        private int     maxIdle;
        private int     minIdle;
        private long    maxWaitMillis;
        private boolean blockWhenExhausted;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean testWhileIdle;

    }
}
