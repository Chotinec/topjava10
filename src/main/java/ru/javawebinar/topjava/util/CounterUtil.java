package ru.javawebinar.topjava.util;


public class CounterUtil {

    private static int counter;

    public static int getCounter() {
        return counter++;
    }
}
