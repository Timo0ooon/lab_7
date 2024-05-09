package com.ClientServerApp.ClientApplication.Client;


import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;
import com.ClientServerApp.ClientApplication.Other.Container;
import com.ClientServerApp.ClientApplication.Other.StringScraper;

import com.ClientServerApp.Environment;

import com.ClientServerApp.Request.Request;

import com.ClientServerApp.Response.Response;


import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.util.Scanner;

public class Client {
    private final int port;
    private final String host;
    private static final Scanner scanner = new Scanner(System.in);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        Request request;
        try (
                SocketChannel socketChannel = SocketChannel.open();
        ) {
            socketChannel.connect(new InetSocketAddress(this.host, this.port));
            while (!Thread.currentThread().isInterrupted()) {
                try {


                    String clientMessage = scanner.nextLine();
                    Container container = StringScraper.create(clientMessage);


                    request = new Request(container.getCommand());
                    request.setOptions(container.getOptions());
                    request.setObjects(container.getObjects());

                    ServerRequestWriter.write(request, socketChannel);

                    ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
                    int bytesRead = socketChannel.read(responseBuffer);
                    byte[] responseBytes = new byte[bytesRead];
                    responseBuffer.flip();
                    responseBuffer.get(responseBytes);

                    Response response = ServerResponseReader.read(responseBytes);
                    System.out.println(response);

                }
                catch (Exception e) {
                    System.out.println(e + "123123131232");
                }
            }
    } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(Environment.HOST, Environment.PORT);
        client.run();
    }
}