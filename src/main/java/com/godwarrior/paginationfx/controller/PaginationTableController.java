package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.database.mysql.MySQLSelect;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginationTableController<T> {

    private Class<T> objectType;
    private String dataBaseTable;
    private String queryBase;
    private String query;
    private int currentPage = 1;
    private final int itemsPerPage = 10;
    private int totalItems = 0;
    private int totalPages = 0;

    @FXML
    private ImageView backPageImgView, nextPageImgView;

    @FXML
    private ImageView filterImgView, resetFilterImgView;

    @FXML
    private TableView<T> filterTableView = new TableView<>();

    @FXML
    private Button goBackPageButton, goNextPageButton;

    @FXML
    private TextField numberPageTextField;

    @FXML
    private ComboBox<Integer> pageSelectComboBox;

    public void initialize(Class<T> objectType, String dataBaseTable) {
        this.objectType = objectType;
        this.dataBaseTable = dataBaseTable;

        queryBase = "SELECT * FROM " + this.dataBaseTable;

        totalItems = MySQLSelect.countRows("SELECT COUNT(*) FROM " + this.dataBaseTable);
        totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        pageSelectComboBox.getItems().addAll(IntStream.rangeClosed(1, totalPages).boxed().toList());
        pageSelectComboBox.setValue(currentPage);

        pageSelectComboBox.setOnAction(event -> {
            currentPage = pageSelectComboBox.getValue();
            updateQuery();
            loadPage();
            updateButtonStates();
        });


        updateQuery();
        loadPage();

        updateButtonStates();
    }

    private void updateQuery() {
        query = queryBase + " LIMIT " + itemsPerPage + " OFFSET " + (currentPage - 1) * itemsPerPage;
    }

    private void loadPage() {
        ObservableList<T> result = MySQLSelect.executeQuery(query, objectType);
        this.filterTableView.setItems(result);
        numberPageTextField.setText(String.valueOf(currentPage));
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updateQuery();
            loadPage();
            pageSelectComboBox.setValue(currentPage);
            updateButtonStates();
        }
    }
    @FXML
    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updateQuery();
            loadPage();
            pageSelectComboBox.setValue(currentPage);
            updateButtonStates();
        }
    }

    private void updateButtonStates() {
        goNextPageButton.setDisable(currentPage >= totalPages);
        goBackPageButton.setDisable(currentPage <= 1);
    }

    public String getQueryBase() {
        return queryBase;
    }

    public void setQueryBase(String query) {
        this.queryBase = query;
    }

    public void addColumn(String columnName, String attributeName) {
        TableColumn<T, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(cellData -> {
            try {
                Field field = cellData.getValue().getClass().getDeclaredField(attributeName);
                field.setAccessible(true);
                return new SimpleStringProperty(String.valueOf(field.get(cellData.getValue())));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return new SimpleStringProperty("Error");
            }
        });

        filterTableView.getColumns().add(column);
    }
}
