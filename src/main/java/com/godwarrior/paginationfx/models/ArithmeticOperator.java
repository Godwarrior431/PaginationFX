package com.godwarrior.paginationfx.models;

public record ArithmeticOperator(String operatorText, String operatorSql) {

    @Override
    public String toString() {
        return operatorText;
    }
}
