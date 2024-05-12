package com.ClientServerApp.ClientApplication.Other;

public class Checker {
    /**
     * Method checks line to number.
     * @param value - String line.
     * @return boolean type.
     */
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
