package com.ClientServerApp.CollectionManager;

import com.ClientServerApp.CollectionManager.Commands.*;
import com.ClientServerApp.CollectionManager.Other.Status;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Request.Request;
import com.ClientServerApp.Response.Response;
import com.ClientServerApp.SQLDatabaseManager.SQLDatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;

public class CollectionManager {

    private final HashMap<String, Command> commands = new HashMap<>();
    private Hashtable<Integer, HumanBeing> collection;
    private Status status;
    private final Save save = new Save();
    private String userName;

    public CollectionManager(Status status) {
        this.status = status;
        this.collection = new Hashtable<>();

        this.commands.put("average_of_impact_speed", new AverageOfImpactSpeed());
        this.commands.put("clear", new Clear());
        this.commands.put("info", new Info());
        this.commands.put("max_by_impact_speed", new MaxByImpactSpeed());
        this.commands.put("remove_greater", new RemoveGreater());
        this.commands.put("remove_lower", new RemoveLower());
        this.commands.put("save", save);
        this.commands.put("show", new Show());
        this.commands.put("insert", new Insert());
        this.commands.put("update_by_id", new UpdateByID());
        this.commands.put("remove_key", new RemoveKey());
    }
    public Response getResponse(Request request) {
        String message = request.getMessage();
        String[] options = request.getOptions();
        HumanBeing[] objects = request.getObjects();
        Response response;

        if (this.commands.containsKey(message)) {
            if (message.equals("save")) {
                this.save.setUserName(this.userName);
            }
            response = this.commands.get(message).execute(this.collection, options, objects);
        }
        else
            response = new Response(null, "Command not found!");


        return response;
    }

    public Status getStatus() { return status;}

    public void setStatus(Status status) {
        this.status = status;
    }

    public Hashtable<Integer, HumanBeing> getCollection() {
        return collection;
    }

    public void setCollection(Hashtable<Integer, HumanBeing> collection) {
        this.collection = collection;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
