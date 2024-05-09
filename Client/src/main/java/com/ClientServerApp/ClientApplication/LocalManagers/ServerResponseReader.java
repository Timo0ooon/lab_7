package com.ClientServerApp.ClientApplication.LocalManagers;

import com.ClientServerApp.Response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;

public class ServerResponseReader {

    public static Response read(byte[] data) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ) {
            return (Response) objectInputStream.readObject();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
