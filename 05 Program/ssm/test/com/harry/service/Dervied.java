package com.harry.service;

public class Dervied extends Base {
    public String name = "Dervied";
    public Dervied() {
        tellName();
        printName();
    }

    @Override
    public void tellName() {
        System.out.println("Dervied tell name " + name);
    }

    @Override
    public void printName() {
        System.out.println("Dervied print name " + name);
    }

    public static void main(String[] args) {
        new Dervied();
    }
}
