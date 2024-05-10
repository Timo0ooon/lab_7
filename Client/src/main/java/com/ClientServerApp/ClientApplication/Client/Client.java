package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;
import com.ClientServerApp.ClientApplication.Other.MD5HashString;
import com.ClientServerApp.CommandManager.CommandManager;
import com.ClientServerApp.Environment;

import com.ClientServerApp.Request.AuthorizationRequest;

import com.ClientServerApp.Response.AuthorizationResponse;
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
    private final CommandManager commandManager = new CommandManager();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        AuthorizationRequest registrationRequest;
        try (
                SocketChannel socketChannel = SocketChannel.open();
        ) {
            socketChannel.connect(new InetSocketAddress(this.host, this.port));

            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Enter your name ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password ");
                String password = MD5HashString.createHashedPassword(scanner.nextLine());

                registrationRequest = new AuthorizationRequest(username, password);
                new ServerRequestWriter().write(registrationRequest, socketChannel);


                ByteBuffer responseBuffer = ByteBuffer.allocate(1024*1024);
                int bytesRead = socketChannel.read(responseBuffer);
                byte[] data = new byte[bytesRead];
                responseBuffer.flip();
                responseBuffer.get(data);
                AuthorizationResponse response = new ServerResponseReader<AuthorizationResponse>().read(data);
                System.out.println(response);
                if (response.isStatus())
                    break;
            }

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String clientMessage = scanner.nextLine();
                    Response response = commandManager.find(clientMessage, socketChannel);
                    if (response != null)
                        System.out.println(response);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(Environment.HOST, Environment.PORT);
        client.run();
    }
}