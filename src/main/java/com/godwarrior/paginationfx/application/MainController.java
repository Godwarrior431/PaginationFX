package com.godwarrior.paginationfx.application;

import com.godwarrior.paginationfx.database.mysql.ConnectionMSQL;
import com.godwarrior.paginationfx.models.ColumnPagTable;
import com.godwarrior.paginationfx.models.Filter;
import com.godwarrior.paginationfx.utils.TableViewPaginated;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class MainController {

    @FXML
    private VBox vboxContainer;

    @FXML
    public void initialize() {
        try {
            ConnectionMSQL.getInstance("localhost", "paginationtest", "root", "");

            TableViewPaginated<Usuario> paginatedTableView = new TableViewPaginated<>(Usuario.class, "usuario");

            paginatedTableView.addColumns(Arrays.asList(
                    new ColumnPagTable("Identificador", "id"),
                    new ColumnPagTable("Nombre", "nombre"),
                    new ColumnPagTable("Apellido", "apellido"),
                    new ColumnPagTable("Telefono", "telefono"),
                    new ColumnPagTable("Fecha Nacimiento", "fechaNacimiento"),
                    new ColumnPagTable("¿Esta activo?", "activo"),
                    new ColumnPagTable("Hora de Registro", "horaRegistro")
            ));

            paginatedTableView.addFilters(Arrays.asList(
                    new Filter("Id de Usuario", "id", "number"),
                    new Filter("Nombre de Usuario", "nombre", "text"),
                    new Filter("Telefono de Usuario", "telefono", "number"),
                    new Filter("¿Esta activo?", "activo", "bool"),
                    new Filter("Fecha de Nacimiento", "fechaNacimiento", "date"),
                    new Filter("Horario de Registro", "horaRegistro", "time")
            ));

            VBox.setVgrow(paginatedTableView.getPaginationTable(), Priority.ALWAYS);
            vboxContainer.getChildren().add(paginatedTableView.getPaginationTable());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
