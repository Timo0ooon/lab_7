package com.ClientServerApp.ClientApplication.Client;

import com.ClientServerApp.Environment;

public class Application {
    public static void main(String[] args) {
        Client client = new Client(Environment.HOST, Environment.PORT);
        client.run();
    }
}
