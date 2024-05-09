package com.ClientServerApp.ClientApplication.Other;

public class Checker {
    public static boolean checkNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
