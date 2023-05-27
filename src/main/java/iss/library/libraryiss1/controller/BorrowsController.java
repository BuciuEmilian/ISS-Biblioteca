package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.*;
import iss.library.libraryiss1.services.Observer.Observer;
import iss.library.libraryiss1.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.List;

public class BorrowsController implements Observer {

    private Services service;

    private final ObservableList<Borrow> borrowsModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Borrow> tableView;
    @FXML
    private TableColumn<Borrow, String> bookTitleColumn;
    @FXML
    private TableColumn<Borrow, String> bookAuthorColumn;
    @FXML
    private TableColumn<Borrow, String> subscriberNameColumn;
    @FXML
    private TableColumn<Borrow, String> borrowStatusColumn;
    @FXML
    private TableColumn<Borrow, Date> borrowDateColumn;
    @FXML
    private TableColumn<Borrow, Date> returnDateColumn;

    private final ObservableList<Subscriber> subscribersModel = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Subscriber> subscribersCombo;

    public void setService(Services service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        this.bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        this.bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        this.subscriberNameColumn.setCellValueFactory(new PropertyValueFactory<>("subscriberName"));
        this.borrowStatusColumn.setCellValueFactory(new PropertyValueFactory<>("borrowStatus"));
        this.borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        this.returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        this.tableView.setItems(borrowsModel);
        this.subscribersCombo.setItems(subscribersModel);
    }

    public void init() {
        this.initTable(this.subscribersCombo.getValue());
        this.initCombo();
    }

    private void initTable(Subscriber subscriber) {
        List<Borrow> borrows = this.service.findBorrows(subscriber);
        borrowsModel.setAll(borrows);
    }

    private void initTable() {
        List<Borrow> borrows = this.service.findBorrows();
        borrowsModel.setAll(borrows);
    }

    private void initCombo() {
        List<Subscriber> subscribers = this.service.findAllSubscribers();
        subscribersModel.setAll(subscribers);
    }

    @Override
    public void update() {
        this.init();
    }
}
