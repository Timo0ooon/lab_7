package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class AverageOfImpactSpeed implements Command {

    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (collection.isEmpty())
            return new Response(null, "Collection is empty!");

        else if (options != null || objects != null)
            return new Response(null, "This command doesn't accept arguments!");

        // Action
        return new Response(collection.values().stream().map(HumanBeing::getImpactSpeed).reduce(Integer::sum).get() / collection.size(), "Done!");
    }
}
