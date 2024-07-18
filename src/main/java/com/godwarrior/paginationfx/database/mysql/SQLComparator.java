package com.godwarrior.paginationfx.database.mysql;

import com.godwarrior.paginationfx.models.ArithmeticOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for SQL comparators.
 * This class provides static methods to get SQL operators for different data types.
 * It should not be instantiated.
 */
public final class SQLComparator {

    private SQLComparator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Returns a list of SQL operators for the given data type.
     *
     * @param type the data type (e.g., "text", "number", "date", "time", "bool")
     * @return a list of SQL operators for the specified data type
     */
    public static List<ArithmeticOperator> getOperatorsForType(String type) {
        List<ArithmeticOperator> arithmeticOperators = new ArrayList<>();

        switch (type.toLowerCase()) {
            case "text":
                arithmeticOperators.add(new ArithmeticOperator("like", "LIKE"));
                arithmeticOperators.add(new ArithmeticOperator("not equals", "<>"));
                arithmeticOperators.add(new ArithmeticOperator("starts with", "LIKE"));
                arithmeticOperators.add(new ArithmeticOperator("ends with", "LIKE"));
                arithmeticOperators.add(new ArithmeticOperator("contains", "LIKE"));
                break;
            case "number":
                arithmeticOperators.add(new ArithmeticOperator("equals", "="));
                arithmeticOperators.add(new ArithmeticOperator("greater than", ">"));
                arithmeticOperators.add(new ArithmeticOperator("less than", "<"));
                arithmeticOperators.add(new ArithmeticOperator("greater than or equals", ">="));
                arithmeticOperators.add(new ArithmeticOperator("less than or equals", "<="));
                arithmeticOperators.add(new ArithmeticOperator("not equals", "<>"));
                break;
            case "date":
                arithmeticOperators.add(new ArithmeticOperator("equals", "="));
                arithmeticOperators.add(new ArithmeticOperator("is after", ">"));
                arithmeticOperators.add(new ArithmeticOperator("is before", "<"));
                arithmeticOperators.add(new ArithmeticOperator("is on or after", ">="));
                arithmeticOperators.add(new ArithmeticOperator("is on or before", "<="));
                break;
            case "time":
                arithmeticOperators.add(new ArithmeticOperator("equals", "="));
                arithmeticOperators.add(new ArithmeticOperator("is after", "> TIME"));
                arithmeticOperators.add(new ArithmeticOperator("is before", "< TIME"));
                break;
            case "bool":
                arithmeticOperators.add(new ArithmeticOperator("is", "="));
                break;
            default:
                throw new IllegalArgumentException("Invalid data type for SQL comparators: " + type);
        }

        return arithmeticOperators;
    }
}
