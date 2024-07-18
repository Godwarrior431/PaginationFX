package com.godwarrior.paginationfx.application;

import com.godwarrior.paginationfx.controller.PaginationTableController;
import com.godwarrior.paginationfx.database.mysql.ConnectionMSQL;
import com.godwarrior.paginationfx.models.Filter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class MainController {

    @FXML
    private VBox vboxContainer;

    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/godwarrior/paginationfx/resources/view/PaginationTableView.fxml"));
            Node paginationTable = loader.load();

            ConnectionMSQL.getInstance("localhost", "paginationtest", "root", "");

            PaginationTableController<Usuario> newTable = loader.getController();
            newTable.initialize(Usuario.class, "usuario");

            newTable.addColumn("Identificador", "id");
            newTable.addColumn("Nombre", "nombre");
            newTable.addColumn("Apellido", "apellido");
            newTable.addColumn("Telefono", "telefono");
            newTable.addColumn("Fecha Nacimiento", "fechaNacimiento");
            newTable.addColumn("¿Esta activo?", "activo");
            newTable.addColumn("Hora de Registro", "horaRegistro");

            newTable.addFilters(new ArrayList<>(Arrays.asList(
                    new Filter("Id de Usuario", "id", "number"),
                    new Filter("Nombre de Usuario", "nombre", "text"),
                    new Filter("Telefono de Usuario", "telefono", "number"),
                    new Filter("¿Esta activo?", "activo", "bool"),
                    new Filter("Fecha de Nacimiento", "fechaNacimiento", "date"),
                    new Filter("Horario de Registro", "horaRegistro", "time")
            )));

            VBox.setVgrow(paginationTable, Priority.ALWAYS);
            vboxContainer.getChildren().add(paginationTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
