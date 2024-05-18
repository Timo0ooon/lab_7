package com.ClientServerApp.Statements.UsersTables.DELETE;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteByID {
    private static final Logger logger = LoggerFactory.getLogger(DeleteByID.class);
    public static void delete(int userID) {
        try (
                Connection connection = new SQLDatabaseManager().connect()
                ) {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "DELETE FROM working_users WHERE working_users.user_id = " + userID
                    )
                    ) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }
}
