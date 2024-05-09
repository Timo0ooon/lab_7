package com.ClientServerApp.Statements.UsersTable.CREATE_TABLE;

import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static java.io.File.separator;

public class UsersTableCreate {
    public static boolean createTable(Connection connection) {
        try {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            new String(
                                    FileUtils.readFileToByteArray(
                                            new File("Database" + separator + "src" + separator + "main" + separator + "java" + separator +
                                                    "com" + separator + "ClientServerApp" + separator + "Statements" + separator + "UsersTable" + separator + "CREATE_TABLE" + separator + "CreateUsersTable.sql")
                                    )
                            )
                    )
                    ) {

                return preparedStatement.executeUpdate() == 0;
            }

        }
        catch (Exception e) {
            return false;
        }
    }
}
