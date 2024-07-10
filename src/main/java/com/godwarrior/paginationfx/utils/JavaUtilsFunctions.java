package com.godwarrior.paginationfx.utils;

import com.godwarrior.paginationfx.annotation.ColumnName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Utility class for common Java operations.
 * Provides static methods for retrieving column labels from annotated fields
 * and setting images in JavaFX ImageViews.
 * This class should not be instantiated.
 */

public final class JavaUtilsFunctions {

    private static final Logger logger = Logger.getLogger(JavaUtilsFunctions.class.getName());

    private JavaUtilsFunctions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Returns the column label for a given field in a class.
     * The field must have a @ColumnName annotation.
     *
     * @param clazz     the class containing the field
     * @param fieldName the name of the field
     * @return the column label from the @ColumnName annotation, or null if the field is not found or not annotated
     */
    public static String getColumnLabel(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(ColumnName.class)) {
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                return columnName.name();
            }
        } catch (NoSuchFieldException e) {
            logger.warning("Field not found: " + fieldName);
        }
        return null;
    }

    /**
     * Sets the image of an ImageView to the image located at the specified path.
     *
     * @param path      the path to the image resource
     * @param imageView the ImageView to set the image on
     */
    public static void setImage(String path, ImageView imageView) {
        try {
            imageView.setImage(new Image(Objects.requireNonNull(JavaUtilsFunctions.class.getResourceAsStream(path))));
        } catch (NullPointerException e) {
            logger.info("Image not found: " + path);
        }
    }
}
