package com.harry.designpattrn.wrapper;

public class Test {

    public static void main(String[] args) {
        TheGreatestSage sage = new Monkey();

        TheGreatestSage bird = new Bird(sage);
        TheGreatestSage fish = new Fish(bird);

        fish.move();
    }
}