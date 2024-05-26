package com.ClientServerApp.ClientApplication.ClientWorking;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ServerConnection {
    public static SocketChannel connect(String host, int port) {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(host, port));
            return channel;
        }

        catch (IOException e) {
            return null;
        }
    }
}
