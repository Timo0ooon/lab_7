package com.clientserverapp.clientgui.controllers;

import com.ClientServerApp.ClientApplication.ClientWorking.ClientAuthorizationOrRegistration;
import com.ClientServerApp.ClientApplication.Other.MD5HashString;
import com.clientserverapp.clientgui.Application;
import com.clientserverapp.clientgui.Environment.UserData;
import com.clientserverapp.clientgui.util.Localizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable {
    @FXML public Button cancelButton;
    @FXML public Button saveButton;
    @FXML public PasswordField passwordCheck;
    @FXML public TextField loginCheck;
    @FXML public Label warningText;
    @FXML public Label authorizationLabel;
    @FXML public Label languageLabel;
    @FXML public ChoiceBox<String> languageChoiceBox;
    @FXML public Button saveButton1;
    @FXML public Label nameLabel;
    @FXML public Label passwordLabel;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final ClientAuthorizationOrRegistration clientAuthorizationOrRegistration = new ClientAuthorizationOrRegistration(UserData.socketChannel);

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
                this.warningText.setText(UserData.localizer.getValue("failRegistration"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageChoiceBox.setValue("English");
        this.languageChoiceBox.getItems().addAll("Русский", "Српски", "Latviski", "English");
    }

    public void onSave1(ActionEvent actionEvent) {
        String language = this.languageChoiceBox.getSelectionModel().getSelectedItem();
        Localizer localizer = new Localizer(language);

        this.setLanguage(localizer, actionEvent);
    }

    private void setLanguage(Localizer localizer, ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UserData.localizer = localizer;

        stage.setTitle(localizer.getValue("registration"));
        this.authorizationLabel.setText(localizer.getValue("authorization"));
        this.cancelButton.setText(localizer.getValue("cancel"));
        this.saveButton1.setText(localizer.getValue("save"));
        this.saveButton.setText(localizer.getValue("save"));
        this.passwordCheck.setPromptText(localizer.getValue("password"));
        this.passwordLabel.setText(localizer.getValue("password"));
        this.nameLabel.setText(localizer.getValue("name"));
        this.loginCheck.setPromptText(localizer.getValue("name"));
        this.languageLabel.setText(localizer.getValue("language"));
    }
}
