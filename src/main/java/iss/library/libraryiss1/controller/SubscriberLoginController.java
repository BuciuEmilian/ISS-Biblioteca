package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.Librarian;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.services.LibraryException;
import iss.library.libraryiss1.services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SubscriberLoginController {
    private Services service;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    public void setService(Services service) {
        this.service = service;
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        try {
            Subscriber subscriber = service.login(new Subscriber(username, password));

            usernameField.clear();
            passwordField.clear();

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getClassLoader().getResource("subscriber-view.fxml")
            );
            Parent mainRoot = fxmlLoader.load();

            SubscriberMainController mainController = fxmlLoader.getController();
            mainController.setService(service);
            mainController.setSubscriber(subscriber);

            Stage stage = new Stage();
            stage.setTitle("Subscriber window for " + subscriber.getName());
            stage.setScene(new Scene(mainRoot));
            stage.show();

            mainController.init();

        } catch (LibraryException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
