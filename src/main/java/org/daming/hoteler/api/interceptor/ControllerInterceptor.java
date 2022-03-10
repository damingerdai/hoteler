package org.daming.hoteler.api.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.logger.LoggerManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * ControllerInterceptor
 *
 * @author gming001
 * @create 2020-12-23 22:57
 **/
@Aspect
@Component
public class ControllerInterceptor {

    private ObjectMapper jsonMapper;

    @Pointcut("execution(* org.daming.hoteler.api.web..*(..)) && (@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void controllerMethodPointcut(){}

    @Around("controllerMethodPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Object[] arguments = pjp.getArgs();
        String param = getMethodArgs(arguments);
        String controllerClass = pjp.getTarget().getClass().getName();
        String controllerMethod = pjp.getSignature().getName();

        String accessToken = request.getHeader("accessToken");
        String requestId = null;
        if (ThreadLocalContextHolder.get() != null) {
            requestId = ThreadLocalContextHolder.get().getRequestId();
        }

        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        // print request param
        LoggerManager.getApiLogger().info("Request Start. requestId: {}, url: {}, uri: {}, request method: {}, controller class: {}, controller method: {}, request params: {}, access token: {}", requestId, url, uri, method, controllerClass, controllerMethod, param, accessToken);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        // print response param
        LoggerManager.getApiLogger().info("Request End. requestId: {}, controller class: {}, controller method: {}, response params: {}", requestId, controllerClass, controllerMethod, jsonMapper.writeValueAsString(result));
        ThreadLocalContextHolder.clean();
        return result;
    }

    private String getMethodArgs(Object[] args) throws JsonProcessingException {
        var sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append("arg[").append(i).append("]: ");
            try {
                if (args[i] instanceof MultipartFile multipartFile) {
                    sb.append(jsonMapper.writeValueAsString(multipartFile.getOriginalFilename()));
                } else {
                    sb.append(jsonMapper.writeValueAsString(args[i])).append(",");
                }
            } catch (JsonProcessingException ex) {
                LoggerManager.getApiLogger().warn("getMethodArgs[{}] json error: {}", i, ex.getMessage());
            }
            sb.append(",");
        }
        if (args.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public ControllerInterceptor(ObjectMapper jsonMapper) {
        super();
        this.jsonMapper = jsonMapper;
    }
}
