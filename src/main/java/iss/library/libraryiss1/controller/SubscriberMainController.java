package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.Book;
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
    private TableColumn<Book, Integer> availableIdColumn;
    @FXML
    private TableColumn<Book, String> availableTitleColumn;
    @FXML
    private TableColumn<Book, String> availableAuthorColumn;
    @FXML
    private TableColumn<Book, String> availableGenreColumn;
    @FXML
    private TableColumn<Book, Integer> availableQuantityColumn;

    private final ObservableList<Book> borrowedBooksModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Book> borrowedBooksTable;
    @FXML
    private TableColumn<Book, Integer> borrowedIdColumn;
    @FXML
    private TableColumn<Book, String> borrowedTitleColumn;
    @FXML
    private TableColumn<Book, String> borrowedAuthorColumn;
    @FXML
    private TableColumn<Book, String> borrowedGenreColumn;
    @FXML
    private TableColumn<Book, Integer> borrowedQuantityColumn;

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
        availableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availableBooksTable.setItems(availableBooksModel);

        borrowedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        borrowedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowedAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowedGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        borrowedQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        borrowedBooksTable.setItems(borrowedBooksModel);
    }

    private void initTable() {
        List<Book> availableBooks = this.service.findAllBooks();
        availableBooksModel.setAll(availableBooks);
//        List<Book> borrowedBooks = this.service.findAllBorrowedBooks(this.Subscriber);
//        borrowedBooksModel.setAll(books);
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
        // TODO:
    }
}
