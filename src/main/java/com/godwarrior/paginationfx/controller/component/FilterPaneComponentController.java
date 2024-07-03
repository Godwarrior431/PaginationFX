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
    private Label attributeLabel;

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

        attributeLabel.setText(filterApplied.getAttributeName());
        operatorLabel.setText(filterApplied.getOperatorName());
        valueTextLabel.setText(filterApplied.getValueQuery());
    }

    public void setParentController(FilterPaneController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void deleteFilter(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox filterComponent = (HBox) button.getParent().getParent(); // Ajuste para obtener el componente adecuado
        parentController.removeFilterComponent(filterComponent);
    }


    public FilterApplied getFilterApplied() {
        return filterApplied;
    }

    public void setFilterApplied(FilterApplied filterApplied) {
        this.filterApplied = filterApplied;
    }
}
