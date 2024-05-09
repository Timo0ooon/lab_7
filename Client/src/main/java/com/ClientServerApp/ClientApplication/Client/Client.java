package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.Other.MD5HashString;
import com.ClientServerApp.CommandManager.CommandManager;
import com.ClientServerApp.Environment;

import com.ClientServerApp.Request.RegistrationRequest;
import com.ClientServerApp.Request.Request;

import com.ClientServerApp.Response.Response;
import org.w3c.dom.ls.LSOutput;


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
        Request request;
        RegistrationRequest registrationRequest;
        try (
                SocketChannel socketChannel = SocketChannel.open();
        ) {
            socketChannel.connect(new InetSocketAddress(this.host, this.port));

            System.out.print("Enter your name ");
            String username = scanner.nextLine();
            System.out.print("Enter password ");
            String password = MD5HashString.createHashedPassword(scanner.nextLine());

            registrationRequest = new RegistrationRequest(username, password);
            new ServerRequestWriter().write(registrationRequest, socketChannel);



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
    } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(Environment.HOST, Environment.PORT);
        client.run();
    }
}