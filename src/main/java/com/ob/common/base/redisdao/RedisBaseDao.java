package com.ob.common.base.redisdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/2 17:37
 * @Description:
 */
@Repository
public class RedisBaseDao {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected HashOperations<String, String, Object> hashOperations;

    @Autowired
    protected ZSetOperations<String, Object> zsetOperations;

    @Autowired
    protected ValueOperations<String, Object> valueOperations;

    @Autowired
    protected SetOperations<String, Object> setOperations;

    @Autowired
    protected ListOperations<String, Object> listOperations;

    //---------------------------------------------------------------------
    // redisTemplate
    //---------------------------------------------------------------------

    /**
     * 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 是否存在
     *
     * @param key
     * @param hashKey
     */
    public boolean hasKey(String key, String hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    /**
     * 获取超时时间
     *
     * @param key
     * @param unit
     * @return:
     */
    public Long getExpire(String key, final TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 所有keys
     *
     * @author
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除keys
     *
     * @author
     */
    public void delete(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 删除key
     *
     * @author
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    public long getKeyExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }

    //---------------------------------------------------------------------
    // Key -> Redis Redis Value 操作
    //---------------------------------------------------------------------

    /**
     * 设置value的同时，设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     */
    public void setKeyWithExpireTime(String key, Object value, long expireTime, TimeUnit timeUnit) {
        valueOperations.set(key, value, expireTime, timeUnit);
    }

    /**
     * 添加value
     *
     * @param key
     * @param value
     */
    public void addValue(String key, Object value) {
        valueOperations.set(key, value);
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return valueOperations.get(key);
    }

    /**
     * 自增value值
     *
     * @param key
     * @param delta
     * @return
     */
    public Long incValue(String key, long delta) {
        return valueOperations.increment(key, delta);
    }

    //---------------------------------------------------------------------
    // HashOperations -> Redis Redis Hash 操作
    //---------------------------------------------------------------------

    /**
     * 添加单个数据
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void addHashValue(String key, String hashKey, String value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * 添加单个数据
     *
     * @param key
     * @param m
     */
    public void addBatchHashValue(String key, Map<String, String> m) {
        hashOperations.putAll(key, m);
    }

    /**
     * 移除数据
     *
     * @param key
     */
    public void removeKey(String key) {
        Map<String, Object> hash = hashOperations.entries(key);
        String[] s = new String[hash.size()];
        hash.keySet().toArray(s);
        if (s.length > 0) {
            hashOperations.delete(key, s);
        }
    }

    /**
     * 自增hash值
     *
     * @param hashKey
     * @param delta
     */
    public Long incHashValue(String key, String hashKey, long delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    /**
     * 删除单个数据
     *
     * @param key
     * @param hashKey
     */
    public void deleteHashValue(String key, String hashKey) {
        hashOperations.delete(key, hashKey);
    }

    /**
     * 删除多个数据
     *
     * @param key
     * @param hashKeys
     */
    public void deleteHashValues(String key, Set<String> hashKeys) {
        hashOperations.delete(key, hashKeys.toArray());
    }

    /**
     * 获取单个数据
     *
     * @param key
     * @param hashKey
     */
    public Object getHashValue(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }


    /**
     * 批量获取hash的所有value值
     *
     * @param key Param-key
     * @author
     */
    public List<Object> getHashAllValue(String key) {
        List<Object> values = hashOperations.values(key);
        return values;
    }

    /**
     * 批量获取hkey
     *
     * @param key
     */
    public Set<String> getHKeys(String key) {
        return hashOperations.keys(key);
    }

    /**
     * 批量获取 数据
     *
     * @param key
     * @param hashKeys
     */
    public List<Object> getHashMultiValue(String key, List<String> hashKeys) {
        List<Object> values = hashOperations.multiGet(key, hashKeys);
        return values;
    }

    /**
     * 获取hash数量
     *
     * @param key
     * @author
     */
    public Long getHashCount(String key) {
        return hashOperations.size(key);
    }


    //---------------------------------------------------------------------
    // SetOperations -> Redis Set 操作
    //---------------------------------------------------------------------

    /**
     * set添加元素
     *
     * @param key
     * @param member
     * @return
     */
    public Long addSetValue(String key, String member) {
        return setOperations.add(key, member);
    }

    /**
     * 是否是set中元素
     *
     * @param key
     * @param member
     * @return
     */
    public boolean isSetMember(String key, String member) {
        return setOperations.isMember(key, member);
    }

    /**
     * 获取set大小
     *
     * @param key
     * @return
     */
    public Long getSetSize(String key) {
        return setOperations.size(key);
    }

    /**
     * 获取zset数量
     *
     * @param key
     */
    public Set<Object> getSetMembers(String key) {
        return setOperations.members(key);
    }

    /**
     * 删除set
     *
     * @param key
     * @param member
     * @return
     */
    public Long deleteSetValue(String key, String member) {
        return setOperations.remove(key, member);
    }

    /**
     * 批量添加set
     *
     * @param key
     * @param values
     * @return
     */
    public Long addBatchSetValue(String key, Set<String> values) {
        String[] tmp = new String[values.size()];
        String[] members = values.toArray(tmp);
        return setOperations.add(key, members);

    }

    /**
     * 如果不存在，就set
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, String value) {
        return valueOperations.setIfAbsent(key, value);
    }


    //---------------------------------------------------------------------
    // ListOperations -> Redis List 操作
    //---------------------------------------------------------------------

    /**
     * 列表左边推入值
     *
     * @param key
     * @param value
     * @return:
     */
    public Long leftPush(String key, String value) {
        return this.listOperations.leftPush(key, value);
    }

    /**
     * 列表左边批量推入值
     *
     * @param key
     * @param valueList
     * @return:
     */
    public Long leftPush(String key, List<String> valueList) {
        return this.listOperations.leftPushAll(key, valueList);
    }

    /**
     * 列表右边推入值
     *
     * @param key
     * @param value
     * @return:
     */
    public Long rightPush(String key, String value) {
        return this.listOperations.rightPush(key, value);
    }

    /**
     * 列表右边批量推入值
     *
     * @param key
     * @param valueList
     * @return:
     */
    public Long rightPush(String key, List<String> valueList) {
        return this.listOperations.rightPushAll(key, valueList);
    }

    /**
     * 列表左边弹出值
     *
     * @param key
     * @return:
     */
    public Object leftPop(String key) {
        return this.listOperations.leftPop(key);
    }

    /**
     * 列表左边弹出值-block
     *
     * @param key
     * @return:
     */
    public Object leftPop(String key, Long timeout, TimeUnit unit) {
        return this.listOperations.leftPop(key, timeout, unit);
    }

    /**
     * 列表右边弹出值
     *
     * @param key
     * @return:
     */
    public Object rightPop(String key) {
        return this.listOperations.rightPop(key);
    }

    /**
     * 列表右边弹出值-block
     *
     * @param key
     * @return:
     */
    public Object rightPop(String key, Long timeout, TimeUnit unit) {
        return this.listOperations.rightPop(key, timeout, unit);
    }

    /**
     * 获取列表值得数量
     *
     * @param key
     * @return:
     */
    public Long listSize(String key) {
        return this.listOperations.size(key);
    }


    //---------------------------------------------------------------------
    // ListOperations -> Redis ZSet 操作
    //---------------------------------------------------------------------


    /**
     * 设置zset值
     *
     * @param key
     * @param member
     * @param score
     */
    public boolean addZSetValue(String key, String member, long score) {
        return zsetOperations.add(key, member, score);
    }

    /**
     * 设置zset值
     *
     * @param key
     * @param member
     * @param score
     */
    public boolean addZSetValue(String key, String member, double score) {
        return zsetOperations.add(key, member, score);
    }

    /**
     * 批量设置zset值
     *
     * @param key
     * @param tuples
     */
    public long addBatchZSetValue(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return zsetOperations.add(key, tuples);
    }

    /**
     * 自增zset值
     *
     * @param key
     * @param member
     * @param delta
     */
    public void incZSetValue(String key, String member, long delta) {
        zsetOperations.incrementScore(key, member, delta);
    }

    /**
     * 获取zset数量
     *
     * @param key
     * @param member
     */
    public long getZSetScore(String key, String member) {
        Double score = zsetOperations.score(key, member);
        if (score == null) {
            return 0;
        } else {
            return score.longValue();
        }
    }

    /**
     * 获取zset数量
     *
     * @param key
     * @param member
     */
    public double getZSetScore4Double(String key, String member) {
        Double score = zsetOperations.score(key, member);
        if (score == null) {
            return 0;
        } else {
            return score;
        }
    }

    /**
     * 删除zset值
     *
     * @param key
     * @param member
     */
    public long deleteZSetValue(String key, String member) {
        return zsetOperations.remove(key, member);
    }

    /**
     * 获取有序集 key 中成员 member 的排名 。其中有序集成员按 score 值递减 (从小到大) 排序。
     *
     * @param key
     * @param start
     * @param end
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRank(String key, long start, long end) {
        return zsetOperations.rangeWithScores(key, start, end);
    }

    /**
     * 获取有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减 (从大到小) 排序。
     *
     * @param key
     * @param start
     * @param end
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRevRank(String key, long start, long end) {
        return zsetOperations.reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取zset数量
     *
     * @param key
     */
    public long getZSetCount(String key) {
        return zsetOperations.size(key);
    }

    /**
     * 按排名批量删除zset
     *
     * @param key
     * @param start
     * @param end
     */
    public long removeBatchZSetRank(String key, long start, long end) {
        return zsetOperations.removeRange(key, start, end);
    }

    /**
     * 批量删除zet排行member
     *
     * @param key
     * @param memberList
     */
    public long removeBatchZSet(String key, List<String> memberList) {
        return zsetOperations.remove(key, memberList);
    }


}
