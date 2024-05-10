package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;
import com.ClientServerApp.ClientApplication.Other.MD5HashString;
import com.ClientServerApp.CommandManager.CommandManager;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Request.AuthorizationRequest;

import com.ClientServerApp.Response.AuthorizationResponse;
import com.ClientServerApp.Response.Response;


import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.util.Hashtable;
import java.util.Scanner;

public class Client {
    private final int port;
    private final String host;
    private static final Scanner scanner = new Scanner(System.in);
    private final CommandManager commandManager = new CommandManager();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        AuthorizationRequest registrationRequest;
        try (
                SocketChannel socketChannel = SocketChannel.open()
        ) {
            socketChannel.connect(new InetSocketAddress(this.host, this.port));

            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("[Message] Enter your name: ");
                String username = scanner.nextLine().trim();
                System.out.print("[Message] Enter password: ");
                String password = MD5HashString.createHashedPassword(scanner.nextLine());

                registrationRequest = new AuthorizationRequest(username, password);
                new ServerRequestWriter().write(registrationRequest, socketChannel);


                ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
                int bytesRead = socketChannel.read(responseBuffer);
                byte[] data = new byte[bytesRead];
                responseBuffer.flip();
                responseBuffer.get(data);
                AuthorizationResponse response = new ServerResponseReader<AuthorizationResponse>().read(data);
                if (response.isStatus())
                    break;
            }

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.print("[Write message] ");
                    String clientMessage = scanner.nextLine();
                    Response response = commandManager.find(clientMessage, socketChannel);
                    if (response != null) {
                        String message = response.getMessage();
                        Hashtable<Integer, HumanBeing> collection = response.getCollection();
                        Object result = response.getObject();
                        StringBuilder sentence = new StringBuilder();

                        if (message != null)
                            sentence.append("[Message] ").append(message);

                        if (collection != null)
                            sentence.append("[Collection] ").append(collection);

                        if (result != null)
                            sentence.append("[Result] ").append(result);

                        System.out.println(sentence);
                    }
                    else {
                        System.out.println("[Error] response is empty");
                    }
                }
                catch (Exception ignored) {}
            }
        }
        catch (IOException e) {
            System.out.println("[Error] " + e);
        }
    }
}