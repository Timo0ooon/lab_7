package com.clientserverapp.clientgui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.File.separator;

public class HelpController implements Initializable {

    @FXML public ScrollPane scrollPane;
    @FXML public TextArea firstTextArea;
    @FXML public TextArea secondTextArea;

    private static final Logger logger = LoggerFactory.getLogger(HelpController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Files" + separator + "Help.txt"));
        )
        {
            String line;
            StringBuilder commandPart = new StringBuilder();
            StringBuilder descriptionPart = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("-.+$");
                Pattern pattern1 = Pattern.compile("^\\[[a-zA-Z]+]\\s\\w+");

                Matcher matcherDescription = pattern.matcher(line);
                Matcher matcherCommand = pattern1.matcher(line);
                if (matcherCommand.find() && matcherDescription.find()) {
                    commandPart.append(matcherCommand.group().split(" ")[1]).append("\n");
                    descriptionPart.append(matcherDescription.group()).append("\n");
                }
            }
            this.firstTextArea.setText(commandPart.toString());
            this.secondTextArea.setText(descriptionPart.toString());

        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }
}
