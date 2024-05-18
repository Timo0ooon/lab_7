package com.ClientServerApp.CollectionManager.Commands;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import com.ClientServerApp.Response.Response;

import java.util.Hashtable;

public interface Command {
    // size of options array equals size of objects array
    Response execute(Hashtable<Integer, HumanBeing> collection, String[] options, HumanBeing[] objects);
}
