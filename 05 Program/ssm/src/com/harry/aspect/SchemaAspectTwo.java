package com.harry.aspect;

import org.aspectj.lang.JoinPoint;

public class SchemaAspectTwo {

    public void before(String msg) {
        System.out.println("--------------- before hello two ----------------------");
    }

    public void after(JoinPoint jp, String msg) {
        System.out.println("--------------- after hello1 two ----------------------");
    }
}
