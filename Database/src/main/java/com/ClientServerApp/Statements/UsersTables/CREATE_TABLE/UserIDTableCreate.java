package com.ClientServerApp.Statements.UsersTables.CREATE_TABLE;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserIDTableCreate {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static void createTable() {
        try {

            try (
                    Connection connection = new SQLDatabaseManager().connect()
                    ) {

                // Table names: working_users and all_saved_working_users
                PreparedStatement preparedStatement = connection.prepareStatement(
                        """
                                CREATE TABLE working_users (
                                    id INT,
                                    key INT,
                                    name VARCHAR(255),
                                    x_coordinate DOUBLE PRECISION,
                                    y_coordinate BIGINT,
                                    date DATE,
                                    real_hero BOOLEAN,
                                    has_tooth_pick BOOLEAN,
                                    impact_speed INT,
                                    weapon_type VARCHAR(30),
                                    mood VARCHAR(30),
                                    car_name VARCHAR(100),
                                    -- User ID Dependency\s
                                    user_id INT REFERENCES users(id)
                                );
                                """
                );
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(
                        """
                                CREATE TABLE all_saved_working_users (
                                    id INT,
                                    key INT,
                                    name VARCHAR(255),
                                    x_coordinate DOUBLE PRECISION,
                                    y_coordinate BIGINT,
                                    date DATE,
                                    real_hero BOOLEAN,
                                    has_tooth_pick BOOLEAN,
                                    impact_speed INT,
                                    weapon_type VARCHAR(30),
                                    mood VARCHAR(30),
                                    car_name VARCHAR(100),
                                    -- User ID Dependency\s
                                    user_id INT REFERENCES users(id)
                                );
                                """
                );
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }
}