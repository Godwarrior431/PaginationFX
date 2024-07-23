package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.database.mysql.MySQLSelect;
import com.godwarrior.paginationfx.models.ColumnPagTable;
import com.godwarrior.paginationfx.models.FilterPagApplied;
import com.godwarrior.paginationfx.models.FilterPagTable;
import com.godwarrior.paginationfx.utils.JavaUtilsFunctions;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PaginationTableController<T> {

    private Class<T> objectType;
    private String dataBaseTable;
    private String queryBase;
    private String query;
    private String queryDefault;
    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalItems = 0;
    private int totalPages = 0;

    private List<FilterPagTable> listFilterPagTables;
    private List<FilterPagApplied> appliedFilters = new ArrayList<>();

    private Stage stageAux;

    @FXML
    private ImageView backPageImgView, nextPageImgView, filterImgView, resetFilterImgView;

    @FXML
    private TableView<T> filterTableView;

    @FXML
    private Button goBackPageButton, goNextPageButton;

    @FXML
    private TextField numberPageTextField;

    @FXML
    private ComboBox<Integer> pageSelectComboBox;

    public void initialize(Class<T> objectType, String dataBaseTable) {
        this.objectType = objectType;
        this.dataBaseTable = dataBaseTable;

        initializeImageViews();
        initializeTableView();

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

        filterTableView.getItems().addListener((ListChangeListener<T>) change -> updateEmptyState());

        filterTableView.setStyle(
                "-fx-alignment: CENTER; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Arial';" // Ajusta según tus preferencias
        );

        updateQuery();
        loadPage();
        updateButtonStates();
        adjustFontSizeDynamically();
    }

    private void initializeImageViews() {
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/filterIcon.png", filterImgView);
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/resetForms.png", resetFilterImgView);
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/backIcon.png", backPageImgView);
        JavaUtilsFunctions.setImage("/com/godwarrior/paginationfx/resources/icons/nextIcon.png", nextPageImgView);
    }

    private void initializeTableView() {
        filterTableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.prefHeightProperty().bind(filterTableView.heightProperty().divide(itemsPerPage + 0.60));
            return row;
        });
    }

    private void adjustFontSizeDynamically() {
        Scene scene = filterTableView.getScene();
        if (scene != null) {
            bindFontSize(scene);
        } else {
            filterTableView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    bindFontSize(newScene);
                }
            });
        }
    }

    private void bindFontSize(Scene scene) {
        scene.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            double fontSize = newHeight.doubleValue() * 0.02;
            filterTableView.setStyle("-fx-font-size: " + fontSize + "px;");
        });
    }

    public void addFilters(List<FilterPagTable> listFilterPagTables) {
        this.listFilterPagTables = listFilterPagTables;
    }

    private void updateQuery() {
        query = queryBase + " LIMIT " + itemsPerPage + " OFFSET " + (currentPage - 1) * itemsPerPage;
    }

    private void loadPage() {
        ObservableList<T> result = MySQLSelect.executeQuery(query, objectType);
        this.filterTableView.setItems(result);
        numberPageTextField.setText(String.valueOf(currentPage));
        updateEmptyState();
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
        updateButtonStates();
        appliedFilters.clear();
    }

    private void updateButtonStates() {
        boolean isEmpty = filterTableView.getItems().isEmpty();
        goNextPageButton.setDisable(currentPage >= totalPages || isEmpty);
        goBackPageButton.setDisable(currentPage <= 1 || isEmpty);
    }

    private void updateEmptyState() {
        boolean isEmpty = filterTableView.getItems().isEmpty();
        goBackPageButton.setDisable(isEmpty || currentPage <= 1);
        goNextPageButton.setDisable(isEmpty || currentPage >= totalPages);
        numberPageTextField.setDisable(isEmpty);
        pageSelectComboBox.setDisable(isEmpty);
    }

    public void addColumns(List<ColumnPagTable> columns) {
        for (ColumnPagTable column : columns) {
            TableColumn<T, String> tableColumn = new TableColumn<>(column.getColumnName());
            tableColumn.setCellValueFactory(cellData -> {
                try {
                    Field field = cellData.getValue().getClass().getDeclaredField(column.getAttributeName());
                    field.setAccessible(true);
                    return new SimpleStringProperty(String.valueOf(field.get(cellData.getValue())));
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                    return new SimpleStringProperty("Error");
                }
            });

            filterTableView.getColumns().add(tableColumn);
        }
    }

    @FXML
    public void showFilters() throws IOException {
        if (stageAux != null && stageAux.isShowing()) {
            stageAux.toFront();
        } else {
            stageAux = new Stage();
            stageAux.resizableProperty().setValue(Boolean.FALSE);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/FilterPaneView.fxml"));
            Region root = loader.load();
            Scene scene = new Scene(root);

            stageAux.setTitle("Filters");
            stageAux.setScene(scene);
            stageAux.resizableProperty().setValue(Boolean.FALSE);

            stageAux.initModality(Modality.APPLICATION_MODAL);

            FilterPaneController filterPaneController = loader.getController();
            filterPaneController.initialize(this.listFilterPagTables, appliedFilters);

            stageAux.showAndWait();
            appliedFilters = filterPaneController.getCurrentFiltersApplied();

            queryBase = buildQueryWithFilters();

            totalItems = MySQLSelect.countRows("SELECT COUNT(*) FROM (" + queryBase + ") AS countQuery");
            totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            currentPage = 1;

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
        String previousLogicalOperator = "";

        for (FilterPagApplied filter : appliedFilters) {
            if (isValidFilter(filter)) {
                if (firstCondition) {
                    queryBuilder.append(" WHERE ");
                } else {
                    queryBuilder.append(" ").append(previousLogicalOperator).append(" ");
                }

                queryBuilder.append(filter.getAttributeName())
                        .append(" ").append(filter.getQueryOperatorQuery())
                        .append(" ").append(filter.getFormattedValue());

                firstCondition = false;
                previousLogicalOperator = "AND";
            } else if (isLogicalOperator(filter)) {
                previousLogicalOperator = filter.getQueryOperatorQuery().toUpperCase().trim();
            }
        }

        trimEndingLogicalOperators(queryBuilder);

        return queryBuilder.toString().trim();
    }

    private boolean isValidFilter(FilterPagApplied filter) {
        return filter.getAttributeName() != null && !filter.getAttributeName().isEmpty() &&
                filter.getQueryOperatorQuery() != null && !filter.getQueryOperatorQuery().isEmpty() &&
                filter.getFormattedValue() != null && !filter.getFormattedValue().isEmpty();
    }

    private boolean isLogicalOperator(FilterPagApplied filter) {
        return filter.getQueryOperatorQuery() != null && !filter.getQueryOperatorQuery().isEmpty();
    }

    private void trimEndingLogicalOperators(StringBuilder queryBuilder) {
        if (queryBuilder.toString().trim().endsWith("AND") || queryBuilder.toString().trim().endsWith("OR")) {
            queryBuilder.setLength(queryBuilder.length() - 3);
        }
    }

}
