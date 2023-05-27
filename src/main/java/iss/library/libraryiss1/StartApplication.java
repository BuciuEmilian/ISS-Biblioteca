package iss.library.libraryiss1;

import iss.library.libraryiss1.controller.LibrarianLoginController;
import iss.library.libraryiss1.controller.SubscriberLoginController;
import iss.library.libraryiss1.persistence.*;
import iss.library.libraryiss1.persistence.repository.hibernate.*;
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
        ILibrariansRepository librariansRepository = new LibrariansRepositoryHibernate();
        IAddressesRepository addressesRepository = new AddressesRepositoryHibernate();
        ISubscribersRepository subscribersRepository = new SubscribersRepositoryHibernate();
        IBorrowsRepository borrowsRepository = new BorrowsRepositoryHibernate();
        IBooksRepository booksRepository = new BooksRepositoryHibernate();

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