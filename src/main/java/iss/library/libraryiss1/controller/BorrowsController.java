package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.*;
import iss.library.libraryiss1.services.Observer.Observer;
import iss.library.libraryiss1.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
        this.service.addObserver(this);
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
        subscribersCombo.setCellFactory(param -> new ListCell<Subscriber>() {
            @Override
            protected void updateItem(Subscriber subscriber, boolean empty) {
                super.updateItem(subscriber, empty);
                if (empty || subscriber == null) {
                    setText(null);
                } else {
                    setText(subscriber.getName());
                }
            }
        });

        subscribersCombo.setConverter(new StringConverter<Subscriber>() {
            @Override
            public String toString(Subscriber subscriber) {
                if (subscriber == null) {
                    return null;
                } else {
                    return subscriber.getName();
                }
            }

            @Override
            public Subscriber fromString(String string) {
                return null;
            }
        });

        this.subscribersCombo.setItems(subscribersModel);
    }

    public void init() {
        this.initCombo();
        this.initTable(this.subscribersCombo.getValue());
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

    @FXML
    public void handleComboChanged(ActionEvent actionEvent) {
        System.out.println(subscribersCombo.getValue().getId());
        this.initTable(subscribersCombo.getValue());
    }

    @FXML
    public void handleReturnButton(ActionEvent actionEvent) {
        Borrow selectedBorrow = tableView.getSelectionModel().getSelectedItem();
        Subscriber selectedSubscriber = subscribersCombo.getValue();
        if (selectedBorrow != null && selectedSubscriber != null) {
            if (selectedBorrow.getBorrowStatus() == BorrowStatus.ACTIVE) {
                Book book = selectedBorrow.getBook();
                this.service.returnBook(book, selectedSubscriber);
            }
            else {
                System.out.println("Nu se poate returna");
            }
        }
    }
}
