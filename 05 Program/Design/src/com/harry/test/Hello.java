package com.harry.test;

public class Hello {

    private static Hello hello = new Hello();

    private Hello() {
    }

    public static Hello getInstance() {
        // if (hello == null) {
        // hello = new Hello();
        // }
        return hello;
    }
}
