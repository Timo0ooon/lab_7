package com.ClientServerApp.CommandManager.Commands;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import static  java.io.File.separator;

/**
 * Client side command.
 */
public class Help implements Command {

    /**
     * Reads Help.txt file and prints content to the console.
     */
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
