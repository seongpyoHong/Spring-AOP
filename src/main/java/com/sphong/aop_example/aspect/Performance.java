package com.sphong.aop_example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Performance {
    @Pointcut("execution(* com.sphong.aop_example.service.BoardService.getBoards(..))")
    public void getBoards() {}

    @Pointcut("execution(* com.sphong.aop_example.service.UserService.getUsers(..))")
    public void getUsers() {}

    @Around("getBoards() || getUsers()")
    public Object calculateExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();

            System.out.println("Execution Time : " + (end - start));
        } catch (Throwable throwable) {
            System.out.println("Exception!");
        }
        return result;
    }
}
