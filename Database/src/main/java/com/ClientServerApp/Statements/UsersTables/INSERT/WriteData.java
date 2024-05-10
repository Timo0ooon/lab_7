package com.ClientServerApp.Statements.UsersTables.INSERT;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Hashtable;

public class WriteData {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static void write(String userName, Hashtable<Integer, HumanBeing> collection) {
        try {
            for (Integer key: collection.keySet()) {
                HumanBeing humanBeing = collection.get(key);
                var name = humanBeing.getName();
                var x_coordinate = humanBeing.getCoordinates().getX();
                var y_coordinate = humanBeing.getCoordinates().getY();
                var date = humanBeing.getCreationDate();
                var real_hero = humanBeing.getRealHero();
                var hasToothPick = humanBeing.getHasToothpick();
                var impactSpeed = humanBeing.getImpactSpeed();
                var weaponType = humanBeing.getWeaponType();
                var mood = humanBeing.getMood();
                var carName = humanBeing.getCar().getName();

                String sqlRequest = "INSERT INTO " + userName + " (key, name, x_coordinate, y_coordinate, date, real_hero, has_tooth_pick, impact_speed, weapon_type, mood, car_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (
                        Connection connection = new SQLDatabaseManager().connect();
                        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)
                        )
                {
                    preparedStatement.setInt(1, key);
                    preparedStatement.setString(2, name);
                    preparedStatement.setDouble(3, x_coordinate);
                    preparedStatement.setDouble(4, y_coordinate);
                    preparedStatement.setDate(5, Date.valueOf(date));
                    preparedStatement.setBoolean(6, real_hero);
                    preparedStatement.setBoolean(7, hasToothPick);
                    preparedStatement.setDouble(8, impactSpeed);
                    preparedStatement.setString(9, String.valueOf(weaponType));
                    preparedStatement.setString(10, String.valueOf(mood));
                    preparedStatement.setString(11, carName);

                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
