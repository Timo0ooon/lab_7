package com.ClientServerApp.ClientApplication.Other;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.UserInput.ClientHumanBeing;

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

            for (int i = 0; i < options.length; i++) {
                objects[i] = (new ClientHumanBeing()).generate();
            }

            container.setObjects(objects);
        }

        return container;
    }
}
