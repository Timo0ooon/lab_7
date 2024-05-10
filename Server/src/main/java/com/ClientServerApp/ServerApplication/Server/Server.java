package com.ClientServerApp.ServerApplication.Server;

import com.ClientServerApp.CollectionManager.CollectionManager;
import com.ClientServerApp.ServerApplication.LocalManagers.ClientHandler;
import com.ClientServerApp.CollectionManager.Other.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    private final int port;
    private final String host;
    private static final int TIME_MS = 100;
    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<SocketChannel, CollectionManager> clientHandler = new ConcurrentHashMap<>();
    private int userCount = 0;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try (
                ServerSocketChannel channel = ServerSocketChannel.open();
                Selector selector = Selector.open()
        ) {
            channel.socket().bind(new InetSocketAddress(this.host, this.port));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);

            this.logger.info("Server started!. Address: " + channel.getLocalAddress());

            while (!Thread.currentThread().isInterrupted()) {
                int numReadyChannels = selector.select();
                if (numReadyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();


                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        this.userCount++;

                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();

                        if (client == null) {
                            continue;
                        }
                        this.logger.info("Client-" + this.userCount + " " + "connected! Client: " + client.getLocalAddress());

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);

                        this.clientHandler.put(client, new CollectionManager(Status.UNAUTHORIZED));
                    }

                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
                            int bytes = client.read(buffer);

                            if (bytes == -1) {
                                this.logger.info("Client disconnected! Client: " + client.getLocalAddress());
                                this.clientHandler.remove(client);
                                client.close();
                            }

                            else {
                                Thread thread = new Thread(new ClientHandler(buffer, bytes, client, this.clientHandler.get(client)));
                                thread.setName("Thread-" + client.getLocalAddress());
                                executorService.execute(
                                        thread
                                );

                                executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
                            }
                        }

                        catch (SocketException e) {
                            this.logger.info("Client terminated Connection! Client: " + client.getLocalAddress());
                            this.clientHandler.remove(client);
                            client.close();
                        }

                        catch (Exception e) {
                            this.logger.error(e.toString());
                        }
                    }
                    keyIterator.remove();
                }
            }
        }
        catch (Exception e) {
            this.logger.error(e.toString());
        }
    }
}
