package com.developer.abdulah.yemekhane.yemekhane;


public class Meal {
    public final String name;
    public final int calory;

    public Meal(String name, int calory) {
        this.name = name;
        this.calory = calory;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, calory);
    }
}