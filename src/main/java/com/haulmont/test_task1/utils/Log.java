package com.haulmont.test_task1.utils;

public class Log {
    public static void printSQLEx(String source, String member, String message) {
        System.out.println(source + "::" + member + " message: " + message);
    }
}
