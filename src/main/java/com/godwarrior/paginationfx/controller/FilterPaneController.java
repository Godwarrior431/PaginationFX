package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.application.Usuario;
import com.godwarrior.paginationfx.controller.component.FilterPaneComponentController;
import com.godwarrior.paginationfx.controller.component.FilterPaneSeparatorController;
import com.godwarrior.paginationfx.models.ArithmeticOperator;
import com.godwarrior.paginationfx.models.FilterPagApplied;
import com.godwarrior.paginationfx.models.FilterPagTable;
import com.godwarrior.paginationfx.utils.JavaUtilsFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FilterPaneController {

    private List<FilterPagApplied> currentFiltersApplied = new ArrayList<>();

    @FXML
    private ImageView addFilterImgView;

    @FXML
    private HBox fieldContainer;

    @FXML
    private VBox appliedFilterContainer;

    @FXML
    private ComboBox<FilterPagTable> attributeComboBox;

    @FXML
    private ImageView filterImgView;

    @FXML
    private ComboBox<ArithmeticOperator> predicatesComboBox;

    @FXML
    private ImageView resetFilterImgView;

    @FXML
    public void initialize(List<FilterPagTable> filterPagTableList, List<FilterPagApplied> filterPagAppliedList) {
        this.currentFiltersApplied = filterPagAppliedList;

        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/addIcon.png", addFilterImgView);
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/addFilterIcon.png", filterImgView);
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/resetForms.png", resetFilterImgView);

        ObservableList<FilterPagTable> filterPagTables = FXCollections.observableArrayList(filterPagTableList);
        attributeComboBox.setItems(filterPagTables);

        attributeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePredicatesComboBox(newValue);
                createFieldForType(newValue.getAttributeType());
            }
        });

        attributeComboBox.getSelectionModel().selectFirst();
        fillAppliedFilters();
    }

    private void updatePredicatesComboBox(FilterPagTable filterPagTable) {
        ObservableList<ArithmeticOperator> arithmeticOperatorTexts = FXCollections.observableArrayList(filterPagTable.getOperators());
        predicatesComboBox.setItems(arithmeticOperatorTexts);
    }

    private void createFieldForType(String type) {
        fieldContainer.getChildren().clear();
        String styleToApply = "-fx-pref-height: 35px; -fx-font-size: 15px;";
        switch (type.toLowerCase()) {
            case "text":
                TextField textField = createTextField("[a-zA-Z\\dáéíóúÁÉÍÓÚñÑ ]*");
                textField.setStyle(styleToApply);
                fieldContainer.getChildren().add(textField);
                break;
            case "number":
                TextField numberField = createTextField("\\d*");
                numberField.setStyle(styleToApply);
                fieldContainer.getChildren().add(numberField);
                break;
            case "date":
                DatePicker datePicker = createDatePicker();
                datePicker.setStyle(styleToApply);
                fieldContainer.getChildren().add(datePicker);
                break;
            case "time":
                HBox timeFields = createTimeFields();
                timeFields.setStyle(styleToApply);
                fieldContainer.getChildren().add(timeFields);
                break;
            case "bool":
                CheckBox checkBox = createCheckBox();
                checkBox.setStyle("-fx-font-size: 15px;");
                fieldContainer.getChildren().add(checkBox);
                if (!predicatesComboBox.getItems().isEmpty()) {
                    predicatesComboBox.getSelectionModel().selectFirst();
                }
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
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);
        return datePicker;
    }

    private HBox createTimeFields() {
        TextField hoursField = new TextField();
        hoursField.setPromptText("HH");
        hoursField.setStyle("-fx-font-size: 16px;");
        hoursField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                hoursField.setText(oldValue);
            }
        });

        TextField minutesField = new TextField();
        minutesField.setPromptText("MM");
        minutesField.setStyle("-fx-font-size: 16px;");
        minutesField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                minutesField.setText(oldValue);
            }
        });

        Label separator = new Label(":");
        separator.setFont(Font.font("System", FontWeight.BOLD, 18));
        separator.setPrefWidth(10);

        HBox timeFields = new HBox(hoursField, separator, minutesField);
        timeFields.setAlignment(Pos.CENTER);
        timeFields.setSpacing(5);

        return timeFields;
    }

    private CheckBox createCheckBox() {
        CheckBox checkBox = new CheckBox("True");
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> checkBox.setText(newValue ? "True" : "False"));
        checkBox.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        return checkBox;
    }


    public void removeFilterComponent(Pane filterComponent) {
        int index = appliedFilterContainer.getChildren().indexOf(filterComponent);

        if (index > 0 && appliedFilterContainer.getChildren().get(index - 1) instanceof HBox) {
            appliedFilterContainer.getChildren().remove(index - 1);
        } else if (index < appliedFilterContainer.getChildren().size() - 1 && appliedFilterContainer.getChildren().get(index + 1) instanceof HBox) {
            appliedFilterContainer.getChildren().remove(index + 1);
        }

        appliedFilterContainer.getChildren().remove(filterComponent);
    }

    @FXML
    void addFilter() {
        FilterPagTable selectedFilterPagTable = attributeComboBox.getSelectionModel().getSelectedItem();
        ArithmeticOperator selectedArithmeticOperator = predicatesComboBox.getSelectionModel().getSelectedItem();
        String value = getFieldValue();

        if (selectedFilterPagTable != null && selectedArithmeticOperator != null && value != null && !value.isEmpty()) {
            String attributeLabel = JavaUtilsFunctions.getColumnLabel(Usuario.class, selectedFilterPagTable.getAttributeObjectName());

            value = switch (selectedArithmeticOperator.operatorText().toLowerCase()) {
                case "starts with" -> value + "%";
                case "ends with" -> "%" + value;
                case "contains" -> "%" + value + "%";
                default -> value;
            };

            if (!appliedFilterContainer.getChildren().isEmpty()) {
                addSeparator(null);
            }
            addFilterComponent(new FilterPagApplied(
                    selectedFilterPagTable.getFilterNameSelect(),
                    attributeLabel != null ? attributeLabel : selectedFilterPagTable.getAttributeObjectName(),
                    selectedArithmeticOperator.operatorText(),
                    selectedArithmeticOperator.operatorSql(),
                    value,
                    this.attributeComboBox.getSelectionModel().getSelectedItem().getAttributeType()));
        }
    }

    private void addFilterComponent(FilterPagApplied filterPagApplied) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/component/FilterPaneComponentView.fxml"));
            Pane filterComponent = loader.load();

            FilterPaneComponentController componentController = loader.getController();
            componentController.setParentController(this);
            componentController.initialize(filterPagApplied);

            filterComponent.getProperties().put("controller", componentController);

            appliedFilterContainer.getChildren().add(filterComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSeparator(String operator) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/component/FilterPaneSeparatorView.fxml"));
            HBox separator = loader.load();
            FilterPaneSeparatorController separatorController = loader.getController();

            separator.getProperties().put("controller", separatorController);

            if ("AND".equals(operator)) {
                separatorController.getAndCheckBox().setSelected(true);
            } else {
                separatorController.getOrCheckBox().setSelected(true);
            }

            appliedFilterContainer.getChildren().add(separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFieldValue() {
        switch (attributeComboBox.getSelectionModel().getSelectedItem().getAttributeType().toLowerCase()) {
            case "number", "text":
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

    private void fillAppliedFilters() {
        if (currentFiltersApplied != null && !currentFiltersApplied.isEmpty()) {
            for (FilterPagApplied filterPagApplied : currentFiltersApplied) {
                if ("AND".equals(filterPagApplied.getQueryOperatorQuery()) || "OR".equals(filterPagApplied.getQueryOperatorQuery())) {
                    addSeparator(filterPagApplied.getQueryOperatorQuery());
                } else {
                    addFilterComponent(filterPagApplied);
                }
            }
        }
    }

    @FXML
    void applyFilters() {
        currentFiltersApplied.clear();

        for (Node node : appliedFilterContainer.getChildren()) {
            if (node instanceof Pane && node.getProperties().get("controller") instanceof FilterPaneComponentController componentController) {
                FilterPagApplied filterPagApplied = componentController.getFilterApplied();
                if (filterPagApplied != null) {
                    currentFiltersApplied.add(filterPagApplied);
                }
            } else if (node instanceof HBox && node.getProperties().get("controller") instanceof FilterPaneSeparatorController separatorController) {
                if (separatorController.getAndCheckBox().isSelected()) {
                    currentFiltersApplied.add(new FilterPagApplied("AND"));
                } else if (separatorController.getOrCheckBox().isSelected()) {
                    currentFiltersApplied.add(new FilterPagApplied("OR"));
                }
            }
        }
        Stage stage = (Stage) attributeComboBox.getScene().getWindow();
        stage.close();
    }

    public List<FilterPagApplied> getCurrentFiltersApplied() {
        return currentFiltersApplied;
    }

    @FXML
    void resetFilters(ActionEvent event) {
        predicatesComboBox.getItems().clear();
        fieldContainer.getChildren().clear();
        appliedFilterContainer.getChildren().clear();
        attributeComboBox.getSelectionModel().selectFirst();
        this.createFieldForType(attributeComboBox.getSelectionModel().getSelectedItem().getAttributeType());
    }
}
