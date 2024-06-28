package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.models.Filter;
import com.godwarrior.paginationfx.models.Operator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FilterPaneController {

    private List<Filter> filterList;

    @FXML
    private Button addFilter;

    @FXML
    private ImageView addFilterImgView;

    @FXML
    private HBox fieldContainer;

    @FXML
    private ComboBox<Filter> attributeComboBox; // Cambiado a ComboBox<Filter>

    @FXML
    private ImageView filterImgView;

    @FXML
    private ComboBox<Operator> predicatesComboBox; // Cambiado a ComboBox<Operator>

    @FXML
    private ImageView resetFilterImgView;


    @FXML
    public void initialize(List<Filter> filterList) {
        this.filterList = filterList;

        addFilterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/addIcon.png"))));
        filterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/addFilterIcon.png"))));
        resetFilterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/resetForms.png"))));

        // Populate attributeComboBox with filterList
        ObservableList<Filter> filters = FXCollections.observableArrayList(filterList);
        attributeComboBox.setItems(filters);

        // Add listener to attributeComboBox to update predicatesComboBox
        attributeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePredicatesComboBox(newValue);
                updateFieldContainer(newValue.getAttributeType());
            }
        });

        attributeComboBox.getSelectionModel().selectFirst();
    }

    private void updatePredicatesComboBox(Filter filter) {
        ObservableList<Operator> operatorTexts = FXCollections.observableArrayList(filter.getOperators());
        predicatesComboBox.setItems(operatorTexts);
    }

    private void updateFieldContainer(String attributeType) {
        createFieldForType(attributeType);
    }

    private void createFieldForType(String type) {
        fieldContainer.getChildren().clear();

        switch (type.toLowerCase()) {
            case "text":
                TextField textField = createTextField("[a-zA-Z\\dáéíóúÁÉÍÓÚñÑ ]*");
                textField.setStyle("-fx-pref-height: 35px; -fx-font-size: 15px;");
                fieldContainer.getChildren().add(textField);
                break;
            case "number":
                TextField numberField = createTextField("\\d*");
                numberField.setStyle("-fx-pref-height: 35px; -fx-font-size: 15px;");
                fieldContainer.getChildren().add(numberField);
                break;
            case "date":
                DatePicker datePicker = createDatePicker();
                datePicker.setStyle("-fx-pref-height: 35px; -fx-font-size: 15px;");
                fieldContainer.getChildren().add(datePicker);
                break;
            case "time":
                HBox timeFields = createTimeFields();
                timeFields.setStyle("-fx-pref-height: 35px; -fx-font-size: 15px;");
                fieldContainer.getChildren().add(timeFields);
                break;
            case "bool":
                CheckBox checkBox = createCheckBox();
                checkBox.setStyle("-fx-font-size: 15px;");
                fieldContainer.getChildren().add(checkBox);
                break;
            default:
                throw new IllegalArgumentException("Tipo de dato no válido para los campos: " + type);
        }
    }

    private TextField createTextField(String regex) {
        TextField textField = new TextField();
        Pattern pattern = Pattern.compile(regex);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) {
                textField.setText(oldValue);
            }
        });

        return textField;
    }

    private DatePicker createDatePicker() {
        return new DatePicker();
    }

    private HBox createTimeFields() {
        TextField hoursField = createTextField("\\d*");
        TextField minutesField = createTextField("\\d*");

        hoursField.setPromptText("HH");
        minutesField.setPromptText("MM");

        HBox timeFields = new HBox(hoursField, new Label(":"), minutesField);
        timeFields.setAlignment(Pos.CENTER);
        return timeFields;
    }

    private CheckBox createCheckBox() {
        CheckBox checkBox = new CheckBox("False");
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            checkBox.setText(newValue ? "True" : "False");
        });
        return checkBox;
    }

    @FXML
    void applyFilters(ActionEvent event) {
        // Implement your filter application logic here
    }

    @FXML
    void resetFilters(ActionEvent event) {
        attributeComboBox.getSelectionModel().selectFirst();
        predicatesComboBox.getItems().clear();
        fieldContainer.getChildren().clear();
    }
}
