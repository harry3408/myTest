package com.harry.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.harry.annotation.service.impl.HelloServiceImplOne;
import com.harry.annotation.service.impl.UserServices;

public class TestHelloServiceWithAnnotation {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContextWithAnnotation.xml");
        UserServices userServices = ac.getBean("userServices", UserServices.class);
    }
}
