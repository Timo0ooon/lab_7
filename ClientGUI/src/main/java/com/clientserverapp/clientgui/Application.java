package com.clientserverapp.clientgui;

import com.clientserverapp.clientgui.Environment.UserData;
import com.clientserverapp.clientgui.Tasks.Connection;
import com.clientserverapp.clientgui.util.Localizer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.io.File.separator;


public class Application extends javafx.application.Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    static {
        PropertyConfigurator.configure("ClientGUI" + separator + "src"  + separator +  "main"
                + separator + "resources"  + separator + "log4j.properties");
    }

    private void setRegistrationView(Stage stage) throws IOException {
        UserData.localizer = new Localizer("English");
        FXMLLoader fxmlLoader = getFXMLLoader("loading-view.fxml");
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);

        stage.setTitle("Loading");
        stage.setScene(scene);
        stage.show();

        Thread thread = new Thread(new Connection(logger, stage));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.setRegistrationView(stage);

    }

    public static FXMLLoader getFXMLLoader(String fileName) throws IOException {
        return new FXMLLoader(Application.class.getResource(fileName));
    }

    public static void main(String[] args) {
        launch(args);
    }
}



