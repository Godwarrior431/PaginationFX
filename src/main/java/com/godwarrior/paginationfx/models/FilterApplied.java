package com.godwarrior.paginationfx.models;

public class FilterApplied {

    private String attributeName;
    private String operatorName;
    private String queryOperatorQuery;
    private String valueQuery;

    public FilterApplied(String attributeName, String operatorName, String queryOperatorQuery, String valueQuery) {
        this.queryOperatorQuery = queryOperatorQuery;
        this.operatorName = operatorName;
        this.attributeName = attributeName;
        this.valueQuery = valueQuery;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getValueQuery() {
        return valueQuery;
    }

    public void setValueQuery(String valueQuery) {
        this.valueQuery = valueQuery;
    }

    @Override
    public String toString() {
        return "FilterApplied{" +
                "attributeName='" + attributeName + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", queryOperatorQuery='" + queryOperatorQuery + '\'' +
                ", valueQuery='" + valueQuery + '\'' +
                '}';
    }

    public String getQueryOperatorQuery() {
        return queryOperatorQuery;
    }

    public void setQueryOperatorQuery(String queryOperatorQuery) {
        this.queryOperatorQuery = queryOperatorQuery;
    }
}
