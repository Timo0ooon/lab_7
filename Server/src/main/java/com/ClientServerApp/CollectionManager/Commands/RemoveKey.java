package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.CollectionManager.Other.Checker;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Arrays;
import java.util.Hashtable;

public class RemoveKey implements Command {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (!Arrays.stream(options).allMatch(Checker::isNumber))
            return new Response("Error with arguments! Not all arguments are numbers!");

        else if (!Arrays.stream(options).allMatch(el -> collection.containsKey(Integer.parseInt(el))))
            return new Response("Error with keys! Not all keys are in the collection!");

        // Action
        for (String option : options) {
            collection.remove(Integer.parseInt(option));
        }

        return new Response("Done!");
    }
}
