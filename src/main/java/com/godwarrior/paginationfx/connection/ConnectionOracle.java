package com.godwarrior.paginationfx.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOracle {

    private static Connection connect;
    private static ConnectionOracle instance;
    private String ip;
    private String database;
    private String user;
    private String password;

    private ConnectionOracle(String ip, String database, String user, String password) {
        this.ip = ip;
        this.database = database;
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            connect = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:" + database, user, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al conectar a la base de datos", e);
        }
    }

    public static ConnectionOracle getInstance(String ip, String database, String user, String password) {
        if (instance == null) {
            instance = new ConnectionOracle(ip, database, user, password);
        }
        return instance;
    }

    public void setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    public static Connection getConnect() {
        return connect;
    }

    public static void closeConnect() {
        if (connect != null) {
            try {
                connect.close();
                instance = null;
            } catch (SQLException e) {
                throw new IllegalArgumentException("No se pudo cerrar la conexión a la base de datos", e);
            }
        }
    }
}