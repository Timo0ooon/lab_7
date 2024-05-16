package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.CollectionManager.CollectionManager;
import com.ClientServerApp.CollectionManager.Commands.Save;
import com.ClientServerApp.CollectionManager.Other.Status;
import com.ClientServerApp.Request.AuthorizationRequest;
import com.ClientServerApp.Request.Request;
import com.ClientServerApp.Response.AuthorizationResponse;
import com.ClientServerApp.Response.Response;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.INSERT.Registration;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT_AND_CHECK_ID.CheckID;
import com.ClientServerApp.Statements.UsersTables.CREATE_TABLE.UserIDTableCreate;
import com.ClientServerApp.Statements.UsersTables.SELECT.LoadDataFromUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Objects;
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
                if (this.register()) {
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
                        int userID = this.collectionManager.getUserID();
                        this.logger.info("Request received. Request: " + request + ". ClientID: " + userID);
                        Response response = this.collectionManager.getResponse(request);

                        this.executorService.execute(new ClientResponseWriter<>(response, this.client));
                        this.executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
                        this.logger.info("Response sent! Response: " + response + ". ClientID: " + userID);
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

    private boolean register() {
        try {
            byte[] data = new byte[this.bytes];
            this.buffer.flip();
            this.buffer.get(data);

            Future<AuthorizationRequest> future = this.executorService.submit(
                    new ClientRequestReader<>(data)
            );

            this.executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
            AuthorizationRequest request = future.get();
            String username = request.name();
            String hashedPassword = request.hashedPassword();

            HashMap<String, String> clients = new HashMap<>();
            Users.get().forEach(el -> clients.put(el[1], el[2]));

            if (!clients.containsKey(username)) {

                this.logger.info(username + " registered!");
                Registration.register(username, hashedPassword);
                Integer userID = CheckID.check(username);

                if (userID != null) {
                    this.collectionManager.setUserID(userID);
                }

                return true;
            }
            else if (clients.get(username).equals(hashedPassword)) {
                this.logger.info(username + " went to profile!");
                Integer userID = CheckID.check(username);

                if (userID != null) {
                    this.collectionManager.setCollection(LoadDataFromUser.load(userID));
                    this.collectionManager.setUserID(userID);
                }
                return true;
            }

        }
        catch (Exception e) {
            this.logger.error(e.toString());
        }
        this.logger.info("Client has not been registered and authorized");
        return false;
    }
}