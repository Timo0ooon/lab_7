package com.ClientServerApp.ClientApplication.LocalManagers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Class sends Request to Server.
 */
public class ServerRequestWriter {
    /**
     *
     * @param request - Class Request or AuthorizationRequest.
     * @param socketChannel - Client channel.
     * @param <T> - Method parameterization.
     */
    public <T> void write(T request, SocketChannel socketChannel) {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                ) {
            objectOutputStream.writeObject(request);

            byte[] data = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(data);

            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
