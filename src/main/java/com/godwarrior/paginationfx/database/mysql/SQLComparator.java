package com.godwarrior.paginationfx.database.mysql;

import com.godwarrior.paginationfx.models.Operator;

import java.util.ArrayList;
import java.util.List;

public class SQLComparator {

    public static List<Operator> getOperatorsForType(String type) {
        List<Operator> operators = new ArrayList<>();

        switch (type.toLowerCase()) {
            case "text":
                operators.add(new Operator("like", "LIKE"));
                operators.add(new Operator("not equals", "<>"));
                operators.add(new Operator("starts with", "LIKE"));
                operators.add(new Operator("ends with", "LIKE"));
                operators.add(new Operator("contains", "LIKE"));
                break;
            case "number":
                operators.add(new Operator("equals", "="));
                operators.add(new Operator("greater than", ">"));
                operators.add(new Operator("less than", "<"));
                operators.add(new Operator("greater than or equals", ">="));
                operators.add(new Operator("less than or equals", "<="));
                operators.add(new Operator("not equals", "<>"));
                operators.add(new Operator("in", "IN"));
                operators.add(new Operator("not in", "NOT IN"));
                break;
            case "date":
                operators.add(new Operator("equals", "="));
                operators.add(new Operator("greater than", ">"));
                operators.add(new Operator("less than", "<"));
                operators.add(new Operator("between", "BETWEEN"));
                break;
            case "time":
                operators.add(new Operator("equals", "="));
                operators.add(new Operator("greater than", ">"));
                operators.add(new Operator("less than", "<"));
                break;
            case "bool":
                operators.add(new Operator("equals", "="));
                break;
            default:
                throw new IllegalArgumentException("Invalid data type for SQL comparators: " + type);
        }

        return operators;
    }
}
