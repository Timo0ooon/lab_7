package com.ClientServerApp.CommandManager;

import com.ClientServerApp.CommandManager.Commands.Command;

import com.ClientServerApp.CommandManager.Commands.Exit;
import com.ClientServerApp.CommandManager.Commands.Help;
import com.ClientServerApp.CommandManager.Commands.LoadScript;
import com.ClientServerApp.ClientApplication.Other.Container;
import com.ClientServerApp.ClientApplication.Other.StringScraper;
import com.ClientServerApp.ClientApplication.Other.TypesOfCommands;
import com.ClientServerApp.Request.Request;
import com.ClientServerApp.Response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final String fileName;

    public CommandManager() {
        commands.put("help", new Help());
        commands.put("exit", new Exit());
        this.fileName = System.getenv("commands_file");
    }

    public Response find(String userLine, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        userLine = userLine.toLowerCase().trim();
        Container container = StringScraper.create(userLine);

        if (container == null)
            return new Response(null, "Unknown syntax");

        if (TypesOfCommands.commandsOnServer.contains(userLine)) {
            Request request = new Request(container.getCommand(), container.getOptions(), container.getObjects());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            return (Response) objectInputStream.readObject();
        }

        if (TypesOfCommands.hybridCommands.contains(userLine)) {
            ArrayList<Request> requests = LoadScript.execute(System.getenv("commands_file"));
            if (requests == null)
                return new Response(null, "Error!");

            for (Request request: requests) {
                if (TypesOfCommands.commandsOnClient.contains(container.getCommand())) {
                    this.commands.get(container.getCommand()).execute();
                    return new Response(true, "Done!");
                }

                else if (TypesOfCommands.commandsOnServer.contains(container.getCommand())) {
                    objectOutputStream.writeObject(request);
                    objectOutputStream.flush();

                    return (Response) objectInputStream.readObject();
                }

                else
                    return new Response(null, "Unknown command!");
            }
        }
        return new Response(null, "Unknown command!");
    }
}
