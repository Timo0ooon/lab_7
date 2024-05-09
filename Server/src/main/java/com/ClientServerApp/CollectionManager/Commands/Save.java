package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public class Save implements Command{
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
         if (options != null || objects != null)
            return new Response("This command doesn't accept arguments!");

        // Action
        return new Response("Saved!");
    }
}
