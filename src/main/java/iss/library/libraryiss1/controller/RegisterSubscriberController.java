package iss.library.libraryiss1.controller;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterSubscriberController {
    private Services service;

    @FXML
    private TextField cnpField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;

    @FXML
    private TextField streetNameField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField countyField;

    public void setService(Services service) {
        this.service = service;
    }

    @FXML
    public void handleRegisterSubscriber(ActionEvent actionEvent) {
        long CNP;
        try {
            CNP = Long.parseLong(cnpField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        int streetNumber;
        try {
            streetNumber = Integer.parseInt(streetNumberField.getText());
            if (streetNumber <= 0)
                return;
        }
        catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        String name = nameField.getText();
        String phone = phoneField.getText();
        String streetName = streetNameField.getText();
        String county = countyField.getText();

        Address address = new Address(
                streetName,
                streetNumber,
                county
        );

        Subscriber subscriber = new Subscriber(
                name,
                CNP,
                phone
        );
        this.service.registerSubscriber(subscriber, address);

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
