package com.godwarrior.paginationfx.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;

public class FilterTableViewController<T> {

    private final T object;
    private final String dataBaseName;

    private String query;

    @FXML
    private ImageView backPageImgView , nextPageImgView;

    @FXML
    private ImageView filterImgView , resetFilterImgView;

    @FXML
    private TableView<T> filterTableView;

    @FXML
    private Button goBackPageButton, goNextPageButton;

    @FXML
    private TextField numberPageTextField;

    @FXML
    private ComboBox<Integer> pageSelectComboBox;

    public FilterTableViewController(T object, String dataBaseName) {
        this.object = object;
        this.dataBaseName = dataBaseName;

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void addColumn(String columnName, String attributeName) {
        TableColumn<T, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getClass().getDeclaredField(attributeName).get(cellData.getValue())));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return new SimpleStringProperty("Error");
            }
        });

        filterTableView.getColumns().add(column);
    }

}
