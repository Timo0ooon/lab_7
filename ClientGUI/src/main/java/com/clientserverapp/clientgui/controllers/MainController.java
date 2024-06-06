package com.clientserverapp.clientgui.controllers;

import com.ClientServerApp.Model.Car.Car;
import com.ClientServerApp.Model.Coordinates.Coordinates;
import com.ClientServerApp.Model.Enums.Mood;
import com.ClientServerApp.Model.Enums.WeaponType;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;
import com.clientserverapp.clientgui.Application;
import com.clientserverapp.clientgui.Environment.UserData;
import com.clientserverapp.clientgui.util.Localizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML public Button saveButton;

    @FXML public Label usernameLabel;

    @FXML public Label messageFromServerLabel;

    @FXML public Label currentUserLabel;

    @FXML public Label messageLabel;

    @FXML public ChoiceBox<String> languageChoiceBox;

    @FXML public Button helpButton;

    @FXML public Label languageLabel;

    @FXML public TableView<HumanBeing> mainTable;

    @FXML public TableColumn<HumanBeing, Integer> idColumn;
    @FXML public TableColumn<HumanBeing, String> nameColumn;
    @FXML public TableColumn<HumanBeing, Coordinates> coordinatesColumn;
    @FXML public TableColumn<HumanBeing, LocalDate> creationDateColumn;
    @FXML public TableColumn<HumanBeing, Boolean> realHeroColumn;
    @FXML public TableColumn<HumanBeing, Boolean> hasToothpickColumn;
    @FXML public TableColumn<HumanBeing, Integer> impactSpeedColumn;
    @FXML public TableColumn<HumanBeing, WeaponType> weaponTypeColumn;
    @FXML public TableColumn<HumanBeing, Mood> moodColumn;
    @FXML public TableColumn<HumanBeing, Car> carColumn;

    @FXML public Button insertButton;
    @FXML public Button clearButton;
    @FXML public Button removeLowerButton;
    @FXML public Button removeGreaterButton;
    @FXML public Button infoButton;
    @FXML public Button saveCollectionButton;
    @FXML public Button averageButton;
    @FXML public Button maxButton;
    @FXML public Button showButton;

    private final ObservableList<HumanBeing> list = FXCollections.observableList( new ArrayList<HumanBeing>() );


    @FXML
    public void onHelp(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = Application.getFXMLLoader("help-view.fxml");
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);

            stage.setTitle("Help");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserData.mainTable = mainTable;

        this.languageChoiceBox.setValue("English");
        this.languageChoiceBox.getItems().addAll("Русский", "Српски", "Latviski", "English");

        this.usernameLabel.setText(UserData.name);

        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        this.creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        this.realHeroColumn.setCellValueFactory(new PropertyValueFactory<>("realHero"));
        this.hasToothpickColumn.setCellValueFactory(new PropertyValueFactory<>("hasToothpick"));
        this.impactSpeedColumn.setCellValueFactory(new PropertyValueFactory<>("impactSpeed"));
        this.weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        this.moodColumn.setCellValueFactory(new PropertyValueFactory<>("mood"));
        this.carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));

        Hashtable<Integer, HumanBeing> collection = UserData.clientWorking.work("show").getCollection();
        if (collection != null) {
            this.list.addAll(collection.values());
            this.mainTable.setItems(this.list);
        }
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        String language = this.languageChoiceBox.getSelectionModel().getSelectedItem();
        Localizer localizer = new Localizer(language);
        this.setLanguage(localizer, actionEvent);
    }

    private void setLanguage(Localizer localizer, ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UserData.localizer = localizer;

        stage.setTitle(localizer.getValue("mainView"));
        this.idColumn.setText(localizer.getValue("idColumn"));
        this.nameColumn.setText(localizer.getValue("nameColumn"));
        this.coordinatesColumn.setText(localizer.getValue("coordinatesColumn"));
        this.creationDateColumn.setText(localizer.getValue("creationDateColumn"));
        this.realHeroColumn.setText(localizer.getValue("realHeroColumn"));
        this.hasToothpickColumn.setText(localizer.getValue("hasToothPickColumn"));
        this.impactSpeedColumn.setText(localizer.getValue("impactSpeedColumn"));
        this.weaponTypeColumn.setText(localizer.getValue("weaponTypeColumn"));
        this.moodColumn.setText(localizer.getValue("moodColumn"));
        this.carColumn.setText(localizer.getValue("carColumn"));
        this.currentUserLabel.setText(localizer.getValue("currentUser"));
        this.languageLabel.setText(localizer.getValue("language"));
        this.messageLabel.setText(localizer.getValue("message"));
        this.saveButton.setText(localizer.getValue("save"));
        this.saveCollectionButton.setText(localizer.getValue("save"));
        this.helpButton.setText(localizer.getValue("help"));
        this.removeLowerButton.setText(localizer.getValue("removeLower"));
        this.removeGreaterButton.setText(localizer.getValue("removeGreater"));
        this.infoButton.setText(localizer.getValue("save"));
        this.infoButton.setText(localizer.getValue("info"));
        this.showButton.setText(localizer.getValue("show"));
        this.averageButton.setText(localizer.getValue("average"));
        this.maxButton.setText(localizer.getValue("max"));
        this.insertButton.setText(localizer.getValue("insert"));
    }

    @FXML
    public void onInsert(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(Application.getFXMLLoader("edit-view.fxml").load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @FXML
    public void onClear(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("clear");
        this.messageFromServerLabel.setText(response.getMessage());

        UserData.mainTable.getItems().clear();
    }

    @FXML
    public void onRemoveLowerButton(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("remove_lower");
        this.messageFromServerLabel.setText(response.getMessage());

        Hashtable<Integer, HumanBeing> collection = UserData.clientWorking.work("show").getCollection();

        if (collection != null) {
            ObservableList<HumanBeing> list = FXCollections.observableList(new ArrayList<>());
            list.addAll(collection.values());
            UserData.mainTable.setItems(list);
        }
        else {
            this.mainTable.getItems().clear();
        }

    }

    @FXML
    public void onRemoveGreater(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("remove_greater");
        this.messageFromServerLabel.setText(response.getMessage());

        Hashtable<Integer, HumanBeing> collection = UserData.clientWorking.work("show").getCollection();

        if (collection != null) {
            ObservableList<HumanBeing> list = FXCollections.observableList(new ArrayList<>());
            list.addAll(collection.values());
            UserData.mainTable.setItems(list);
        }
        else {
            this.mainTable.getItems().clear();
        }
    }

    @FXML
    public void onInfoButton(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("info");
        this.messageFromServerLabel.setText(response.getMessage());
    }

    @FXML
    public void onSaveCollectionButton(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("save");
        this.messageFromServerLabel.setText(response.getMessage());
    }

    @FXML
    public void onAverageButton(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("average_of_impact_speed");
        this.messageFromServerLabel.setText(response.getMessage());
    }

    @FXML
    public void onMaxButton(ActionEvent actionEvent) {
        Response response;

        response = UserData.clientWorking.work("max_by_impact_speed");
        this.messageFromServerLabel.setText(response.getMessage());
        HumanBeing humanBeing = response.getHumanBeing();
        if (humanBeing != null) {
            ObservableList<HumanBeing> list = FXCollections.observableList(new ArrayList<>());
            list.add(humanBeing);
            UserData.mainTable.setItems(list);
        }
    }

    @FXML
    public void onShowButton(ActionEvent actionEvent) {
        Hashtable<Integer, HumanBeing> collection = UserData.clientWorking.work("show").getCollection();

        if (collection != null) {
            ObservableList<HumanBeing> list = FXCollections.observableList(new ArrayList<>());
            list.addAll(collection.values());
            UserData.mainTable.setItems(list);
        }
        else {
            this.mainTable.getItems().clear();
        }
    }
}
