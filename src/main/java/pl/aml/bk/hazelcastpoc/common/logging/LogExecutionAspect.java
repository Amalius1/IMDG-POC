package pl.aml.bk.hazelcastpoc.common.logging;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class LogExecutionAspect {

    @SneakyThrows
    @Around("@annotation(LogExecutionTime)")
    public Object logExeutionTime(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Method=\"{}\"|Time=\"{}\"", joinPoint.getSignature().getName(), executionTime);
        }
    }

}
