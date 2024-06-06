package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.CollectionManager.Other.Checker;
import com.ClientServerApp.CollectionManager.Other.IDGetter;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import com.ClientServerApp.Response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

public class Insert implements Command {
    private int userID;
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (options == null || objects == null)
            return new Response("Error with arguments!");

        else if (!Arrays.stream(options).allMatch(Checker::isNumber))
            return new Response("Error with arguments! Not all arguments are numbers!");

        else if (!Arrays.stream(objects).allMatch(Objects::nonNull))
            return new Response("Error with objects! not all elements are entered!");

        // Action
        ArrayList<Integer> maxValues = new ArrayList<>();
        maxValues.add(IDGetter.get(this.userID));
        maxValues.add(collection.values().stream().map(HumanBeing::getID).max(Integer::compareTo).stream().toList().get(0));

        Integer firstID = maxValues.stream().max(Integer::compareTo).stream().toList().get(0);
        for (int i = 0; i < options.length; i++) {
            firstID++;
            objects[i].setID(firstID);
            collection.put(Integer.parseInt(options[i]), objects[i]);
        }

        return new Response("Done!");
    }

    public void setUserID(int userID) { this.userID = userID; }
}
