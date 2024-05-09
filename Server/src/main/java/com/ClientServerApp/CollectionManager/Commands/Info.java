package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;

import java.util.HashMap;
import java.util.Hashtable;
import java.time.LocalDate;

public class Info implements Command{
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects) {
        // Checking
        if (collection.isEmpty())
            return new Response(null, "Collection is empty!");

        else if (options != null || objects != null)
            return new Response(null, "This command doesn't accept arguments!");

        // Action
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Size of collection: ").append(collection.size());

        LocalDate dateOfCreation = collection.values().stream().map(HumanBeing::getCreationDate).min((date, date1) -> {
            if (date.equals(date1))
                return 0;
            else if (date.isBefore(date1))
                return -1;
            return 1;
        }).get();

        stringBuilder.append("Date of creation").append(dateOfCreation);

        return new Response(String.valueOf(stringBuilder));
    }
}
