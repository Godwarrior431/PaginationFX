package com.godwarrior.paginationfx.utils;

import com.godwarrior.paginationfx.controller.PaginationTableController;
import com.godwarrior.paginationfx.models.ColumnPagTable;
import com.godwarrior.paginationfx.models.Filter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.util.List;

public class TableViewPaginated<T> {
    private PaginationTableController<T> controller;
    private Node paginationTable;

    public TableViewPaginated(Class<T> objectType, String dataBaseTable) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/PaginationTableView.fxml"));
            this.paginationTable = loader.load();
            this.controller = loader.getController();
            this.controller.initialize(objectType, dataBaseTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColumns(List<ColumnPagTable> columns) {
        controller.addColumns(columns);
    }

    public void addFilters(List<Filter> filters) {
        controller.addFilters(filters);
    }

    public Node getPaginationTable() {
        return this.paginationTable;
    }
}
