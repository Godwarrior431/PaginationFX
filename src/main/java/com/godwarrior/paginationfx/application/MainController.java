package com.godwarrior.paginationfx.application;

import com.godwarrior.paginationfx.database.mysql.ConnectionMSQL;
import com.godwarrior.paginationfx.models.ColumnPagTable;
import com.godwarrior.paginationfx.models.FilterPagTable;
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
                    new FilterPagTable("Id de Usuario", "id", "number"),
                    new FilterPagTable("Nombre de Usuario", "nombre", "text"),
                    new FilterPagTable("Telefono de Usuario", "telefono", "number"),
                    new FilterPagTable("¿Esta activo?", "activo", "bool"),
                    new FilterPagTable("Fecha de Nacimiento", "fechaNacimiento", "date"),
                    new FilterPagTable("Horario de Registro", "horaRegistro", "time")
            ));

            VBox.setVgrow(paginatedTableView.getPaginationTable(), Priority.ALWAYS);
            vboxContainer.getChildren().add(paginatedTableView.getPaginationTable());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
