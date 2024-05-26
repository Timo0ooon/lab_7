package com.clientserverapp.clientgui.controllers;

import com.clientserverapp.clientgui.Environment.UserData;
import com.clientserverapp.clientgui.LanguageSetting.Languages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML public Button saveButton;
    @FXML public ChoiceBox<String> languageSelection;


    @FXML
    public void onSave(ActionEvent actionEvent) {
        String language = this.languageSelection.getSelectionModel().getSelectedItem();
        HashMap<String, Languages> values = new HashMap<>();

        values.put("Русский", Languages.Russian);
        values.put("Српски", Languages.Serbian);
        values.put("Latviski", Languages.Latvian);
        values.put("English", Languages.English);

        UserData.language = values.get(language);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageSelection.setValue("English");
        this.languageSelection.getItems().addAll("Русский", "Српски", "Latviski", "English");
    }
}
