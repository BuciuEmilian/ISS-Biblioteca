package iss.library.libraryiss1;

import iss.library.libraryiss1.controller.LibrarianLoginController;
import iss.library.libraryiss1.controller.SubscriberLoginController;
import iss.library.libraryiss1.persistence.*;
import iss.library.libraryiss1.persistence.repository.jdbc.*;
import iss.library.libraryiss1.services.Services;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(StartApplication.class.getResourceAsStream("/db.properties"));
            System.out.println("Services properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find db.properties " + e);
        }

        ILibrariansRepository librariansRepository = new LibrariansRepositoryJdbc(props);
        IAddressesRepository addressesRepository = new AdressesRepositoryJdbc(props);
        ISubscribersRepository subscribersRepository = new SubscribersRepositoryJdbc(props);
        IBorrowsRepository borrowsRepository = new BorrowsRepositoryJdbc(props);
        IBooksRepository booksRepository = new BooksRepositoryJdbc(props);
        Services services = new Services(
                addressesRepository,
                librariansRepository,
                subscribersRepository,
                booksRepository,
                borrowsRepository
        );

        FXMLLoader librarianLoader = new FXMLLoader(
            getClass().getClassLoader().getResource("librarian-login-view.fxml")
        );
        Parent librarianLoginRoot = librarianLoader.load();

        LibrarianLoginController librarianLoginController = librarianLoader.getController();
        librarianLoginController.setService(services);

        stage.setTitle("Librarian login");
        stage.setScene(new Scene(librarianLoginRoot));
        stage.show();

        FXMLLoader subscriberLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("subscriber-login-view.fxml")
        );
        Parent subscriberLoginRoot = subscriberLoader.load();

        SubscriberLoginController subscriberLoginController = subscriberLoader.getController();
        subscriberLoginController.setService(services);

        Stage subscriberStage = new Stage();
        subscriberStage.setTitle("Subscriber login");
        subscriberStage.setScene(new Scene(subscriberLoginRoot));
        subscriberStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}