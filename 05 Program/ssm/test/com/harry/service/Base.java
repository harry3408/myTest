package com.harry.service;

public abstract class Base {
    public String name = "Base";

    public Base() {
        tellName();
        printName();
    }

    public abstract void tellName();

    public abstract void printName();
}
