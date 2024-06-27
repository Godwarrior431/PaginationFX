package com.godwarrior.paginationfx.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMSQL {

    private static Connection connect;
    private static ConnectionMSQL instance;
    private final String ip;
    private final String database;
    private String user;
    private String password;

    private ConnectionMSQL(String ip, String database, String user, String password) {
        this.ip = ip;
        this.database = database;
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalArgumentException("Error al conectar a la base de datos", e);
        }
    }

    public static ConnectionMSQL getInstance(String ip, String database, String user, String password) {
        if (instance == null) {
            instance = new ConnectionMSQL(ip, database, user, password);
        }
        return instance;
    }

    public void  setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    public static Connection getConnect() {
        return connect;
    }

    public void closeConnect() {
        if (connect != null) {
            try {
                connect.close();
                instance = null;
            } catch (SQLException e) {
                throw new IllegalArgumentException("No se pudo cerrar la conexión a la base de datos" , e);
            }
        }
    }


}
