package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class Clear implements Command{

    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (collection.isEmpty())
            return new Response("Collection is empty!");

        else if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        // Action
        collection.clear();
        return new Response("Collection cleared!");
    }
}
