package com.ClientServerApp.CommandManager;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;

import com.ClientServerApp.CommandManager.Commands.Command;
import com.ClientServerApp.CommandManager.Commands.ExecuteScript;
import com.ClientServerApp.CommandManager.Commands.Exit;
import com.ClientServerApp.CommandManager.Commands.Help;

import com.ClientServerApp.ClientApplication.Other.Container;
import com.ClientServerApp.ClientApplication.Other.StringScraper;
import com.ClientServerApp.ClientApplication.Other.TypesOfCommands;

import com.ClientServerApp.Request.Request;

import com.ClientServerApp.Response.Response;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.util.HashMap;


/**
 * Command handler from the client side of the application.
 */
public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put("help", new Help());
        commands.put("exit", new Exit());
    }

    /**
     * The handler reads the command, verifies it, sends it to the server if necessary, and returns the result of the command.
     * @param userLine - user command with arguments.
     * @param channel - connection channel between client and server.
     * @return - Response, result command working.
     */
    public Response find(String userLine, SocketChannel channel) {
        userLine = userLine.toLowerCase().trim();
        Container container = StringScraper.create(userLine);

        if (container == null)
            return new Response(null, "Unknown syntax");

        if (TypesOfCommands.commandsOnServer.contains(container.getCommand())) {
            Request request = new Request(container.getCommand(), container.getOptions(), container.getObjects());
            new ServerRequestWriter().write(request, channel);
            try {
                ByteBuffer responseBuffer = ByteBuffer.allocate(1024 * 1024);
                int bytesRead = channel.read(responseBuffer);
                byte[] responseBytes = new byte[bytesRead];
                responseBuffer.flip();
                responseBuffer.get(responseBytes);

                return new ServerResponseReader<Response>().read(responseBytes);
            }
            catch (Exception ignored) {}

            return new Response(null, "Error reading command");
        }

        else if (TypesOfCommands.commandsOnClient.contains(container.getCommand())) {
            this.commands.get(userLine).execute();
            return null;
        }


        else if (TypesOfCommands.hybridCommands.contains(container.getCommand())) {
            return ExecuteScript.execute(channel);
        }

        return new Response(null, "Unknown command!");
    }
}
