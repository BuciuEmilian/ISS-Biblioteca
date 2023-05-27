package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.model.Librarian;
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

public class LibrarianMainController implements Observer {
    private Services service;

    private Librarian librarian;
    @FXML
    private Label librarianLabel;

    private final ObservableList<Book> booksModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, Integer> quantityColumn;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField quantityField;

    public void setService(Services service) {
        this.service = service;
        this.service.addObserver(this);
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
        this.librarianLabel.setText(librarian.getUsername());
    }

    @FXML
    public void initialize() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        this.genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.tableView.setItems(booksModel);
    }

    private void initTable() {
        List<Book> books = this.service.findAllBooks();
        booksModel.setAll(books);
    }

    public void init() {
        this.initTable();
    }

    @Override
    public void update() {
        init();
    }

    @FXML
    public void handleBorrowsMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("borrows-view.fxml")
        );
        Parent mainRoot = fxmlLoader.load();

        BorrowsController borrowsController = fxmlLoader.getController();
        borrowsController.setService(service);

        Stage stage = new Stage();
        stage.setTitle("Borrows window for " + librarian.getName());
        stage.setScene(new Scene(mainRoot));
        stage.show();

        borrowsController.init();
    }

    @FXML
    public void handleRegisterSubscriber(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("signup-view.fxml")
        );
        Parent mainRoot = fxmlLoader.load();

        RegisterSubscriberController registerController = fxmlLoader.getController();
        registerController.setService(service);

        Stage stage = new Stage();
        stage.setTitle("Register window for " + librarian.getName());
        stage.setScene(new Scene(mainRoot));
        stage.show();
    }


    @FXML
    public void handleAddBook(ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0)
                return;
        }
        catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        Book book = new Book(title, author, genre, quantity);
        this.service.addBook(book);

        titleField.clear();
        authorField.clear();
        genreField.clear();
        quantityField.clear();
    }

    @FXML
    public void handleDeleteBook(ActionEvent actionEvent) {
        Book selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null)
            this.service.deleteBook(selected);
    }

    @FXML
    public void handleUpdateBook(ActionEvent actionEvent) {
        Book selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("no book selected");
            return;
        }

        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                System.out.println("invalid quantity");
                return;
            }
        }
        catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        Book book = new Book(
                title,
                author,
                genre,
                quantity
        );
        book.setId(selected.getId());

        this.service.updateBook(book);

        titleField.clear();
        authorField.clear();
        genreField.clear();
        quantityField.clear();
    }
}
