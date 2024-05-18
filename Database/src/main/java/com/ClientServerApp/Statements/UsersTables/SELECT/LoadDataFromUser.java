package com.ClientServerApp.Statements.UsersTables.SELECT;

import com.ClientServerApp.Model.Car.Car;
import com.ClientServerApp.Model.Coordinates.Coordinates;
import com.ClientServerApp.Model.Enums.*;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.Hashtable;



public class LoadDataFromUser {
    private static final Logger logger = LoggerFactory.getLogger(LoadDataFromUser.class);
    public static Hashtable<Integer, HumanBeing> load(int userID) {
        try (
                Connection connection = new SQLDatabaseManager().connect()
        ) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM working_users WHERE working_users.user_id = " + userID
            );

            Hashtable<Integer, HumanBeing> collection = new Hashtable<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("key");
                int key = resultSet.getInt("key");
                String name = resultSet.getString("name");
                double xCoordinate = resultSet.getDouble("x_coordinate");
                long yCoordinate = resultSet.getLong("y_coordinate");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                Boolean realHero = resultSet.getBoolean("real_hero");
                Boolean hasToothPick = resultSet.getBoolean("has_tooth_pick");
                int impactSpeed = resultSet.getInt("impact_speed");
                WeaponType weaponType = WeaponType.valueOf(resultSet.getString("weapon_type"));
                Mood mood = Mood.valueOf(resultSet.getString("mood"));
                String carName = resultSet.getString("car_name");

                Coordinates coordinates = new Coordinates();
                coordinates.setX(xCoordinate);
                coordinates.setY(yCoordinate);

                Car car = new Car();
                car.setName(carName);

                HumanBeing humanBeing = new HumanBeing(
                    id, name, coordinates, date, realHero, hasToothPick, impactSpeed, weaponType, mood, car
                );

                collection.put(key, humanBeing);
            }

            return collection;

        } catch (SQLException e) {
            logger.error(e.toString());
        }

        return null;
    }
}
