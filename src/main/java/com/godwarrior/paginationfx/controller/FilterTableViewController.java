package com.godwarrior.paginationfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FilterTableViewController {

    @FXML
    private ImageView backPageImgView;

    @FXML
    private ImageView filterImgView;

    @FXML
    private TableView<?> filterTableView;

    @FXML
    private VBox filterTableViewVBox;

    @FXML
    private Button goBackPageButton;

    @FXML
    private Button goNextButton;

    @FXML
    private ImageView nextPageImgView;

    @FXML
    private TextField numberPageTextField;

    @FXML
    private ComboBox<?> pageSelectComboBox;

    @FXML
    private ImageView resetFilterImgView;

}
