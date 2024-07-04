package com.godwarrior.paginationfx.models;

public class FilterApplied {

    private String filterNameSelect;
    private String attributeName;
    private String operatorName;
    private String queryOperatorQuery;
    private String valueQuery;
    private String typeFilter;

    public FilterApplied(String filterNameSelect, String attributeName, String operatorName, String queryOperatorQuery, String valueQuery, String typeFilter) {
        this.filterNameSelect = filterNameSelect;
        this.queryOperatorQuery = queryOperatorQuery;
        this.operatorName = operatorName;
        this.attributeName = attributeName;
        this.valueQuery = valueQuery;
        this.typeFilter = typeFilter;
    }

    public FilterApplied(String queryOperatorQuery) {
        this.queryOperatorQuery = queryOperatorQuery;
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

    public String getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }

    public String getFormattedValue() {
        switch (typeFilter) {
            case "text":
            case "date":
            case "time":
                return "'" + valueQuery + "'";
            case "number":
                return valueQuery;
            case "bool":
                // Convertir el valor booleano a 1 o 0
                if (valueQuery != null && (valueQuery.equalsIgnoreCase("true") || valueQuery.equals("1"))) {
                    return "1";
                } else {
                    return "0";
                }
            default:
                throw new IllegalArgumentException("Tipo de filtro desconocido: " + typeFilter);
        }
    }

    public String getFilterNameSelect() {
        return filterNameSelect;
    }

    public void setFilterNameSelect(String filterNameSelect) {
        this.filterNameSelect = filterNameSelect;
    }
}
