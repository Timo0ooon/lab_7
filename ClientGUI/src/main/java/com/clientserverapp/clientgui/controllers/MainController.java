package com.clientserverapp.clientgui.controllers;

import com.ClientServerApp.Model.Car.Car;
import com.ClientServerApp.Model.Coordinates.Coordinates;
import com.ClientServerApp.Model.Enums.Mood;
import com.ClientServerApp.Model.Enums.WeaponType;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.ClientServerApp.Response.Response;
import com.clientserverapp.clientgui.Environment.UserData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML public Label commandLabel;

    @FXML public Pagination pagination;

    @FXML public PieChart pieChart;

    @FXML public Label usernameLabel;

    @FXML public Label messageFromServerLabel;

    @FXML public Label messageLabel;

    @FXML public TableView<HumanBeing> mainTable;

    @FXML public TableColumn<HumanBeing, Integer> idColumn;
    @FXML public ChoiceBox<String> idChoiceBox;

    @FXML public TableColumn<HumanBeing, String> nameColumn;
    @FXML public ChoiceBox<String> nameChoiceBox;

    @FXML public TableColumn<HumanBeing, Coordinates> coordinatesColumn;
    @FXML public ChoiceBox<String> coordinatesChoiceBox;

    @FXML public TableColumn<HumanBeing, LocalDate> creationDateColumn;
    @FXML public ChoiceBox<String> creationDateChoiceBox;

    @FXML public TableColumn<HumanBeing, Boolean> realHeroColumn;
    @FXML public ChoiceBox<String> realHeroChoiceBox;

    @FXML public TableColumn<HumanBeing, Boolean> hasToothpickColumn;
    @FXML public ChoiceBox<String> hasToothpickChoiceBox;

    @FXML public TableColumn<HumanBeing, Integer> impactSpeedColumn;
    @FXML public ChoiceBox<String> impactSpeedChoiceBox;

    @FXML public TableColumn<HumanBeing, WeaponType> weaponTypeColumn;
    @FXML public ChoiceBox<String> weaponTypeChoiceBox;

    @FXML public TableColumn<HumanBeing, Mood> moodColumn;
    @FXML public ChoiceBox<String> moodChoiceBox;

    @FXML public TableColumn<HumanBeing, Car> carColumn;
    @FXML public ChoiceBox<String> carChoiceBox;
    @FXML public Button onSaveButton;

    private ObservableList<HumanBeing> initialData(Hashtable<Integer, HumanBeing> collection) {
        return FXCollections.observableArrayList(collection.values());
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
                    String command = matcher.group().split(" ")[1];
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        this.idChoiceBox.getItems().setAll(
                "numbers in ascending order",
                "numbers in descending order"
        );

        this.nameChoiceBox.getItems().setAll(
                "alphabet order",
                "reverse alphabetical order"
        );

        this.coordinatesChoiceBox.getItems().setAll(
                "x: ascending, y: ascending",
                "x: ascending, y: descending",
                "x: descending, y: ascending",
                "x descending, y: descending"
        );

        this.creationDateChoiceBox.getItems().setAll(
                "date ascending",
                "date descending"
        );

        this.realHeroChoiceBox.getItems().setAll(
                "true -> false",
                "false -> true"
        );

        this.hasToothpickChoiceBox.getItems().setAll(
                "true -> false",
                "false -> true"
        );

        this.impactSpeedChoiceBox.getItems().setAll(
                "numbers in ascending order",
                "numbers in descending order"
        );

        this.weaponTypeChoiceBox.getItems().setAll(
                "alphabet order",
                "reverse alphabetical order"
        );

        this.moodChoiceBox.getItems().setAll(
                "alphabet order",
                "reverse alphabetical order"
        );

        this.carChoiceBox.getItems().setAll(
                "alphabet order",
                "reverse alphabetical order"
        );

        this.loadCommandChoiceBox();

        Hashtable<Integer, HumanBeing> collection = this.getDataFromUserAfterAuthorization();
        if (collection != null) {
            this.setData(collection);
        }
    }
}
