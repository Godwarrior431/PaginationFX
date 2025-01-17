package com.godwarrior.paginationfx.controller.component;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class FilterPaneSeparatorController {

    @FXML
    private CheckBox andCheckBox;

    @FXML
    private CheckBox orCheckBox;

    @FXML
    public void initialize() {
        orCheckBox.setSelected(true);
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

