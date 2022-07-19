package com.jiang.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;

/*
  拦截请求过滤器 打印请求和响应信息
 */
@Aspect
@Controller
public class WebLogAspect {
    private final Logger log  = LoggerFactory.getLogger(WebLogAspect.class);
    @Pointcut("execution(public * com.jiang.mall.controller.*.*(..))")
    public  void webLog(){

    }
    @Before("webLog()")
    public  void  doBefore(JoinPoint joinpoint){
        // 收到请求，记录请求内容
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
        log.info("URL:" +request.getRequestURL().toString());
        log.info("HTTP_MOEDTH:"+request.getMethod());
        log.info("Ip:"+request.getRemoteAddr());
        log.info("CLASS_METHOD:"+joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
        log.info("ARGS:"+ Arrays.toString(joinpoint.getArgs()));
    }
    @AfterReturning(returning = "res",pointcut = "webLog()")
    public  void  doAfterReturning(Object res) throws JsonProcessingException {
        log.info("RESPONSE:"+ new ObjectMapper().writeValueAsString(res));

    }
}
