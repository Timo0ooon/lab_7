package com.ClientServerApp.ClientApplication.Other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashString {
    /**
     * The method hashes the password using the algorithm MD5.
     * @param userInput - client password.
     */
    public static String createHashedPassword(String userInput) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] bytes = messageDigest.digest(userInput.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();

        }
        catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return null;
    }
}
