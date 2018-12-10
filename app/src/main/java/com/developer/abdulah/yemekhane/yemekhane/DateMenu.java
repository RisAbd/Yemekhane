package com.developer.abdulah.yemekhane.yemekhane;

import java.util.Arrays;

public class DateMenu {
    public final String date;
    public final Meal meals[];

    public DateMenu(String date, Meal meals[]) {
        this.date = date;
        this.meals = meals;
    }

    public int totalCalory() {
        int t = 0;
        for (Meal m: meals) {
            t += m.calory;
        }
        return t;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", date, Arrays.toString(meals));
    }
}