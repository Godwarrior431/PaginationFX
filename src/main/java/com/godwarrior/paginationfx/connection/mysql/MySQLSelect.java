package com.godwarrior.paginationfx.connection.mysql;

import com.godwarrior.paginationfx.annotation.ColumnName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.Field;


public class  MySQLSelect {

    public static <T> ObservableList<T> executeQuery(String query, Class<T> clazz) {
        ObservableList<T> resultList = FXCollections.observableArrayList();

        try (Connection connection = ConnectionMSQL.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    // Check for Column annotation
                    ColumnName columnAnnotation = field.getAnnotation(ColumnName.class);
                    if (columnAnnotation != null) {
                        String columnName = columnAnnotation.name();
                        Object value = resultSet.getObject(columnName);
                        field.set(obj, value);
                    } else {
                        // Fallback to field name if no annotation is present
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);
                        field.set(obj, value);
                    }
                }

                resultList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
