package com.godwarrior.paginationfx.models;

import com.godwarrior.paginationfx.database.mysql.SQLComparator;

import java.util.List;

public class Filter {

    private String attributeName;
    private String attributeType;
    private List<Operator> operators;

    public Filter(String attributeName, String attributeType) {
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.operators = SQLComparator.getOperatorsForType(attributeType);
    }

    public String getAttributeName() {
        return attributeName;
    }
    public String getAttributeType() {
        return attributeType;
    }


    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    @Override
    public String toString() {
        return attributeName;
    }

}
