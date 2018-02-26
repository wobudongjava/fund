package com.wo.bu.dong.common.lock.impl;

import com.wo.bu.dong.common.lock.BusinessPrefixEnum;
import com.wo.bu.dong.common.lock.MyLock;

/**
 * redis锁实现
 */
public class RedisLock implements MyLock {

    @Override
    public boolean tryLock(String uniqueKey, String value, int time) {
        return false;
    }

    @Override
    public boolean unlock(String uniqueKey, String value) {
        return false;
    }

    @Override
    public boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value, int time) {
        return false;
    }

    @Override
    public boolean unlock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value) {
        return false;
    }

}
