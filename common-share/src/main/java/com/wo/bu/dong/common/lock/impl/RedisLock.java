package com.wo.bu.dong.common.lock.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wo.bu.dong.common.exception.LockException;
import com.wo.bu.dong.common.lock.BusinessPrefixEnum;
import com.wo.bu.dong.common.lock.SysLock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * redis锁实现
 */
@Component
@Slf4j
public class RedisLock implements SysLock {
    private static final String  LOCK_SUCCESS              = "OK";
    private static final String  SET_IF_NOT_EXIST          = "NX";
    private static final String  SET_WITH_EXPIRE_TIME_UNIT = "EX";
    private static final Integer LOCK_RELEASE_SUCCESS      = 1;
    private static final Integer DEFAULT_TIMEOUT           = 1;

    @Autowired
    private JedisPoolManager     jedisPoolManager;

    @Override
    public boolean tryLock(String uniqueKey) {
        return tryLock(uniqueKey, 1);
    }

    @Override
    public boolean tryLock(String uniqueKey, int time) {
        String value = Thread.currentThread().getId() + "";
        log.debug("tryLock==> begin,uniqueKey={},value={},time={}", uniqueKey, value, time);
        Jedis jedis = null;
        try {
            jedis = jedisPoolManager.getJedis();
            //nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the keyif it already exist.
            //expx(EX = seconds; PX = milliseconds)
            String result = jedis.set(uniqueKey, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME_UNIT, time);
            log.debug("tryLock, set:result={}", result);
            return LOCK_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("tryLock, 加锁异常,uniqueKey={},value={},time={}", uniqueKey, value, time, e);
            throw new LockException("加锁异常", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean unlock(String uniqueKey) {
        String value = Thread.currentThread().getId() + "";
        log.debug("unlock==> begin, uniqueKey={}", uniqueKey);
        Jedis jedis = null;
        try {
            jedis = jedisPoolManager.getJedis();
            String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            Object result = jedis.eval(luaScript, Collections.singletonList(uniqueKey), Collections.singletonList(value));
            return LOCK_RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            log.error("unlock, 解锁异常,uniqueKey={}", uniqueKey, e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey) {
        return tryLock(uniqueKeyPrefix + "-" + uniqueKey, DEFAULT_TIMEOUT);
    }

    @Override
    public boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, int time) {
        return tryLock(uniqueKeyPrefix + "-" + uniqueKey, time);
    }

    @Override
    public boolean unlock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey) {
        return unlock(uniqueKeyPrefix + "-" + uniqueKey);
    }

}
