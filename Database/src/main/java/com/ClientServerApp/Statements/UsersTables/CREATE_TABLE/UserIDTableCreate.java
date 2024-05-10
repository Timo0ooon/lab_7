package com.ClientServerApp.Statements.UsersTables.CREATE_TABLE;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserIDTableCreate {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static void createTable(int id) {
        try {

            try (
                    Connection connection = new SQLDatabaseManager().connect();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "CREATE TABLE user_" + id + " (\n" +
                                    "    id SERIAL PRIMARY KEY,\n" +
                                    "    key INT,\n" +
                                    "    name VARCHAR(255),\n" +
                                    "    x_coordinate DOUBLE PRECISION,\n" +
                                    "    y_coordinate BIGINT,\n" +
                                    "    date DATE,\n" +
                                    "    real_hero BOOLEAN,\n" +
                                    "    has_tooth_pick BOOLEAN,\n" +
                                    "    impact_speed INT,\n" +
                                    "    weapon_type VARCHAR(30),\n" +
                                    "    mood VARCHAR(30), \n" +
                                    "    car_name VARCHAR(100) \n" +
                                    ");"
                    )
                    ) {
                preparedStatement.executeUpdate();
            }

        }

        catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
