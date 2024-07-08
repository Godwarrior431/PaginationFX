package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.database.mysql.MySQLSelect;
import com.godwarrior.paginationfx.models.Filter;
import com.godwarrior.paginationfx.models.FilterApplied;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class PaginationTableController<T> {

    private Class<T> objectType;
    private String dataBaseTable;
    private String queryBase;
    private String query;
    private String queryDefault;
    private int currentPage = 1;
    private final int itemsPerPage = 10;
    private int totalItems = 0;
    private int totalPages = 0;

    private List<Filter> listFilters;
    private List<FilterApplied> appliedFilters = new ArrayList<>();

    private Stage stageAux;

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

        filterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/filterIcon.png"))));
        resetFilterImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/resetForms.png"))));
        backPageImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/backIcon.png"))));
        nextPageImgView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/godwarrior/paginationfx/resources/icons/nextIcon.png"))));

        queryBase = "SELECT * FROM " + this.dataBaseTable;
        queryDefault = queryBase;


        totalItems = MySQLSelect.countRows("SELECT COUNT(*) FROM " + this.dataBaseTable);
        totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        pageSelectComboBox.getItems().addAll(IntStream.rangeClosed(1, totalPages).boxed().toList());
        pageSelectComboBox.setValue(currentPage);

        pageSelectComboBox.setOnAction(event -> {
            Integer selectedPage = pageSelectComboBox.getValue();
            if (selectedPage != null) {
                currentPage = selectedPage;
                updateQuery();
                loadPage();
                updateButtonStates();
            }
        });

        updateQuery();
        loadPage();

        updateButtonStates();
    }

    public void addFilters(ArrayList<Filter> listFilters) {
        this.listFilters = listFilters;
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

    @FXML
    private void resetFilter() {
        queryBase = queryDefault;
        currentPage = 1;

        totalItems = MySQLSelect.countRows("SELECT COUNT(*) FROM " + this.dataBaseTable);
        totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        pageSelectComboBox.getItems().clear();
        pageSelectComboBox.getItems().addAll(IntStream.rangeClosed(1, totalPages).boxed().toList());
        pageSelectComboBox.setValue(currentPage);
        updateQuery();
        loadPage();
        pageSelectComboBox.setValue(currentPage);
        updateButtonStates();
        appliedFilters.clear();
    }

    private void updateButtonStates() {
        goNextPageButton.setDisable(currentPage >= totalPages);
        goBackPageButton.setDisable(currentPage <= 1);
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

    @FXML
    public void showFilters() throws IOException {
        if (stageAux != null && stageAux.isShowing())
            stageAux.toFront();
        else {
            stageAux = new Stage();
            stageAux.resizableProperty().setValue(Boolean.FALSE);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/FilterPaneView.fxml"));
            Region root = (Region) loader.load();
            Scene scene = new Scene(root);

            stageAux.setTitle("Filters");
            stageAux.setScene(scene);
            stageAux.resizableProperty().setValue(Boolean.FALSE);

            stageAux.initModality(Modality.APPLICATION_MODAL);

            FilterPaneController FilterPane = loader.<FilterPaneController>getController();
            FilterPane.initialize(this.listFilters, appliedFilters);
            stageAux.showAndWait();
            appliedFilters = FilterPane.getCurrentFiltersApplied();

            queryBase = buildQueryWithFilters();

            System.out.println(queryBase);

            totalItems = MySQLSelect.countRows("SELECT COUNT(*) FROM (" + queryBase + ") AS countQuery");
            totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            pageSelectComboBox.getItems().clear();
            pageSelectComboBox.getItems().addAll(IntStream.rangeClosed(1, totalPages).boxed().toList());
            pageSelectComboBox.setValue(currentPage);

            updateQuery();
            loadPage();
            updateButtonStates();

        }
    }


    private String buildQueryWithFilters() {
        StringBuilder queryBuilder = new StringBuilder(queryDefault);
        boolean firstCondition = true;
        boolean whereAdded = false;
        String previousLogicalOperator = "";

        for (FilterApplied filter : appliedFilters) {
            if (filter.getAttributeName() != null && !filter.getAttributeName().isEmpty() &&
                    filter.getQueryOperatorQuery() != null && !filter.getQueryOperatorQuery().isEmpty() &&
                    filter.getFormattedValue() != null && !filter.getFormattedValue().isEmpty()) {

                if (firstCondition && !whereAdded) {
                    queryBuilder.append(" WHERE ");
                    whereAdded = true;
                } else if (!firstCondition) {
                    queryBuilder.append(" ").append(previousLogicalOperator).append(" ");
                }

                queryBuilder.append(filter.getAttributeName())
                        .append(" ").append(filter.getQueryOperatorQuery())
                        .append(" ").append(filter.getFormattedValue());

                firstCondition = false;
                previousLogicalOperator = "AND"; // Default logical operator is "AND"
            } else if (filter.getQueryOperatorQuery() != null && !filter.getQueryOperatorQuery().isEmpty()) {
                // If only the logical operator is provided
                previousLogicalOperator = filter.getQueryOperatorQuery().toUpperCase().trim();
            }
        }

        // Removing trailing logical operators if present
        String query = queryBuilder.toString().trim();
        if (query.endsWith("AND") || query.endsWith("OR")) {
            query = query.substring(0, query.length() - 3).trim();
        }

        return query;
    }


}

