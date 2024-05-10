package com.ClientServerApp.Statements.DataAboutUsers.SELECT;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import static java.io.File.separator;

public class Users {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static ArrayList<String[]> get() {
        try (
                Connection connection = new SQLDatabaseManager().connect();
                ) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    new String(
                            FileUtils.readFileToByteArray(
                            new File("Database" + separator + "src" + separator + "main" + separator + "java" + separator +
                                    "com" + separator + "ClientServerApp" + separator + "Statements" + separator + "DataAboutUsers" + separator + "SELECT" + separator + "SelectRequest.sql")
                            )
                    )
            );

            ArrayList<String[]> result = new ArrayList<>();

            while (resultSet.next()) {

                result.add(new String[] {
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("hash_password")
                });
            }

            return result;

        } catch (SQLException | IOException e) {
            logger.error(e.toString());
        }

        return null;
    }
}
