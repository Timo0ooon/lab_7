package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import com.ClientServerApp.Response.Response;

import com.ClientServerApp.Statements.UsersTables.DELETE.DeleteByID;
import com.ClientServerApp.Statements.UsersTables.INSERT.WriteData;

import java.util.Hashtable;

public class Save implements Command {
    private int userID;

    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        DeleteByID.delete(this.userID);
        WriteData.write(this.userID, collection);
        return new Response("Saved!");

    }

    public void setUserID(int userID) { this.userID = userID; }

    @Override
    public String toString() {
        return "Save{" +
                "userName='" + userID + '\'' +
                '}';
    }
}
