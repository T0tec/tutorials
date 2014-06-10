package org.t0tec.tutorials.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
 
    @Pointcut("execution(* org.t0tec.tutorials.HelloWorld.sayHello(..))")
    public void logging() {}
        
    @Around("logging()")
    public Object logging(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("Before " + thisJoinPoint.getSignature());
        Object ret = thisJoinPoint.proceed();
        System.out.println("After " + thisJoinPoint.getSignature());
 
        return ret;
    }
}
