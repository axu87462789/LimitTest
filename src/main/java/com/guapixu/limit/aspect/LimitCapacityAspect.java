package com.guapixu.limit.aspect;

import com.guapixu.limit.annotation.LimitCapacity;
import com.guapixu.limit.constant.ResultCode;
import com.guapixu.limit.pojo.vo.ResultVO;
import com.guapixu.limit.utils.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author lizx
 */
@Aspect
@Component
public class LimitCapacityAspect {
    @Value("${redis.key-prefix}")
    private String redisKeyPrefix;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 连接点
     */
    @Pointcut("@annotation(com.guapixu.limit.annotation.LimitCapacity)")
    public void cut(){

    }

    /**
     * 控制访问流量
     * @param pjp 切入点
     * @return 结果
     */
    @Around("cut()")
    public Object limitCapacity(ProceedingJoinPoint pjp){
        String className = pjp.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        String key = redisKeyPrefix + className + "." + methodName;
        Integer o = (Integer) redisUtils.get(key);
        LimitCapacity capacity = method.getAnnotation(LimitCapacity.class);
        if (o==null){
            // 严谨来讲  还可以做个CAS+原子操作来保证这个操作不会被重复执行以免覆盖一部分的访问量
            redisUtils.set(key,1,capacity.time(),capacity.timeUnit());
        }else {
            if (o>=capacity.capacity()){
                return new ResultVO<>(ResultCode.SYS_BUSY);
            }
            redisUtils.increase(key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return new ResultVO<>(ResultCode.FAIL);
    }
}
