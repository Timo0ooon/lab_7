package com.ClientServerApp.CommandManager.Commands;

import java.io.*;
import java.util.Map;

import static  java.io.File.separator;

public class Help implements Command {
    @Override
    public void execute() {

        File file = new File("Files" + separator + "Help.txt");


        if (file.isFile()) {

            try (
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ) {
                StringBuilder stringBuilder = new StringBuilder("[Commands]:\n");
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append("*").append("\t").append(line).append("\n");
                }
                System.out.println(stringBuilder);

            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("File not found!");
        }
    }
}
