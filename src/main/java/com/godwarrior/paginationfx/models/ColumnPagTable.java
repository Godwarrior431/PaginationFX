package com.godwarrior.paginationfx.models;

public class ColumnPagTable {
    private String columnName;
    private String attributeName;

    public ColumnPagTable(String columnName, String attributeName) {
        this.columnName = columnName;
        this.attributeName = attributeName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
