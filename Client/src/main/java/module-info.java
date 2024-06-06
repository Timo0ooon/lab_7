module Client {
    requires Common;

    exports com.ClientServerApp.ClientApplication.ClientWorking;
    exports com.ClientServerApp.ClientApplication.Other;
    exports com.ClientServerApp.ClientApplication.LocalManagers;

    exports com.ClientServerApp.CommandManager.Commands;
    exports com.ClientServerApp.CommandManager;

    exports com.ClientServerApp.UserInput;
}