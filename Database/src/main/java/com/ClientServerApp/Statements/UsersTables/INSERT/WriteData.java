package com.ClientServerApp.Statements.UsersTables.INSERT;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.Hashtable;

public class WriteData {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    private static final ArrayList<String> tables = new ArrayList<>();
    static  {
        tables.add("working_users");
        tables.add("all_saved_working_users");
    }
    public static void write(int userID, Hashtable<Integer, HumanBeing> collection) {
        try {
            for (Integer key: collection.keySet()) {
                HumanBeing humanBeing = collection.get(key);
                var id = humanBeing.getID();
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

                for (String table: tables) {

                        try (
                                Connection connection = new SQLDatabaseManager().connect();
                                PreparedStatement preparedStatement = connection.prepareStatement(
                                        "INSERT INTO "  + table + " (id, key, name, x_coordinate, y_coordinate, date, real_hero, has_tooth_pick, impact_speed, weapon_type, mood, car_name, user_id)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ")
                        )
                            {
                                preparedStatement.setInt(1, id);
                                preparedStatement.setInt(2, key);
                                preparedStatement.setString(3, name);
                                preparedStatement.setDouble(4, x_coordinate);
                                preparedStatement.setDouble(5, y_coordinate);
                                preparedStatement.setDate(6, Date.valueOf(date));
                                preparedStatement.setBoolean(7, real_hero);
                                preparedStatement.setBoolean(8, hasToothPick);
                                preparedStatement.setDouble(9, impactSpeed);
                                preparedStatement.setString(10, String.valueOf(weaponType));
                                preparedStatement.setString(11, String.valueOf(mood));
                                preparedStatement.setString(12, carName);
                                preparedStatement.setInt(13, userID);

                                preparedStatement.executeUpdate();
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
