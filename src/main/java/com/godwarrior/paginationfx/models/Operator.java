package com.godwarrior.paginationfx.models;

public class Operator {
    private String text;
    private String sql;

    public Operator(String text, String sql) {
        this.text = text;
        this.sql = sql;
    }

    public String getText() {
        return text;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public String toString() {
        return text;
    }
}
