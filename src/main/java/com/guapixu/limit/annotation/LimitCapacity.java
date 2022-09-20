package com.guapixu.limit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author lizx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LimitCapacity {
    /**
     * @return 单位时长
     */
    long time();

    /**
     * @return 时间单位
     */
    TimeUnit timeUnit();

    /**
     * @return 单位时长内容量
     */
    int capacity();
}
