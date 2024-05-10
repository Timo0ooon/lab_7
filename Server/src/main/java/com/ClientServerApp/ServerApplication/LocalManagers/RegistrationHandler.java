package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.CollectionManager.CollectionManager;
import com.ClientServerApp.CollectionManager.Commands.Save;
import com.ClientServerApp.Request.AuthorizationRequest;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.INSERT.Registration;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;

import com.ClientServerApp.Statements.DataAboutUsers.SELECT_AND_CHECK_ID.CheckID;
import com.ClientServerApp.Statements.UsersTables.CREATE_TABLE.UserIDTableCreate;
import com.ClientServerApp.Statements.UsersTables.SELECT.LoadDataFromUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RegistrationHandler {
    private static final int TIME_MS = 100;
    private final ByteBuffer buffer;
    private final int bytes;
    private final Connection connection;
    private final CollectionManager collectionManager;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Logger logger = LoggerFactory.getLogger(RegistrationHandler.class);
    public RegistrationHandler(Connection connection, CollectionManager collectionManager, ByteBuffer buffer, int bytes) {
        this.connection = connection;
        this.collectionManager = collectionManager;
        this.buffer = buffer;
        this.bytes = bytes;
    }

    public boolean register() {
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
            Objects.requireNonNull(Users.get()).forEach(el -> { clients.put(el[1], el[2]); });

            if (!clients.containsKey(username)) {

                this.logger.info(username + " registered!");
                Registration.register(username, hashedPassword);
                Integer userID = CheckID.check(username);

                if (userID != null) {
                    UserIDTableCreate.createTable(userID);
                    this.collectionManager.setUserName("user_" + userID);
                }

                return true;
            }
            else if (clients.get(username).equals(hashedPassword)) {
                this.logger.info(username + " went to profile!");
                Integer userID = CheckID.check(username);

                if (userID != null) {
                    this.collectionManager.setCollection(LoadDataFromUser.load("user_" + userID));
                    this.collectionManager.setUserName("user_" + userID);
                }
                return true;
            }

        } catch (Exception e) {
            this.logger.error(e.toString());
        }
        this.logger.info("Client has not been registered and authorized");
        return false;
    }
}
