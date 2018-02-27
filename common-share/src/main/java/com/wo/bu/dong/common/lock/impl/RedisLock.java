package com.wo.bu.dong.common.lock.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wo.bu.dong.common.exception.LockException;
import com.wo.bu.dong.common.lock.BusinessPrefixEnum;
import com.wo.bu.dong.common.lock.MyLock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * redis锁实现
 */
@Component
@Slf4j
public class RedisLock implements MyLock {

    @Autowired
    private JedisPoolManager jedisPoolManager;

    @Override
    public boolean tryLock(String uniqueKey, String value) {
        return tryLock(uniqueKey, value, 1);
    }

    @Override
    public boolean tryLock(String uniqueKey, String value, int time) {
        log.debug("tryLock==> begin,uniqueKey={},value={},time={}", uniqueKey, value, time);
        Jedis jedis = null;
        try {
            jedis = jedisPoolManager.getJedis();
            //nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the keyif it already exist.
            //expx(EX = seconds; PX = milliseconds)
            String result = jedis.set(uniqueKey, value, "NX", "EX", time);
            log.debug("tryLock, set:result={}", result);
            log.debug("tryLock==> end");
            return "OK".equals(result);
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
        log.debug("unlock==> begin, uniqueKey={}", uniqueKey);
        Jedis jedis = null;
        try {
            jedis = jedisPoolManager.getJedis();
            Long result = jedis.del(uniqueKey);
            log.debug("tryLock, del:result={}", result);
            log.debug("unlock==> end");
            return result > 0;
        } catch (Exception e) {
            log.error("tryLock, 解锁异常,uniqueKey={}", uniqueKey);
            throw new LockException("解锁异常", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value) {
        return tryLock(uniqueKeyPrefix + "-" + uniqueKey, value, 1);
    }

    @Override
    public boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value, int time) {
        return tryLock(uniqueKeyPrefix + "-" + uniqueKey, value, time);
    }

    @Override
    public boolean unlock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey) {
        return unlock(uniqueKeyPrefix + "-" + uniqueKey);
    }

}
