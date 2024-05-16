package com.ClientServerApp.Statements.DataAboutUsers.INSERT;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import java.sql.*;

public class Registration {
    public static void register(String username, String hashedPassword) {
        try {

            try (
                    Connection connection = new SQLDatabaseManager().connect();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO users (username, hash_password) VALUES (?, ?);"
                    )
            ) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                preparedStatement.executeUpdate();
            }

        } catch (Exception ignored) {}
    }
}
