package com.example.testtasknews.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();

        log.info("REQUEST: {} with args: {}", method, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.info("RESPONSE: {} returned: {}", method, result instanceof ResponseEntity ? ((ResponseEntity<?>) result).getBody() : result);
        return result;
    }
}