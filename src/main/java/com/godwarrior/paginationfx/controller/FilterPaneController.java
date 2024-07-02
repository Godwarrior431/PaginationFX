package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.models.Filter;
import com.godwarrior.paginationfx.models.FilterApplied;
import com.godwarrior.paginationfx.models.Operator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FilterPaneController {

    private  List<FilterApplied> filtersApplied = FXCollections.observableArrayList();

    @FXML
    private ImageView addFilterImgView;

    @FXML
    private HBox fieldContainer;

    @FXML
    private VBox appliedFilterContainer;

    @FXML
    private ComboBox<Filter> attributeComboBox;

    @FXML
    private ImageView filterImgView;

    @FXML
    private ComboBox<Operator> predicatesComboBox;

    @FXML
    private ImageView resetFilterImgView;

    @FXML
    public void initialize(List<Filter> filterList , List<FilterApplied> filterAppliedList) {
        this.filtersApplied = filterAppliedList;
        addFilterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/addIcon.png"))));
        filterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/addFilterIcon.png"))));
        resetFilterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/resetForms.png"))));

        ObservableList<Filter> filters = FXCollections.observableArrayList(filterList);
        attributeComboBox.setItems(filters);

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

    public void removeFilterComponent(Pane filterComponent) {
        int index = appliedFilterContainer.getChildren().indexOf(filterComponent);

        if (index > 0 && appliedFilterContainer.getChildren().get(index - 1) instanceof HBox) {
            // Remove the separator before the filter component
            appliedFilterContainer.getChildren().remove(index - 1);
        } else if (index < appliedFilterContainer.getChildren().size() - 1 && appliedFilterContainer.getChildren().get(index + 1) instanceof HBox) {
            // Remove the separator after the filter component if there is no component before it
            appliedFilterContainer.getChildren().remove(index + 1);
        }

        appliedFilterContainer.getChildren().remove(filterComponent);
    }


    @FXML
    void addFilter() {
        Filter selectedFilter = attributeComboBox.getSelectionModel().getSelectedItem();
        Operator selectedOperator = predicatesComboBox.getSelectionModel().getSelectedItem();
        String value = getFieldValue();

        if (selectedFilter != null && selectedOperator != null && value != null && !value.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/FilterPaneComponentView.fxml"));
                Pane filterComponent = loader.load();

                FilterPaneComponentController componentController = loader.getController();
                componentController.setParentController(this);

                FilterApplied filterApplied = new FilterApplied(selectedFilter.getAttributeName(), selectedOperator.getText(), selectedOperator.getSql(), value);
                componentController.initialize(filterApplied);

                // Set the controller as a property of the filter component
                filterComponent.getProperties().put("controller", componentController);

                if (!appliedFilterContainer.getChildren().isEmpty()) {
                    addSeparator();
                }

                appliedFilterContainer.getChildren().add(filterComponent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addSeparator() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/FilterPaneSeparatorView.fxml"));
            HBox separator = loader.load();
            FilterPaneSeparatorController separatorController = loader.getController();

            separator.getProperties().put("controller", separatorController);

            appliedFilterContainer.getChildren().add(separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getFieldValue() {
        switch (attributeComboBox.getSelectionModel().getSelectedItem().getAttributeType().toLowerCase()) {
            case "text":
            case "number":
                TextField textField = (TextField) fieldContainer.getChildren().get(0);
                return textField.getText();
            case "date":
                DatePicker datePicker = (DatePicker) fieldContainer.getChildren().get(0);
                return datePicker.getValue() != null ? datePicker.getValue().toString() : "";
            case "time":
                HBox timeFields = (HBox) fieldContainer.getChildren().get(0);
                TextField hoursField = (TextField) timeFields.getChildren().get(0);
                TextField minutesField = (TextField) timeFields.getChildren().get(2);
                return hoursField.getText() + ":" + minutesField.getText();
            case "bool":
                CheckBox checkBox = (CheckBox) fieldContainer.getChildren().get(0);
                return checkBox.isSelected() ? "True" : "False";
            default:
                throw new IllegalArgumentException("Tipo de dato no válido para los campos");
        }
    }

    @FXML
    void applyFilters(ActionEvent event) {
        List<FilterApplied> filtersApplied = new ArrayList<>();

        for (Node node : appliedFilterContainer.getChildren()) {
            if (node instanceof Pane && node.getProperties().get("controller") instanceof FilterPaneComponentController) {
                // Recuperar el controlador del componente de filtro
                FilterPaneComponentController componentController = (FilterPaneComponentController) node.getProperties().get("controller");
                if (componentController != null) {
                    FilterApplied filterApplied = componentController.getFilterApplied();
                    if (filterApplied != null) {
                        filtersApplied.add(filterApplied);
                    }
                }
            } else if (node instanceof HBox && node.getProperties().get("controller") instanceof FilterPaneSeparatorController) {
                // Recuperar el controlador del separador
                FilterPaneSeparatorController separatorController = (FilterPaneSeparatorController) node.getProperties().get("controller");
                if (separatorController != null) {
                    // Verificar el estado de los checkboxes y crear un FilterApplied para el operador lógico
                    if (separatorController.getAndCheckBox().isSelected()) {
                        filtersApplied.add(new FilterApplied("AND"));
                    } else if (separatorController.getOrCheckBox().isSelected()) {
                        filtersApplied.add(new FilterApplied("OR"));
                    }
                }
            }
        }

        // Implementar lógica para aplicar los filtros
        for (FilterApplied filter : filtersApplied) {
            System.out.println("Filter Applied: " + filter.getAttributeName() + " " + filter.getOperatorName() + " " + filter.getQueryOperatorQuery() + " " + filter.getValueQuery());
        }
    }

    @FXML
    void resetFilters(ActionEvent event) {
        attributeComboBox.getSelectionModel().selectFirst();
        predicatesComboBox.getItems().clear();
        fieldContainer.getChildren().clear();
        appliedFilterContainer.getChildren().clear();  // Clear applied filters as well
    }


    public List<FilterApplied> getFiltersApplied() {
        return filtersApplied;
    }
}
