package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.CollectionManager.Other.Checker;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

public class UpdateByID implements Command {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (options == null || objects == null)
            return new Response("Error with arguments!");

        else if (!Arrays.stream(options).allMatch(Checker::isNumber))
            return new Response("Error with arguments! Not all arguments are numbers!");

        else if (!Arrays.stream(options).allMatch(el -> collection.containsKey(Integer.parseInt(el))))
            return new Response("Error with keys! Not all keys are in the collection!");

        else if (!Arrays.stream(objects).allMatch(Objects::nonNull))
            return new Response("Error with objects! not all elements are entered!");

        // Action
        for (int i = 0; i < options.length; i++) {
            int id = collection.get(Integer.parseInt(options[i])).getID();
            collection.put(Integer.parseInt(options[i]), objects[i]);
            collection.get(Integer.parseInt(options[i])).setID(id);

        }

        return new Response("Done!");
    }
}
