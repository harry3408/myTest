package com.harry.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component("annonationAspect")
public class AnnonationAspect {

    @Pointcut(value = "execution(* com.harry..*.*Hello(..))")
    public void beforePointcut() {

    }

    @Before(value = "beforePointcut()")
    public void beforeHello() {
        System.out.println("--------------- AnnonationAspect before hello ----------------------");
    }

    @Before(value = "beforePointcut()")
    public void beforeHelloT() {
        System.out.println("--------------- AnnonationAspect before hello Two----------------------");
    }

    @Before(value = "args(msg)", argNames = "msg")
    public void beforezz(JoinPoint jp, String msg) {
        System.out.println("===msg:" + msg);
    }

    @Before(value = "args(msg, msgtwo)", argNames = "msg, msgtwo")
    public void before1(JoinPoint jp, String msg, String msgtwo) {
        System.out.println("===msg2:" + msg);
    }
}
