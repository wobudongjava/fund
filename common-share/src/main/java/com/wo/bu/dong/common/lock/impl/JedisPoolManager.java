package com.wo.bu.dong.common.lock.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wo.bu.dong.common.config.RedisConfig;
import com.wo.bu.dong.common.config.RedisConfig.Pool;
import com.wo.bu.dong.common.exception.SystemException;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
@Slf4j
public class JedisPoolManager {
    private final JedisPool pool;

    private JedisPool init(RedisConfig redisConfig) {
        //初始化连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Pool poolPro = redisConfig.getPool();
        jedisPoolConfig.setMaxTotal(poolPro.getMaxTotal());
        jedisPoolConfig.setMaxIdle(poolPro.getMaxIdle());
        jedisPoolConfig.setMinIdle(poolPro.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(poolPro.getMaxWaitMillis());
        jedisPoolConfig.setBlockWhenExhausted(poolPro.isBlockWhenExhausted());
        jedisPoolConfig.setTestOnBorrow(poolPro.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(poolPro.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(poolPro.isTestWhileIdle());

        return new JedisPool(jedisPoolConfig, redisConfig.getServer(), redisConfig.getPort(), redisConfig.getConnectionTimeout(), redisConfig.getPassword());
    }

    @Autowired
    public JedisPoolManager(RedisConfig redisConfig) {
        pool = init(redisConfig);
    }

    /**
     * 获取jedis
     * 
     * @return
     */
    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
        } catch (Exception e) {
            log.error("getJedis, 获取jedis异常", e);
            throw new SystemException("系统异常-jedis获取失败", e);
        }
        return jedis;
    }

}
