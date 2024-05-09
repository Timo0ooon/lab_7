package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.Environment;
import com.ClientServerApp.Request.Request;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try ( SocketChannel socketChannel = SocketChannel.open() )  {
            socketChannel.connect(new InetSocketAddress(Environment.HOST, Environment.PORT));

            while (true) {
                Request request = new Request("show");

                try (
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                ) {
                    objectOutputStream.writeObject(request);

                    byte[] data = byteArrayOutputStream.toByteArray();
                    ByteBuffer buffer = ByteBuffer.wrap(data);

                    scanner.nextLine();
                    socketChannel.write(buffer);


                    ByteBuffer buffer1 = ByteBuffer.allocate(1024);
                    int bytesRead = socketChannel.read(buffer1);

                    if (bytesRead > 0) {
                        buffer1.flip();
                        byte[] data1 = new byte[bytesRead];
                        buffer1.get(data1);

                        try (
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data1);
                                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        ) {
                            System.out.println(objectInputStream.readObject());
                        }
                        catch (ClassNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }

        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
