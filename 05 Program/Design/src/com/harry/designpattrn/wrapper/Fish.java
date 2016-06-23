package com.harry.designpattrn.wrapper;

public class Fish extends Change {

    public Fish(TheGreatestSage sage) {
        super(sage);
    }

    @Override
    public void move() {
        super.move();
        System.out.println("Fish move.");
    }
}
