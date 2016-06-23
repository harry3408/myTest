package com.harry.annotation.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContextWithAnnotation.xml");
        UserServices userServices = ac.getBean("userServices", UserServices.class);
        userServices.getHelloService().sayHello("", "", "");
    }
}
