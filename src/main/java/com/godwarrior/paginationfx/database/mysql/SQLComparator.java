package com.godwarrior.paginationfx.database.mysql;

import com.godwarrior.paginationfx.models.Operator;

import java.util.ArrayList;
import java.util.List;

public class SQLComparator {

    public static List<Operator> getOperatorsForType(String type) {
        List<Operator> operators = new ArrayList<>();

        switch (type.toLowerCase()) {
            case "text":
                operators.add(new Operator("igual", "="));
                operators.add(new Operator("diferente", "<>"));
                operators.add(new Operator("like", "LIKE"));
                operators.add(new Operator("comienza con", "LIKE"));
                operators.add(new Operator("termina con", "LIKE"));
                operators.add(new Operator("contiene", "LIKE"));
                break;
            case "number":
                operators.add(new Operator("igual", "="));
                operators.add(new Operator("mayor", ">"));
                operators.add(new Operator("menor", "<"));
                operators.add(new Operator("mayor o igual", ">="));
                operators.add(new Operator("menor o igual", "<="));
                operators.add(new Operator("diferente", "<>"));
                operators.add(new Operator("in", "IN"));
                operators.add(new Operator("not in", "NOT IN"));
                break;
            case "date":
                operators.add(new Operator("igual", "="));
                operators.add(new Operator("mayor", ">"));
                operators.add(new Operator("menor", "<"));
                operators.add(new Operator("entre", "BETWEEN"));
                break;
            case "time":
                operators.add(new Operator("igual", "="));
                operators.add(new Operator("mayor", ">"));
                operators.add(new Operator("menor", "<"));
                break;
            case "bool":
                operators.add(new Operator("igual", "="));
                break;
            default:
                throw new IllegalArgumentException("Tipo de dato no válido para comparadores SQL: " + type);
        }

        return operators;
    }
}
