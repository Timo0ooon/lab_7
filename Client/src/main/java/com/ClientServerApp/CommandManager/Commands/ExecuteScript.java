package com.ClientServerApp.CommandManager.Commands;


import com.ClientServerApp.CommandManager.CommandManager;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.nio.channels.SocketChannel;
import java.util.Hashtable;


import static java.io.File.separator;

/**
 * Client side command.
 */
public class ExecuteScript {
    /**
     * Reads commands from a file and sends a request to the server or processes part of the commands on the client side.
     * @param channel - current connection channel between client and server.
     * @return - response from server.
     */
    public static Response execute(SocketChannel channel) {
        File file = new File("Files" + separator + "Commands" + separator + "Commands.txt");
        CommandManager commandManager = new CommandManager();
        if (file.isFile()) {
            try (
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim().toLowerCase();
                    if (!line.equals("execute_script")) {
                        Response response = commandManager.find(line, channel);
                        if (response != null) {
                            String message = response.getMessage();
                            Hashtable<Integer, HumanBeing> collection = response.getCollection();
                            Object result = response.getObject();
                            StringBuilder stringBuilder = new StringBuilder();
                            if (message != null)
                                stringBuilder.append("[Message] ").append(message).append("\n");
                            if (collection != null)
                                stringBuilder.append("[Result] ").append(message).append("\n");
                            if (result != null)
                                stringBuilder.append("[Result] ").append(result).append("\n");
                            System.out.println(stringBuilder);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage());
            }
        }
        else System.out.println("Create directory Files, Commands and file Commands.txt");

        return new Response("Done!");
    }
}
