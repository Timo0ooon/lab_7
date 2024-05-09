package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.Request.Request;
import com.ClientServerApp.ServerApplication.Server.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


import java.util.concurrent.Callable;


public class ClientRequestReader implements Callable<Request> {
    private final byte[] data;

    public ClientRequestReader(byte[] data) {
        this.data = data;
    }

    @Override
    public Request call() {
       try (
               ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.data);
               ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
       ) {
           return (Request) objectInputStream.readObject();
       }

       catch (IOException | ClassNotFoundException e) {
           return null;
       }
    }
}
