module Database {
    requires org.slf4j;
    requires log4j;
    requires Common;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports com.ClientServerApp.SQLDatabaseManager;
    exports com.ClientServerApp.Statements.DataAboutUsers.CREATE_TABLE;
    exports com.ClientServerApp.Statements.DataAboutUsers.INSERT;
    exports com.ClientServerApp.Statements.DataAboutUsers.SELECT;
    exports com.ClientServerApp.Statements.DataAboutUsers.SELECT_AND_CHECK_ID;

    exports com.ClientServerApp.Statements.UsersTables.CREATE_TABLE;
    exports com.ClientServerApp.Statements.UsersTables.INSERT;
    exports com.ClientServerApp.Statements.UsersTables.SELECT;
    exports com.ClientServerApp.Statements.UsersTables.DELETE;

}