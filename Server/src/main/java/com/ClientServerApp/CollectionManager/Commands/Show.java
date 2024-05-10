package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class Show implements Command{
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        else if (collection.isEmpty())
            return new Response("Collection is empty!");

        // Action
        StringBuilder stringBuilder = new StringBuilder("\n[Collection]:\n");
        for (Integer key: collection.keySet()) {
            stringBuilder.append(key).append(": ").append(collection.get(key)).append("\n");
        }
        return new Response("Done! " + stringBuilder);
    }
}
