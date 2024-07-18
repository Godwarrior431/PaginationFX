package com.godwarrior.paginationfx.models;

import com.godwarrior.paginationfx.database.mysql.SQLComparator;

import java.util.List;

public class Filter {
    private String filterNameSelect;
    private String attributeObjectName;
    private final String attributeType;
    private final List<ArithmeticOperator> arithmeticOperators;

    public Filter(String filterNameSelect, String attributeObjectName, String attributeType) {
        this.filterNameSelect = filterNameSelect;
        this.attributeObjectName = attributeObjectName;
        this.attributeType = attributeType;
        this.arithmeticOperators = SQLComparator.getOperatorsForType(attributeType);
    }

    public String getAttributeObjectName() {
        return attributeObjectName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public List<ArithmeticOperator> getOperators() {
        return arithmeticOperators;
    }

    @Override
    public String toString() {
        return filterNameSelect;
    }

    public String getFilterNameSelect() {
        return filterNameSelect;
    }

}
