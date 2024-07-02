package com.godwarrior.paginationfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class FilterPaneSeparatorController {

    @FXML
    private CheckBox andCheckBox;

    @FXML
    private CheckBox orCheckBox;

    @FXML
    public void initialize() {
        // Set the default selection to "orCheckBox"
        orCheckBox.setSelected(true);

        // Add listeners to ensure only one checkbox is selected at a time
        andCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                orCheckBox.setSelected(false);
            } else if (!orCheckBox.isSelected()) {
                andCheckBox.setSelected(true);
            }
        });

        orCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                andCheckBox.setSelected(false);
            } else if (!andCheckBox.isSelected()) {
                orCheckBox.setSelected(true);
            }
        });
    }

    public CheckBox getAndCheckBox() {
        return andCheckBox;
    }

    public CheckBox getOrCheckBox() {
        return orCheckBox;
    }
}

