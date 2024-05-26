package com.clientserverapp.clientgui.controllers;

import com.ClientServerApp.ClientApplication.ClientWorking.ClientAuthorizationOrRegistration;
import com.ClientServerApp.ClientApplication.Other.MD5HashString;
import com.clientserverapp.clientgui.Application;
import com.clientserverapp.clientgui.Environment.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RegistrationController {
    @FXML public Button cancelButton;
    @FXML public Button saveButton;
    @FXML public PasswordField passwordCheck;
    @FXML public TextField loginCheck;
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final ClientAuthorizationOrRegistration clientAuthorizationOrRegistration = new ClientAuthorizationOrRegistration(UserData.socketChannel);
    @FXML public Label warningText;
    @FXML public Button settingsButton;



    @FXML
    public void onSave(ActionEvent actionEvent) {
        String login = this.loginCheck.getText();
        String password = MD5HashString.createHashedPassword(this.passwordCheck.getText());

        try {
            boolean isRegistered = this.clientAuthorizationOrRegistration.register(login, password);
            logger.info("Client: {}, Password {} sent data to server", login, password);
            if (isRegistered) {

                UserData.name = login;
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(Application.getFXMLLoader("main-view.fxml").load(), 1200, 800));
                stage.setTitle("Main-Menu");
                stage.show();
            }
            else {
                this.warningText.setText("Authorization failed!");
                logger.info("Client: {} canceled", login);
                this.onCancel(actionEvent);
            }

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        this.loginCheck.clear();
        this.passwordCheck.clear();
    }

    @FXML
    public void onSettings(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(Application.getFXMLLoader("settings-view.fxml").load(), 300, 200));
        stage.setTitle("Settings");
        stage.show();
    }
}
