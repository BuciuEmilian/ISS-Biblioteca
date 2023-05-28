package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.model.Borrow;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.services.Observer.Observer;
import iss.library.libraryiss1.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class SubscriberMainController implements Observer {
    private Services service;

    private Subscriber subscriber;
    @FXML
    private Label subscriberLabel;

    private final ObservableList<Book> availableBooksModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Book> availableBooksTable;
    @FXML
    private TableColumn<Book, String> availableTitleColumn;
    @FXML
    private TableColumn<Book, String> availableAuthorColumn;
    @FXML
    private TableColumn<Book, Date> availableGenreColumn;
    @FXML
    private TableColumn<Book, Integer> availableQuantityColumn;

    private final ObservableList<Borrow> borrowsModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Borrow> borrowsTable;
    @FXML
    private TableColumn<Borrow, String> borrowedTitleColumn;
    @FXML
    private TableColumn<Borrow, String> borrowedAuthorColumn;
    @FXML
    private TableColumn<Borrow, String> borrowStatusColumn;
    @FXML
    private TableColumn<Borrow, Date> borrowDateColumn;
    @FXML
    private TableColumn<Borrow, Date> returnDateColumn;

    public void setService(Services service) {
        this.service = service;
        this.service.addObserver(this);
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        this.subscriberLabel.setText(subscriber.getUsername());
    }

    @FXML
    public void initialize() {
        availableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availableBooksTable.setItems(availableBooksModel);

        borrowedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowedAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        borrowStatusColumn.setCellValueFactory(new PropertyValueFactory<>("borrowStatus"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        borrowsTable.setItems(borrowsModel);
    }

    private void initTable() {
        List<Book> availableBooks = this.service.findAllBooks();
        availableBooksModel.setAll(availableBooks);
        List<Borrow> borrows = this.service.findBorrows(this.subscriber);
        borrowsModel.setAll(borrows);
    }

    public void init() {
        this.initTable();
    }

    @Override
    public void update() {
        init();
    }

    @FXML
    public void handleBorrowBook(ActionEvent actionEvent) {
        Book selected = availableBooksTable.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getQuantity() > 0)
            this.service.borrowBook(selected, this.subscriber);
    }
}
