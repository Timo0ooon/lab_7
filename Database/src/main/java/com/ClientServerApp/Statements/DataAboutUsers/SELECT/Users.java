package com.ClientServerApp.Statements.DataAboutUsers.SELECT;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.ArrayList;


public class Users {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static ArrayList<String[]> get() {
        ArrayList<String[]> result = new ArrayList<>();

        try (
                Connection connection = new SQLDatabaseManager().connect()
                ) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                result.add(new String[] {
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("hash_password")
                });
            }
            return result;

        }

        catch (SQLException e) {
            logger.error(e.toString());
        }

        return result;
    }
}
