package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.database.mysql.ConnectionMSQL;
import com.godwarrior.paginationfx.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private VBox vboxConteiner;

    @FXML
    public void initialize() {
        try {
            // Cargar el archivo FXML de PaginationTable
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/view/PaginationTableView.fxml"));
            Node paginationTable = loader.load();

            ConnectionMSQL.getInstance("localhost", "paginationtest", "root", "");

            PaginationTableController newTable = loader.getController();
            newTable.initialize(Usuario.class, "usuario");

            newTable.addColumn("Identificador", "id");
            newTable.addColumn("Nombre", "name");
            newTable.addColumn("Apellido", "telefono");

            // Añadir el nodo cargado al VBox
            vboxConteiner.getChildren().add(paginationTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}