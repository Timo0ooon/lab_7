package com.ClientServerApp.ClientApplication.Other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static java.io.File.separator;

/**
 * Initializing commands with Help.txt
 */
public class TypesOfCommands {
    public static final ArrayList<String> commandsOnServer = new ArrayList<>();
    public static final ArrayList<String> commandsOnClient = new ArrayList<>();
    public static final ArrayList<String> hybridCommands = new ArrayList<>();
    static {
        String separator = File.separator;
        try (
                FileReader fileReader = new FileReader("Files" + separator + "Help.txt");

                BufferedReader bufferedReader = new BufferedReader(fileReader);

        ) {
            String commandHelpLine;
            while ((commandHelpLine = bufferedReader.readLine()) != null) {
                String[] values = commandHelpLine.split(" ");
                if (values.length > 3) {
                    switch (values[0]) {
                        case "[Server]" -> commandsOnServer.add(values[1]);
                        case "[Client]" -> commandsOnClient.add(values[1]);
                        case "[Hybrid]" -> hybridCommands.add(values[1]);
                    }

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
