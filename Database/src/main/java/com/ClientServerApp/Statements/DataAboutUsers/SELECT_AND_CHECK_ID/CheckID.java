package com.ClientServerApp.Statements.DataAboutUsers.SELECT_AND_CHECK_ID;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.DataAboutUsers.SELECT.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckID {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public static Integer check(String userName) {
        try {
            ArrayList<String[]> users = Users.get();
            if (users != null) {
                HashMap<String, Integer> hashMap = new HashMap<>();
                users.forEach(el -> hashMap.put(el[1], Integer.parseInt(el[0])));
                return hashMap.get(userName);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return null;
    }
}
