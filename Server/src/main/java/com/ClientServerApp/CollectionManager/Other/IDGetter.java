package com.ClientServerApp.CollectionManager.Other;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Statements.UsersTables.SELECT.LoadDataFromUser;

import java.util.List;
import java.util.Optional;

public class IDGetter {
    public static int get(int userID) {
        var collection = LoadDataFromUser.load(userID);
        if (collection != null) {
            List<Integer> identifiers = collection.values().stream().map(HumanBeing::getID).sorted().toList();
            Optional<Integer> value = identifiers.stream().max(Integer::compareTo);
            if (value.isPresent())
                return value.get();
        }
        return 1;
    }
}
