package com.godwarrior.paginationfx.database.mysql;

import com.godwarrior.paginationfx.utils.JavaUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLSelect {

    public static int countRows(String query) {
        int rowCount = 0;

        try {
            Connection connection = ConnectionMSQL.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowCount;
    }


    public static <T> ObservableList<T> executeQuery(String query, Class<T> clazz) {
        ObservableList<T> resultList = FXCollections.observableArrayList();

        try {
            Connection connection = ConnectionMSQL.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    String columnName = JavaUtils.getColumnLabel(clazz, field.getName());
                    if (columnName == null) {
                        columnName = field.getName();
                    }

                    Object value = resultSet.getObject(columnName);
                    field.set(obj, value);
                }

                resultList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
