package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class RemoveGreater implements Command{
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (collection.isEmpty())
            return new Response("Collection is empty!");

        else if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        // Action
        Integer keyGreaterHumanBeing = collection.keySet().stream().max((key, key1) -> {
            if (collection.get(key).getID().equals(collection.get(key1).getID()))
                return 0;
            else if (collection.get(key).getID() < collection.get(key1).getID())
                return -1;
            return 1;
        }).get();

        collection.remove(keyGreaterHumanBeing);
        return new Response("Done!");
    }
}
