package com.wo.bu.dong.common.lock;

public interface MyLock {

    /**
     * 默认有效时间1秒
     * 
     * @param uniqueKey 唯一键
     * @param value 值
     * @return true:锁成功 false:锁失败
     */
    boolean tryLock(String uniqueKey, String value);

    /**
     * @param uniqueKey 唯一键
     * @param value 值
     * @param time 过期时间（单位：秒）
     * @return true:锁成功 false:锁失败
     */
    boolean tryLock(String uniqueKey, String value, int time);

    /**
     * @param uniqueKey 唯一键
     * @return true:解锁成功 false:解锁失败
     */
    boolean unlock(String uniqueKey);

    /**
     * 默认有效时间1秒
     * 
     * @param uniqueKeyPrefix 唯一键前缀
     * @param uniqueKey 唯一键
     * @param value 值
     * @return true:锁成功 false:锁失败
     */
    boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value);

    /**
     * @param uniqueKeyPrefix 唯一键前缀
     * @param uniqueKey 唯一键
     * @param value 值
     * @param time 过期时间（单位：秒）
     * @return true:锁成功 false:锁失败
     */
    boolean tryLock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey, String value, int time);

    /**
     * @param uniqueKeyPrefix 唯一键前缀
     * @param uniqueKey 唯一键
     * @return true:解锁成功 false:解锁失败
     */
    boolean unlock(BusinessPrefixEnum uniqueKeyPrefix, String uniqueKey);

}
