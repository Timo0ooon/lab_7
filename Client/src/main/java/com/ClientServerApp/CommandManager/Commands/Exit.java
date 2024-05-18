package com.ClientServerApp.CommandManager.Commands;

/**
 * Client side command.
 */
public class Exit implements Command {
    /**
     * Command to terminate the program
     */
    @Override
    public void execute() {
        System.exit(52);
    }
}
