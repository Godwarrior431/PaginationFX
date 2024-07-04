package com.godwarrior.paginationfx.models;

import com.godwarrior.paginationfx.database.mysql.SQLComparator;

import java.util.List;

public class Filter {

    private String attributeClassName;
    private String attributeType;
    private String filterNameSelect;
    private List<Operator> operators;

    public Filter(String filterNameSelect, String attributeName, String attributeType) {
        this.filterNameSelect = filterNameSelect;
        this.attributeClassName = attributeName;
        this.attributeType = attributeType;
        this.operators = SQLComparator.getOperatorsForType(attributeType);
    }

    public String getAttributeClassName() {
        return attributeClassName;
    }

    public String getAttributeType() {
        return attributeType;
    }


    public void setAttributeClassName(String attributeClassName) {
        this.attributeClassName = attributeClassName;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    @Override
    public String toString() {
        return filterNameSelect;
    }

    public String getFilterNameSelect() {
        return filterNameSelect;
    }

    public void setFilterNameSelect(String filterNameSelect) {
        this.filterNameSelect = filterNameSelect;
    }
}
