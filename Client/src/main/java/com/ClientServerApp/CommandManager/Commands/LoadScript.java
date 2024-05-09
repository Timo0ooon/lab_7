package com.ClientServerApp.CommandManager.Commands;

import com.ClientServerApp.ClientApplication.Other.Container;
import com.ClientServerApp.ClientApplication.Other.StringScraper;
import com.ClientServerApp.Request.Request;

import java.io.*;
import java.util.ArrayList;

import static java.io.File.separator;

public class LoadScript {
    public static ArrayList<Request> execute(String fileName) throws ClassNotFoundException {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Files" + separator + "Commands" + separator + fileName)));
                ) {
            String stringLine;
            ArrayList<Request> requests = new ArrayList<>();
            while ((stringLine = bufferedReader.readLine()) != null) {
                Container container = StringScraper.create(stringLine);
                requests.add(new Request(container.getCommand(), container.getOptions(), container.getObjects()));
            }
            return requests;
        }

        catch (IOException e) {
            return null;
        }
    }
}
