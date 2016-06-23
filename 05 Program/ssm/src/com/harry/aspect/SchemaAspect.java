package com.harry.aspect;

import org.aspectj.lang.JoinPoint;

public class SchemaAspect {

    public void beforeHello(JoinPoint jp) {
        System.out.println("--------------- before hello ----------------------");
    }

    public void beforeHello2(JoinPoint jp) {
        System.out.println("--------------- before2 hello ----------------------");
    }

    public void beforeHello(String msg) {
        System.out.println("------------- before hello advice --> " + msg + "-----------------");
    }

    public void beforeHello(String msg, String msg2) {
        System.out.println("------------- before hello advice --> " + msg + "-----------------" + msg2);
    }


    public void before(String msg) {
        System.out.println("--------------- before hello ----------------------");
    }
    
    public void after(JoinPoint jp, String msg) {
        System.out.println("--------------- after hello1 ----------------------");
    }
}
