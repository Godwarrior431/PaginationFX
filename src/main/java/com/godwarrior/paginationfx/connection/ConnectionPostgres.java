package com.godwarrior.paginationfx.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgres {

    private static Connection connect;
    private static ConnectionPostgres instance;
    private final String ip;
    private final String database;
    private String user;
    private String password;

    // Constructor privado con credenciales
    private ConnectionPostgres(String ip, String database, String user, String password) {
        this.ip = ip;
        this.database = database;
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    // Método para conectarse a la base de datos
    private void connectToDatabase() {
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://" + ip + "/" + database, user, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al conectar a la base de datos", e);
        }
    }

    // Método estático para obtener la instancia única
    public static ConnectionPostgres getInstance(String ip, String database, String user, String password) {
        if (instance == null) {
            instance = new ConnectionPostgres(ip, database, user, password);
        }
        return instance;
    }

    // Método para establecer credenciales y actualizar la conexión
    public void setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    // Método para obtener la conexión actual
    public static Connection getConnect() {
        return connect;
    }

    // Método para cerrar la conexión
    public static void closeConnect() {
        if (connect != null) {
            try {
                connect.close();
                instance = null; // Invalida la instancia única
            } catch (SQLException e) {
                throw new IllegalArgumentException("No se pudo cerrar la conexión a la base de datos", e);
            }
        }
    }
}
