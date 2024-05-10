package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.CollectionManager.CollectionManager;
import com.ClientServerApp.CollectionManager.Commands.Save;
import com.ClientServerApp.CollectionManager.Other.Status;
import com.ClientServerApp.Request.Request;
import com.ClientServerApp.Response.AuthorizationResponse;
import com.ClientServerApp.Response.Response;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final ByteBuffer buffer;
    private final int bytes;
    private final SocketChannel client;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int TIME_MS = 50;
    private final CollectionManager collectionManager;


    public ClientHandler(ByteBuffer buffer, int bytes, SocketChannel client, CollectionManager collectionManager) {
        this.buffer = buffer;
        this.bytes = bytes;
        this.client = client;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        try {
            if (this.collectionManager.getStatus() == Status.UNAUTHORIZED) {
                RegistrationHandler registrationHandler = new RegistrationHandler(this.collectionManager, this.buffer, this.bytes);
                if (registrationHandler.register()) {
                    this.collectionManager.setStatus(Status.AUTHORIZED);
                    this.executorService.execute(new ClientResponseWriter<>(new AuthorizationResponse(true), client));
                }
                else {
                    this.executorService.execute(new ClientResponseWriter<>(new AuthorizationResponse(false), client));
                }
                this.executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
            }
            else {
                this.buffer.flip();
                byte[] data = new byte[this.bytes];
                this.buffer.get(data);

                Future<Request> future = executorService.submit(new ClientRequestReader<>(data));
                try {
                    executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
                    Request request = future.get();
                    if (request != null) {
                        this.logger.info("Request received. Request: " + request + ". Client: " + this.client.getLocalAddress());
                        Response response = this.collectionManager.getResponse(request);

                        this.executorService.execute(new ClientResponseWriter<>(response, this.client));
                        this.executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
                        this.logger.info("Response sent! Response: " + response + ". Client: " + this.client.getLocalAddress());
                    }

                } catch (Exception e) {
                    this.logger.error(e.toString());
                }
            }

        }
        catch (Exception e) {
            this.logger.error(e.toString());
        }
        finally {
            executorService.shutdown();
        }
    }
}