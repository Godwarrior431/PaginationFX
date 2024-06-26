package com.godwarrior.paginationfx.models;

import java.util.HashMap;
import java.util.Map;

public class Comparators {
    private static final Map<String, String> comparatorsMap = new HashMap<>();

    static {
        comparatorsMap.put("igual", "=");
        comparatorsMap.put("mayor", ">");
        comparatorsMap.put("menor", "<");
        comparatorsMap.put("mayor o igual", ">=");
        comparatorsMap.put("menor o igual", "<=");
        comparatorsMap.put("diferente", "<>");
        comparatorsMap.put("like", "LIKE");
        comparatorsMap.put("entre", "BETWEEN");
        comparatorsMap.put("is null", "IS NULL");
        comparatorsMap.put("is not null", "IS NOT NULL");
        comparatorsMap.put("in", "IN");
        comparatorsMap.put("not in", "NOT IN");
        comparatorsMap.put("exists", "EXISTS");
        comparatorsMap.put("not exists", "NOT EXISTS");
        comparatorsMap.put("comienza con", "LIKE");  // Ejemplo: LIKE 'abc%'
        comparatorsMap.put("termina con", "LIKE");   // Ejemplo: LIKE '%abc'
        comparatorsMap.put("contiene", "LIKE");     // Ejemplo: LIKE '%abc%'

        // Compatibilidad adicional con otros motores de bases de datos
        comparatorsMap.put("ilike", "ILIKE");         // PostgreSQL: Case-insensitive LIKE
        comparatorsMap.put("similar to", "SIMILAR TO");  // PostgreSQL: Pattern matching
        comparatorsMap.put("regexp", "REGEXP");       // MySQL/SQLite: Regular expression matching
        comparatorsMap.put("not regexp", "NOT REGEXP");  // MySQL/SQLite: Negation of regular expression matching
        comparatorsMap.put("match", "MATCH");         // Full-text search (SQLite, MySQL)
        comparatorsMap.put("fulltext", "FULLTEXT");   // Full-text search (MySQL)
        comparatorsMap.put("contains", "CONTAINS");   // Full-text search (SQL Server)
        comparatorsMap.put("soundex", "SOUNDEX");     // Phonetic search (MySQL, SQL Server)

        // Operadores específicos de Oracle
        comparatorsMap.put("regexp_like", "REGEXP_LIKE"); // Oracle: Regular expression matching
        comparatorsMap.put("not regexp_like", "NOT REGEXP_LIKE"); // Oracle: Negation of regular expression matching
        // Agrega más comparadores según sea necesario
    }

    public static String getComparator(String key) {
        return comparatorsMap.get(key);
    }
}
