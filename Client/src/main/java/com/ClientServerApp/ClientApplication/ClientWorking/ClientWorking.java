package com.ClientServerApp.ClientApplication.ClientWorking;

import com.ClientServerApp.CommandManager.CommandManager;
import com.ClientServerApp.Response.Response;

import java.nio.channels.SocketChannel;

public class ClientWorking {
    private SocketChannel channel;
    private final CommandManager commandManager = new CommandManager();

    public ClientWorking() {}

    public ClientWorking(SocketChannel channel) {
        this.channel = channel;
    }
    public Response work(String clientMessage) {
        return commandManager.find(clientMessage, this.channel);
    }

    public SocketChannel getChannel() {return channel;}

    public void setChannel(SocketChannel channel) {this.channel = channel;}
}
