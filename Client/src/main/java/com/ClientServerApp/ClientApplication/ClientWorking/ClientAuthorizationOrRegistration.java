package com.ClientServerApp.ClientApplication.ClientWorking;

import com.ClientServerApp.ClientApplication.LocalManagers.ServerRequestWriter;
import com.ClientServerApp.ClientApplication.LocalManagers.ServerResponseReader;


import com.ClientServerApp.Request.AuthorizationRequest;

import com.ClientServerApp.Response.AuthorizationResponse;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientAuthorizationOrRegistration {
    private SocketChannel channel;

    public ClientAuthorizationOrRegistration() {}

    public ClientAuthorizationOrRegistration(SocketChannel channel) {
        this.channel = channel;
    }

    public boolean register(String username, String password) throws IOException {
        AuthorizationRequest registrationRequest = new AuthorizationRequest(username, password);

        // Sending request to Server
        new ServerRequestWriter().write(registrationRequest, this.channel);

        // Reading from Server
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
        int bytesRead = this.channel.read(responseBuffer);
        byte[] data = new byte[bytesRead];
        responseBuffer.flip();
        responseBuffer.get(data);
        AuthorizationResponse response = new ServerResponseReader<AuthorizationResponse>().read(data);


        return response.isStatus();
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }
}
