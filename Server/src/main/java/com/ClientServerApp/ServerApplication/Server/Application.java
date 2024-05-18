package com.ClientServerApp.ServerApplication.Server;

import com.ClientServerApp.Environment;

import org.apache.log4j.PropertyConfigurator;

import static java.io.File.separator;

public class Application {
    public static void main(String[] args) {
        PropertyConfigurator.configure("Server" + separator + "src"  + separator +  "main"
                + separator + "resources"  + separator + "log4j.properties");
        Server server = new Server(Environment.HOST, Environment.PORT);
        server.run();
    }
}
