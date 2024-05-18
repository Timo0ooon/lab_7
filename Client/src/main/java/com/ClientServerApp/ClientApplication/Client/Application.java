package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.Environment;

import java.io.Console;

/**
 * Class Application starts Client application to work with Server.
 */
public class Application {
    /**
     * @param args no parameters need to be inserted.
     * if parameters are inserted they will be ignored.
     */
    public static void main(String[] args) {
        Client client = new Client(Environment.HOST, Environment.PORT);
        client.run();
    }
}
