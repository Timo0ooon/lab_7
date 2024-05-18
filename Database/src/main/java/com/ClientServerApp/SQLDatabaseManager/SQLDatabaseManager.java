package com.ClientServerApp.SQLDatabaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SQLDatabaseManager {
    private final Logger logger = LoggerFactory.getLogger(SQLDatabaseManager.class);

    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Users", new String(Config.USER), new String(Config.PASSWORD)
            );
        }

        catch (SQLException | ClassNotFoundException e) {
            this.logger.error(e.toString());
        }
        return null;
    }
}
