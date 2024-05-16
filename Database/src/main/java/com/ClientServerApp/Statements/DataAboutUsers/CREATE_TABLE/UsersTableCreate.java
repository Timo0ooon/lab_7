package com.ClientServerApp.Statements.DataAboutUsers.CREATE_TABLE;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsersTableCreate {
    public static void createTable() {
        try {
            try (
                    Connection connection = new SQLDatabaseManager().connect();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            """
                                    CREATE TABLE IF NOT EXISTS users (
                                        id SERIAL PRIMARY KEY,
                                        username VARCHAR(255),
                                        hash_password VARCHAR(32)
                                    );
                                    """
                    )
                    ) {

                preparedStatement.executeUpdate();
            }

        }
        catch (Exception ignored) {}
    }
}
