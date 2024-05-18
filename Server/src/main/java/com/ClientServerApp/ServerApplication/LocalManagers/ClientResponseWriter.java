package com.ClientServerApp.ServerApplication.LocalManagers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientResponseWriter <T> implements Runnable {
    private final T response;
    private final SocketChannel client;
    private final Logger logger = LoggerFactory.getLogger(ClientResponseWriter.class);

    public ClientResponseWriter(T response, SocketChannel client) {
        this.response = response;
        this.client = client;
    }

    @Override
    public void run() {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
                ) {
            objectOutputStream.writeObject(response);

            byte[] data = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(data);

            while (buffer.hasRemaining()) {
                client.write(buffer);
            }

        } catch (IOException e) {
            this.logger.error(e.toString());
        }
    }
}
