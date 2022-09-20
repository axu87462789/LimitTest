package com.guapixu.limit.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lizx
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 写入一个缓存
     * @param key 键
     * @param value 数据
     */
    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 写入一个缓存
     * @param key 键
     * @param value 数据
     * @param expireSeconds 过期秒数
     */
    public void set(String key,Object value,Long expireSeconds){
        redisTemplate.opsForValue().set(key,value,expireSeconds, TimeUnit.SECONDS);
    }

    /**
     * 写入一个缓存
     * @param key 键
     * @param value 数据
     * @param expireSeconds 过期时长
     * @param timeUnit 时间单位
     */
    public void set(String key,Object value,Long expireSeconds,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,expireSeconds,timeUnit);
    }

    /**
     * 取缓存
     * @param key 键
     * @return 数据
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存自增1
     * @param key 键
     * @return 数据
     */
    public Object increase(String key){
        return redisTemplate.opsForValue().increment(key);
    }
}
