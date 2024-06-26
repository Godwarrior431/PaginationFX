package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.connection.mysql.ConnectionMSQL;
import com.godwarrior.paginationfx.connection.mysql.MySQLSelect;
import com.godwarrior.paginationfx.models.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;

public class PaginationTableController<T> {

    private T object;
    private String dataBaseName;

    private String query;

    @FXML
    private ImageView backPageImgView , nextPageImgView;

    @FXML
    private ImageView filterImgView , resetFilterImgView;

    @FXML
    private TableView<T> filterTableView = new TableView<>();

    @FXML
    private Button goBackPageButton, goNextPageButton;

    @FXML
    private TextField numberPageTextField;

    @FXML
    private ComboBox<Integer> pageSelectComboBox;

    public void initialize(T object, String dataBaseName) {
        this.object = object;
        this.dataBaseName = dataBaseName;

        ConnectionMSQL.getInstance("localhost", "paginationtest", "root", "");

        query = "SELECT * FROM usuario";

        this.addColumn("Identificador" , "id");
        this.addColumn("Nombre", "name");
        this.addColumn("Apellido" ,"telefono");

        // Set the items in the TableView
        this.filterTableView.setItems((ObservableList<T>) MySQLSelect.executeQuery(query, Usuario.class));
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
                Field field = cellData.getValue().getClass().getDeclaredField(attributeName);
                field.setAccessible(true);  // Make the field accessible
                return new SimpleStringProperty(String.valueOf(field.get(cellData.getValue())));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return new SimpleStringProperty("Error");
            }
        });

        filterTableView.getColumns().add(column);
    }

}
