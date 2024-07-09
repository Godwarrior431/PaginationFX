package com.godwarrior.paginationfx.controller.component;

import com.godwarrior.paginationfx.controller.FilterPaneController;
import com.godwarrior.paginationfx.models.FilterApplied;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class FilterPaneComponentController {

    private FilterPaneController parentController;

    @FXML
    private Label filterNameSelectLabel;

    @FXML
    private ImageView deleteImgView;

    @FXML
    private Label operatorLabel;

    @FXML
    private Label valueTextLabel;

    @FXML
    private FilterApplied filterApplied;

    @FXML
    public void initialize(FilterApplied filterApplied) {
        this.filterApplied = filterApplied;
        deleteImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/deleteIcon.png"))));

        filterNameSelectLabel.setText(filterApplied.getFilterNameSelect());
        operatorLabel.setText(filterApplied.getOperatorName());

        String valueQuery = filterApplied.getValueQuery();

        if (valueQuery != null) {
            while (valueQuery.startsWith("%")) {
                valueQuery = valueQuery.substring(1);
            }
            while (valueQuery.endsWith("%")) {
                valueQuery = valueQuery.substring(0, valueQuery.length() - 1);
            }
        }

        valueTextLabel.setText(valueQuery);
    }


    public void setParentController(FilterPaneController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void deleteFilter(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox filterComponent = (HBox) button.getParent().getParent();
        parentController.removeFilterComponent(filterComponent);
    }

    public FilterApplied getFilterApplied() {
        return filterApplied;
    }

}
