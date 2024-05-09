package com.ClientServerApp.ClientApplication.LocalManagers;

import com.ClientServerApp.Request.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerRequestWriter {
    public static void write(Request request, SocketChannel socketChannel) {
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
