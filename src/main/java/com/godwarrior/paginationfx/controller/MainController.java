package com.godwarrior.paginationfx.controller;

import com.godwarrior.paginationfx.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

            PaginationTableController newWindow = loader.getController();
            newWindow.initialize(new Usuario(), "paginationtest");

            // Añadir el nodo cargado al VBox
            vboxConteiner.getChildren().add(paginationTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}