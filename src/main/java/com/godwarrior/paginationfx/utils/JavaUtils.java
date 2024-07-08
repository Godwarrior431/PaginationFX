package com.godwarrior.paginationfx.utils;

import com.godwarrior.paginationfx.annotation.ColumnName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
import java.util.Objects;

public class JavaUtils {

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

    public static void setImage(String path, ImageView imageView) {
        try {
            imageView.setImage(new Image(Objects.requireNonNull(JavaUtils.class.getResourceAsStream(path))));
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + path);
            // Optionally, you can set a default image or handle the error as needed
        }
    }
}