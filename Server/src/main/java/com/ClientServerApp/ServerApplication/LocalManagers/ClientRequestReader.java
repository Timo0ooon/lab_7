package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.Request.Request;
import com.ClientServerApp.ServerApplication.Server.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


import java.util.concurrent.Callable;


public class ClientRequestReader<T> implements Callable<T> {
    private final byte[] data;

    public ClientRequestReader(byte[] data) {
        this.data = data;
    }

    @Override
    public T call() {
       try (
               ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.data);
               ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
       ) {

           var request = objectInputStream.readObject();
           if (request != null )
               return (T) request;

           return null;
       }

       catch (IOException | ClassNotFoundException e) {
           return null;
       }
    }
}
