module com.clientserverapp.clientgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires log4j;
    requires Client;
    requires Common;
    requires java.xml;

    opens com.clientserverapp.clientgui to javafx.fxml;
    exports com.clientserverapp.clientgui;
    exports com.clientserverapp.clientgui.controllers;
    opens com.clientserverapp.clientgui.controllers to javafx.fxml;
}