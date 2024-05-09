package com.ClientServerApp.CollectionManager.Other;

public class Checker {
    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
