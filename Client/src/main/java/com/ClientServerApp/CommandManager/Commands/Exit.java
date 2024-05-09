package com.ClientServerApp.CommandManager.Commands;

public class Exit implements Command {
    @Override
    public void execute() {
        System.exit(52);
    }
}
