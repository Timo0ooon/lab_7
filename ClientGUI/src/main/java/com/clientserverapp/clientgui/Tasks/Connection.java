package com.clientserverapp.clientgui.Tasks;

import com.ClientServerApp.ClientApplication.ClientWorking.ClientWorking;
import com.ClientServerApp.ClientApplication.ClientWorking.ServerConnection;
import com.ClientServerApp.Environment;
import com.clientserverapp.clientgui.Application;
import com.clientserverapp.clientgui.Environment.UserData;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.nio.channels.SocketChannel;

public class Connection implements Runnable {
    private final Logger logger;
    private final Stage stage;

    public Connection(Logger logger, Stage stage) {
        this.logger = logger;
        this.stage = stage;
    }

    @Override
    public void run()  {
        int connectionCount = 0;
        SocketChannel socketChannel;
        do {
            socketChannel = ServerConnection.connect(Environment.HOST, Environment.PORT);
            connectionCount++;
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        while (socketChannel == null);

        logger.info("Connected to {}:{} , count of connection: {}", Environment.HOST, Environment.PORT, connectionCount);
        UserData.socketChannel = socketChannel;
        UserData.clientWorking = new ClientWorking(socketChannel);

        // По сути из потока Connection-Thread я обращаюсь к потоку JavaFX
        Platform.runLater(() -> {
            try {
                Scene scene = new Scene(Application.getFXMLLoader("registration-view.fxml").load(), 600, 700);
                stage.setTitle("Registration");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                 logger.error(e.getMessage());
            }
        });
    }
}
