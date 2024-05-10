package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;
import com.ClientServerApp.Statements.UsersTables.INSERT.WriteData;

import java.sql.Connection;
import java.util.Hashtable;

public class Save implements Command {
    private String userName;


    public Save() {}
    public Save(String userName) {
        this.userName = userName;
    }
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        WriteData.write(this.userName, collection);
        return new Response("Saved!");

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Save{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
