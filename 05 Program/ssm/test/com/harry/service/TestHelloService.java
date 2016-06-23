package com.harry.service;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.object.SqlQuery;

public class TestHelloService {

    public static String property = "Hello2";
    private IHelloService helloService;
    private String message;

    public IHelloService getHelloService() {
        return helloService;
    }

    public void setHelloService(IHelloService helloService) {
        this.helloService = helloService;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        // IHelloService hello = ac.getBean("helloService", IHelloService.class);
        UserService userService = ac.getBean("userService", UserService.class);
        // userService.getHelloService().sayHello();
        userService.getHelloService().sayHello();
        System.out.println(userService.getMessage());
    }
}