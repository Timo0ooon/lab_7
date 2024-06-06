package com.clientserverapp.clientgui.controllers;

import com.ClientServerApp.Model.Car.Car;
import com.ClientServerApp.Model.Coordinates.Coordinates;
import com.ClientServerApp.Model.Enums.Mood;
import com.ClientServerApp.Model.Enums.WeaponType;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.clientserverapp.clientgui.Environment.UserData;
import com.clientserverapp.clientgui.util.Localizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    @FXML public Label formLabel;
    @FXML public Label nameLabel;
    @FXML public Label coordinatesLabel;
    @FXML public Label realHeroLabel;
    @FXML public Label hasToothpickLabel;
    @FXML public Label impactSpeedLabel;
    @FXML public Label weaponTypeLabel;
    @FXML public Label moodLabel;
    @FXML public Label carLabel;
    @FXML public TextField coordinatesInput;
    @FXML public TextField nameInput;
    @FXML public TextField carInput;
    @FXML public TextField impactSpeedInput;
    @FXML public ChoiceBox<String> realHeroChoice;
    @FXML public ChoiceBox<String> hasToothPickChoice;
    @FXML public ChoiceBox<String> weaponTypeChoice;
    @FXML public ChoiceBox<String> moodChoice;
    @FXML public Label languageLabel;
    @FXML public ChoiceBox<String> languageChoice;
    @FXML public Button saveLanguageButton;
    @FXML public Button saveButton;
    @FXML public Label keyLabel;
    @FXML public TextField keyInput;
    private static final Logger logger = LoggerFactory.getLogger(EditController.class);

    @FXML
    public void onSaveButton(ActionEvent actionEvent) {
        try {
            String[] userCoordinates = Arrays.stream(this.coordinatesInput.getText().split(";")).map(String::trim).toArray(String[]::new);
            HumanBeing[] objects = new HumanBeing[1];

            String key = this.keyInput.getText();
            String name = this.nameInput.getText();
            Coordinates coordinates = new Coordinates(Double.parseDouble(userCoordinates[0]), Long.parseLong(userCoordinates[1]));
            Boolean realHero = Boolean.parseBoolean(this.realHeroChoice.getValue());
            Boolean hasTooth = Boolean.parseBoolean(this.hasToothPickChoice.getValue());
            Integer impactSpeed = Integer.valueOf(this.impactSpeedInput.getText());
            WeaponType weaponType = WeaponType.valueOf(this.weaponTypeChoice.getValue());
            Mood mood = Mood.valueOf(this.moodChoice.getValue());
            Car car = new Car(this.carInput.getText());

            HumanBeing humanBeing = new HumanBeing(
                    null, name, coordinates, LocalDate.now(), realHero, hasTooth, impactSpeed, weaponType, mood, car
            );
            objects[0] = humanBeing;
            UserData.clientWorking.work("insert {" + key + "}", objects);

            Hashtable<Integer, HumanBeing> collection = UserData.clientWorking.work("show").getCollection();

            ObservableList<HumanBeing> list = FXCollections.observableList(new ArrayList<>());
            list.addAll(collection.values());
            UserData.mainTable.setItems(list);

        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @FXML
    public void onSaveLanguageButton(ActionEvent actionEvent) {
        String language = this.languageChoice.getSelectionModel().getSelectedItem();
        Localizer localizer = new Localizer(language);
        this.setLanguage(localizer, actionEvent);
    }

    private void setLanguage(Localizer localizer, ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UserData.localizer = localizer;


        stage.setTitle(localizer.getValue("editView"));
        this.formLabel.setText(localizer.getValue("form"));
        this.nameLabel.setText(localizer.getValue("formName"));
        this.coordinatesLabel.setText(localizer.getValue("coordinatesColumn"));
        this.realHeroLabel.setText(localizer.getValue("realHeroColumn"));
        this.hasToothpickLabel.setText(localizer.getValue("hasToothPickColumn"));
        this.impactSpeedLabel.setText(localizer.getValue("impactSpeedColumn"));
        this.weaponTypeLabel.setText(localizer.getValue("weaponTypeColumn"));
        this.moodLabel.setText(localizer.getValue("moodColumn"));
        this.carLabel.setText(localizer.getValue("carColumn"));

        this.saveButton.setText(localizer.getValue("save"));
        this.saveLanguageButton.setText(localizer.getValue("save"));
        this.languageLabel.setText(localizer.getValue("language"));
        this.keyLabel.setText(localizer.getValue("key"));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.languageChoice.setValue("English");

        this.realHeroChoice.getItems().addAll("true", "false");
        this.hasToothPickChoice.getItems().addAll("true", "false");
        this.weaponTypeChoice.getItems().addAll(Arrays.stream(WeaponType.values()).map(Enum::toString).toArray(String[]::new));
        this.moodChoice.getItems().addAll(Arrays.stream(Mood.values()).map(Enum::toString).toArray(String[]::new));
        this.languageChoice.getItems().addAll("Русский", "Српски", "Latviski", "English");
    }
}
