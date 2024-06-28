package com.godwarrior.paginationfx.models;

import java.util.List;

public class FilterApplied {

    private String attributeName;
    private String operatorSelected;
    private String valueQuery;

    public FilterApplied(String operatorSelected, String attributeName, String valueQuery) {
        this.operatorSelected = operatorSelected;
        this.attributeName = attributeName;
        this.valueQuery = valueQuery;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getOperatorSelected() {
        return operatorSelected;
    }

    public void setOperatorSelected(String operatorSelected) {
        this.operatorSelected = operatorSelected;
    }

    public String getValueQuery() {
        return valueQuery;
    }

    public void setValueQuery(String valueQuery) {
        this.valueQuery = valueQuery;
    }
}
