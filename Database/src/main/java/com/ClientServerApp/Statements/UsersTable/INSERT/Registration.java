package com.ClientServerApp.Statements.UsersTable.INSERT;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import org.apache.commons.io.FileUtils;

import java.io.File;

import java.sql.*;

import static java.io.File.separator;

public class Registration {
    public static boolean register(Connection connection, String username, String hashedPassword) {
        try {

            try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        new String(
                                FileUtils.readFileToByteArray(
                                        new File("Database" + separator + "src" + separator + "main" + separator + "java" + separator +
                                                "com" + separator + "ClientServerApp" + separator + "Statements" + separator + "UsersTable" + separator + "INSERT" + separator + "Register.sql")
                                )
                        )
                )
            ) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                return preparedStatement.executeUpdate() == 1;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
