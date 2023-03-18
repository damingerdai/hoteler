package org.daming.hoteler.api.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.annontaions.RateLimiter;
import org.daming.hoteler.pojo.enums.LimitType;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.utils.IpUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @author daming
 * @version 2023-03-18 09:54
 **/
@Aspect
@Component
public class RateLimiterAspect {

    private static final HotelerLogger logger = LoggerManager.getApiLogger();

    private RedisTemplate<Object, Object> redisTemplate;

    private RedisScript<Long> limitScript;

    private IErrorService errorService;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (number==null || number.intValue() > count) {
                throw this.errorService.createHotelerRateLimitException();
            }
            logger.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), key);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(IpUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest())).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

    public RateLimiterAspect(RedisTemplate<Object, Object> redisTemplate, RedisScript<Long> limitScript, IErrorService errorService) {
        this.redisTemplate = redisTemplate;
        this.limitScript = limitScript;
        this.errorService = errorService;
    }
}
