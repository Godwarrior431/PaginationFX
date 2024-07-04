package com.godwarrior.paginationfx.utils;

import com.godwarrior.paginationfx.annotation.ColumnName;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static String getColumnLabel(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(ColumnName.class)) {
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                return columnName.name();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}