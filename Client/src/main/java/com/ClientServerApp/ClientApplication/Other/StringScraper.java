package com.ClientServerApp.ClientApplication.Other;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.UserInput.ClientHumanBeing;

import java.util.ArrayList;
import java.util.Arrays;


public class StringScraper {
    /**
     * Method converts the string to Container.
     * @param message - user's string.
     * @return Container.
     */
    public static Container create(String message) {
        String[] options;
        HumanBeing[] objects;
        String command;
        Container container = new Container();

        String[] values = message.trim().toLowerCase().split(" ");
        if (values.length == 0)
            return null;

        command = values[0];
        container.setCommand(command);

        if (values.length == 2) {
            options = values[1].replaceAll("\\{", "").replaceAll("}", "").split(",");
            if (!Arrays.stream(options).allMatch(Checker::checkNumber))
                return null;
            container.setOptions(options);

            if (command.equals("remove_key"))
                return container;

            objects = new HumanBeing[options.length];

            ArrayList<String> commands = new ArrayList<>();

            commands.add("show");
            commands.add("info");
            commands.add("clear");
            commands.add("remove_greater");
            commands.add("remove_lower");
            commands.add("average_of_impact_speed");
            commands.add("max_by_impact_speed");


            if (!commands.contains(command)) {
                for (int i = 0; i < options.length; i++) {
                    objects[i] = (new ClientHumanBeing()).generate();
                }
            }

            container.setObjects(objects);
        }

        return container;
    }

    public static Container create(String message, HumanBeing[] objects) {
        String[] options;
        String command;
        Container container = new Container();

        String[] values = message.trim().toLowerCase().split(" ");
        if (values.length == 0)
            return null;

        command = values[0];
        container.setCommand(command);

        if (values.length == 2) {
            options = values[1].replaceAll("\\{", "").replaceAll("}", "").split(",");
            if (!Arrays.stream(options).allMatch(Checker::checkNumber))
                return null;
            container.setOptions(options);

            if (command.equals("remove_key"))
                return container;

            container.setObjects(objects);
        }

        return container;
    }
}
