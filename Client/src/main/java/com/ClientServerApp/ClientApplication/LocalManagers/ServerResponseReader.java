package com.ClientServerApp.ClientApplication.LocalManagers;

import com.ClientServerApp.Response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;

/**
 * @param <T> - Class parameterization.
 */
public class ServerResponseReader<T> {

    /**
     * @param data - Serialized object sent from Server application.
     * @return Response or AuthorizationResponse.
     */
    public T read(byte[] data) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ) {
            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
