package com.godwarrior.paginationfx.models;

public class FilterPagApplied {

    private String filterNameSelect;
    private String attributeName;
    private String operatorName;
    private final String queryOperatorQuery;
    private String valueQuery;
    private String typeFilter;

    public FilterPagApplied(String filterNameSelect, String attributeName, String operatorName, String queryOperatorQuery, String valueQuery, String typeFilter) {
        this.filterNameSelect = filterNameSelect;
        this.queryOperatorQuery = queryOperatorQuery;
        this.operatorName = operatorName;
        this.attributeName = attributeName;
        this.valueQuery = valueQuery;
        this.typeFilter = typeFilter;
    }

    public FilterPagApplied(String queryOperatorQuery) {
        this.queryOperatorQuery = queryOperatorQuery;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getOperatorName() {
        return operatorName;
    }


    public String getValueQuery() {
        return valueQuery;
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

    public String getFormattedValue() {
        switch (typeFilter) {
            case "date", "text":
            case "time":
                return "'" + valueQuery + "'";
            case "number":
                return valueQuery;
            case "bool":
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

}
