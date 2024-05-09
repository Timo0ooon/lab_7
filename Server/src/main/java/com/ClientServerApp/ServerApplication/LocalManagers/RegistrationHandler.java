package com.ClientServerApp.ServerApplication.LocalManagers;

import com.ClientServerApp.Request.RegistrationRequest;
import com.ClientServerApp.Statements.UsersTable.INSERT.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RegistrationHandler {
    private static final int TIME_MS = 100;
    private final ByteBuffer buffer;
    private final int bytes;
    private final Connection connection;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Logger logger = LoggerFactory.getLogger(RegistrationHandler.class);
    public RegistrationHandler(Connection connection, ByteBuffer buffer, int bytes) {
        this.connection = connection;
        this.buffer = buffer;
        this.bytes = bytes;
    }

    public void register() {
        try {
            byte[] data = new byte[this.bytes];
            this.buffer.flip();
            this.buffer.get(data);

            Future<RegistrationRequest> future = this.executorService.submit(
                    new ClientRequestReader<>(data)
            );

            this.executorService.awaitTermination(TIME_MS, TimeUnit.MILLISECONDS);
            RegistrationRequest request = future.get();
            String username = request.name();
            String hashedPassword = request.hashedPassword();

            // нужно сделать запрос к базе и убедиться что тип не зарегестрирован

            boolean result = Registration.register(this.connection, username, hashedPassword);

            if (result)
                this.logger.info("Client registered!");
            else this.logger.info("There was an error with registration");

        } catch (Exception e) {
            this.logger.error(e.toString());
        }
    }
}
