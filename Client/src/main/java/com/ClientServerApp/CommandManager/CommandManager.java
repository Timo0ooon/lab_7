package com.ClientServerApp.CommandManager;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;
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

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.util.ArrayList;
import java.util.HashMap;


public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put("help", new Help());
        commands.put("exit", new Exit());
    }

    public Response find(String userLine, SocketChannel channel) throws IOException, ClassNotFoundException {
        userLine = userLine.toLowerCase().trim();
        Container container = StringScraper.create(userLine);

        if (container == null)
            return new Response(null, "Unknown syntax");

        if (TypesOfCommands.commandsOnServer.contains(container.getCommand())) {
            Request request = new Request(container.getCommand(), container.getOptions(), container.getObjects());
            new ServerRequestWriter().write(request, channel);

            ByteBuffer responseBuffer = ByteBuffer.allocate(1024*1024);
            int bytesRead = channel.read(responseBuffer);
            byte[] responseBytes = new byte[bytesRead];
            responseBuffer.flip();
            responseBuffer.get(responseBytes);

            return ServerResponseReader.read(responseBytes);
        }

        else if (TypesOfCommands.hybridCommands.contains(container.getCommand())) {
            ArrayList<Request> requests = LoadScript.execute(System.getenv("commands_file"));
            if (requests == null)
                return new Response(null, "Error!");

            for (Request request: requests) {
                if (TypesOfCommands.commandsOnClient.contains(container.getCommand())) {
                    this.commands.get(container.getCommand()).execute();
                    return new Response(true, "Done!");
                }

                else if (TypesOfCommands.commandsOnServer.contains(container.getCommand())) {
                    new ServerRequestWriter().write(request, channel);

                    ByteBuffer responseBuffer = ByteBuffer.allocate(1024*1024);
                    int bytesRead = channel.read(responseBuffer);
                    byte[] responseBytes = new byte[bytesRead];
                    responseBuffer.flip();
                    responseBuffer.get(responseBytes);

                    return ServerResponseReader.read(responseBytes);

                }

                else
                    return new Response(null, "Unknown command!");
            }
        }

        else if (TypesOfCommands.commandsOnClient.contains(container.getCommand())) {
            this.commands.get(userLine).execute();
            return null;
        }
        return new Response(null, "Unknown command!");
    }
}
