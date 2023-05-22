package com.fairing.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by flanker on 2022/05/04.
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    定义切入点表达式方法，要对所有控制器方法进行增强
    @Pointcut("execution(* com.fairing.web.*.*(..))")
    public void log() {}

//    前置通知方法
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        获取request对象
        HttpServletRequest request = attributes.getRequest();
//        从请求对象和joinPoint对象获取各项参数
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
//        new一个RequestLog内部类对象用来存放请求的url，IP地址，调用的方法和请求参数
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
//        将该对象传入日志文件
        logger.info("Request : {}", requestLog);
    }

//    最终通知方法，暂不使用
    @After("log()")
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }

//    后置通知方法
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
//        控制器方法执行结果传入日志文件
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
