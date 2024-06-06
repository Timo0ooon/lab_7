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
import javafx.application.Platform;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.File.separator;

public class MainController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML public Label argumentsLabel;

    @FXML public TextField argumentsTextField;

    @FXML public ChoiceBox<String> commandChoiceBox;

    @FXML public Button saveButton;

    @FXML public Label commandLabel;

    @FXML public Label usernameLabel;

    @FXML public Label messageFromServerLabel;

    @FXML public Label currentUserLabel;

    @FXML public Label messageLabel;

    @FXML public Button executeButton;

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

    private ObservableList<HumanBeing> list;


    private ObservableList<HumanBeing> initialData(Hashtable<Integer, HumanBeing> collection) {
        if (this.list == null) {
            this.list = FXCollections.observableArrayList(collection.values());
            UserData.list = this.list;
        }
        return this.list;
    }

    public void setData(Hashtable<Integer, HumanBeing> collection) {
        this.mainTable.setItems(this.initialData(collection));
    }

    private void loadCommandChoiceBox() {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Files" + separator + "Help.txt"))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\[[a-zA-Z]+]\\s[a-zA-Z_]+\\s(.+|\\{.+})\\s+-.+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String[] elements = matcher.group().split(" ");
                    String command = elements[1];
                    String info = elements[0];
                    if (!(info.equals("[Client]") || info.equals("[Hybrid]")) && !command.equals("insert"))
                        this.commandChoiceBox.getItems().add(command);
                }
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.toString());
            Platform.exit();
        }
        catch (Exception ignored) {}
    }

    private Hashtable<Integer, HumanBeing> getDataFromUserAfterAuthorization() {
        Response response = UserData.clientWorking.work("show");
        return response.getCollection();
    }

    @FXML
    public void onExecute(ActionEvent actionEvent) {
        String command = this.commandChoiceBox.getValue();
        String arguments = this.argumentsTextField.getText();

        if (command != null) {

            String userCommand;
            if (arguments == null || arguments.isEmpty()) userCommand = command;
            else userCommand  = command + " " + "{" + arguments + "}";
            Response response = UserData.clientWorking.work(userCommand);

            Hashtable<Integer, HumanBeing> collection = response.getCollection();
            HumanBeing humanBeing = response.getHumanBeing();

            String message = response.getMessage();

            if (humanBeing != null) {
                this.list.clear();
                this.list.add(humanBeing);
            }

            else if (collection != null) {
                this.setData(collection);
            }

            if (message != null) {
                this.messageFromServerLabel.setText(message);
            }
        }
    }

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

        this.loadCommandChoiceBox();

        Hashtable<Integer, HumanBeing> collection = this.getDataFromUserAfterAuthorization();
        if (collection != null) {
            this.setData(collection);
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
        this.commandLabel.setText(localizer.getValue("command"));
        this.argumentsLabel.setText(localizer.getValue("arguments"));
        this.executeButton.setText(localizer.getValue("execute"));
        this.helpButton.setText(localizer.getValue("help"));
        this.languageLabel.setText(localizer.getValue("language"));
        this.messageLabel.setText(localizer.getValue("message"));
        this.saveButton.setText(localizer.getValue("save"));
        this.insertButton.setText(localizer.getValue("insert"));
    }

    private void getEditView() {
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
    public void onInsert(ActionEvent actionEvent) {
        this.getEditView();
    }
}
