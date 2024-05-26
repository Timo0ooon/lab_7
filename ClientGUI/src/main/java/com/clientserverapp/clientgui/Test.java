package com.clientserverapp.clientgui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.File.separator;

public class Test {
    public static void main(String[] args) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Files" + separator + "Help.txt"))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\[[a-zA-Z]+]\\s[a-zA-Z_]+\\s(.+|\\{.+})\\s+-.+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String command = matcher.group().split(" ")[1];

                }
            }
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
