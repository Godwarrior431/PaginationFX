package com.godwarrior.paginationfx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLite {

    private Connection connect;
    private static ConnectionSQLite instance;
    private final String dbPath;

    // Constructor privado con credenciales
    private ConnectionSQLite(String dbPath) {
        this.dbPath = dbPath;
        connectToDatabase();
    }

    // Método para conectarse a la base de datos
    private void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connect = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalArgumentException("Error al conectar a la base de datos", e);
        }
    }

    // Método estático para obtener la instancia única
    public static ConnectionSQLite getInstance(String dbPath) {
        if (instance == null) {
            instance = new ConnectionSQLite(dbPath);
        }
        return instance;
    }

    // Método para obtener la conexión actual
    public Connection getConnect() {
        return connect;
    }

    // Método para cerrar la conexión
    public void closeConnect() {
        if (connect != null) {
            try {
                connect.close();
                instance = null; // Invalida la instancia única
            } catch (SQLException e) {
                throw new IllegalArgumentException("No se pudo cerrar la conexión a la base de datos" , e);
            }
        }
    }
}
