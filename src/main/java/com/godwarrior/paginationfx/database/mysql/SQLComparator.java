package com.godwarrior.paginationfx.database.mysql;

import com.godwarrior.paginationfx.models.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for SQL comparators.
 * This class provides static methods to get SQL operators for different data types.
 * It should not be instantiated.
 */
public final class SQLComparator {

    // Private constructor to prevent instantiation
    private SQLComparator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Returns a list of SQL operators for the given data type.
     *
     * @param type the data type (e.g., "text", "number", "date", "time", "bool")
     * @return a list of SQL operators for the specified data type
     */
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
                break;
            case "date":
                operators.add(new Operator("equals", "="));
                operators.add(new Operator("is after", ">"));
                operators.add(new Operator("is before", "<"));
                operators.add(new Operator("is on or after", ">="));
                operators.add(new Operator("is on or before", "<="));
                break;
            case "time":
                operators.add(new Operator("equals", "="));
                operators.add(new Operator("is after", "> TIME"));
                operators.add(new Operator("is before", "< TIME"));
                break;
            case "bool":
                operators.add(new Operator("is", "="));
                break;
            default:
                throw new IllegalArgumentException("Invalid data type for SQL comparators: " + type);
        }

        return operators;
    }
}
