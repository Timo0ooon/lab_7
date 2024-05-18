package com.ClientServerApp.ClientApplication.LocalManagers;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @param <T> - Class parameterization. Needs for reading Response or AuthorizationResponse.
 */
public class ServerResponseReader<T> {

    /**
     * @param data - Serialized object sent from Server application.
     * @return Response or AuthorizationResponse.
     */
    public T read(byte[] data) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
                ) {
            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
            System.out.println("[Error]: " + e.getMessage());
        }
        return null;
    }
}
