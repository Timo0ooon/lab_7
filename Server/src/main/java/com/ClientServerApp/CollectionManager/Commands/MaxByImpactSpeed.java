package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class MaxByImpactSpeed implements Command{
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (collection.isEmpty())
            return new Response("Collection is empty!");

        else if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        // Action
        HumanBeing humanBeing = collection.values().stream().max((ob, ob1) -> {
            if (ob.getImpactSpeed().equals(ob1.getImpactSpeed()))
                return 0;
            else if (ob.getImpactSpeed() < ob1.getImpactSpeed())
                return -1;
            return 1;
        }).get();

        return new Response("Done! Human: " + humanBeing);
    }
}
