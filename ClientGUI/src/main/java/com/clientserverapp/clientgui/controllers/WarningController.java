package com.clientserverapp.clientgui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WarningController {
    @FXML public Label warningLabel;

    public void updateText(String text) {
        this.warningLabel.setText(text);
    }
}
