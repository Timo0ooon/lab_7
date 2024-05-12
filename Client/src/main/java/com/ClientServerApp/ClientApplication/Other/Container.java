package com.ClientServerApp.ClientApplication.Other;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

/**
 * Class with Getters and Setters. Needed to store commands parameters and objects.
 */
public class Container {
    private String command;
    private String[] options;
    private HumanBeing[] objects;
    public Container(String command, String[] options, HumanBeing[] objects) {
        this.command = command;
        this.options = options;
        this.objects = objects;
    }
    public Container() {}

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public HumanBeing[] getObjects() {
        return objects;
    }

    public void setObjects(HumanBeing[] objects) {
        this.objects = objects;
    }
}
