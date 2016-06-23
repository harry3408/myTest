package com.harry.annotation.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AnnonationAspect {

    @Pointcut(value="execution(protected com.harry..*.*(..))")
    public void beforePointcut() {
        
    }

    @Before(value="beforePointcut()")
    public void beforeHello() {
        System.out.println("--------------- AnnonationAspect before hello ----------------------");
    }
}
