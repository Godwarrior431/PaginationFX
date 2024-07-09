package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.controller.component.FilterPaneComponentController;
import com.godwarrior.paginationfx.controller.component.FilterPaneSeparatorController;
import com.godwarrior.paginationfx.models.Filter;
import com.godwarrior.paginationfx.models.FilterApplied;
import com.godwarrior.paginationfx.models.Operator;
import com.godwarrior.paginationfx.models.Usuario;
import com.godwarrior.paginationfx.utils.JavaUtils;
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

    private List<FilterApplied> currentFiltersApplied = new ArrayList<>();

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
    public void initialize(List<Filter> filterList, List<FilterApplied> filterAppliedList) {
        this.currentFiltersApplied = filterAppliedList;

        JavaUtils.setImage("/com/godwarrior/paginationfx/resources/icons/addIcon.png", addFilterImgView);
        JavaUtils.setImage("/com/godwarrior/paginationfx/resources/icons/addFilterIcon.png", filterImgView);
        JavaUtils.setImage("/com/godwarrior/paginationfx/resources/icons/resetForms.png", resetFilterImgView);

        ObservableList<Filter> filters = FXCollections.observableArrayList(filterList);
        attributeComboBox.setItems(filters);

        attributeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePredicatesComboBox(newValue);
                createFieldForType(newValue.getAttributeType());
            }
        });

        attributeComboBox.getSelectionModel().selectFirst();
        fillAppliedFilters();
    }

    private void updatePredicatesComboBox(Filter filter) {
        ObservableList<Operator> operatorTexts = FXCollections.observableArrayList(filter.getOperators());
        predicatesComboBox.setItems(operatorTexts);
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
        CheckBox checkBox = new CheckBox("False");
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> checkBox.setText(newValue ? "True" : "False"));
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
        Filter selectedFilter = attributeComboBox.getSelectionModel().getSelectedItem();
        Operator selectedOperator = predicatesComboBox.getSelectionModel().getSelectedItem();
        String value = getFieldValue();

        if (selectedFilter != null && selectedOperator != null && value != null && !value.isEmpty()) {
            String attributeLabel = JavaUtils.getColumnLabel(Usuario.class, selectedFilter.getAttributeClassName());

            value = switch (selectedOperator.getText().toLowerCase()) {
                case "starts with" -> value + "%";
                case "ends with" -> "%" + value;
                case "contains" -> "%" + value + "%";
                default -> value;
            };

            if (!appliedFilterContainer.getChildren().isEmpty()) {
                addSeparator(null);
            }
            addFilterComponent(new FilterApplied(
                    selectedFilter.getFilterNameSelect(),
                    attributeLabel != null ? attributeLabel : selectedFilter.getAttributeClassName(),
                    selectedOperator.getText(),
                    selectedOperator.getSql(),
                    value,
                    this.attributeComboBox.getSelectionModel().getSelectedItem().getAttributeType()));
        }
    }

    private void addFilterComponent(FilterApplied filterApplied) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/component/FilterPaneComponentView.fxml"));
            Pane filterComponent = loader.load();

            FilterPaneComponentController componentController = loader.getController();
            componentController.setParentController(this);
            componentController.initialize(filterApplied);

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

    private void fillAppliedFilters() {
        if (currentFiltersApplied != null && !currentFiltersApplied.isEmpty()) {
            for (FilterApplied filterApplied : currentFiltersApplied) {
                if ("AND".equals(filterApplied.getQueryOperatorQuery()) || "OR".equals(filterApplied.getQueryOperatorQuery())) {
                    addSeparator(filterApplied.getQueryOperatorQuery());
                } else {
                    addFilterComponent(filterApplied);
                }
            }
        }
    }

    @FXML
    void applyFilters() {
        currentFiltersApplied.clear();

        for (Node node : appliedFilterContainer.getChildren()) {
            if (node instanceof Pane && node.getProperties().get("controller") instanceof FilterPaneComponentController componentController) {
                FilterApplied filterApplied = componentController.getFilterApplied();
                if (filterApplied != null) {
                    currentFiltersApplied.add(filterApplied);
                }
            } else if (node instanceof HBox && node.getProperties().get("controller") instanceof FilterPaneSeparatorController separatorController) {
                if (separatorController.getAndCheckBox().isSelected()) {
                    currentFiltersApplied.add(new FilterApplied("AND"));
                } else if (separatorController.getOrCheckBox().isSelected()) {
                    currentFiltersApplied.add(new FilterApplied("OR"));
                }
            }
        }

        for (FilterApplied filter : currentFiltersApplied) {
            System.out.println("Filter Applied: " + filter.getAttributeName() + " " + filter.getOperatorName() + " " + filter.getQueryOperatorQuery() + " " + filter.getValueQuery());
        }

        Stage stage = (Stage) attributeComboBox.getScene().getWindow();
        stage.close();
    }

    public List<FilterApplied> getCurrentFiltersApplied() {
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
