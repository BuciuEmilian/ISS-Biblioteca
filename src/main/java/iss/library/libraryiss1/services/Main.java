package iss.library.libraryiss1.services;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.*;
import iss.library.libraryiss1.persistence.repository.jdbc.*;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(iss.library.libraryiss1.persistence.Main.class.getResourceAsStream("/db.properties"));
            System.out.println("Services properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find db.properties " + e);
        }

        IAddressesRepository addressesRepository = new AdressesRepositoryJdbc(props);
        ILibrariansRepository librariansRepository = new LibrariansRepositoryJdbc(props);
        IBorrowsRepository borrowsRepository = new BorrowsRepositoryJdbc(props);
        IBooksRepository booksRepository = new BooksRepositoryJdbc(props);
        ISubscribersRepository subscribersRepository = new SubscribersRepositoryJdbc(props);
        Services services = new Services(
                addressesRepository,
                librariansRepository,
                subscribersRepository,
                booksRepository,
                borrowsRepository
        );

        Subscriber subscriber = new Subscriber(
                "numeTest",
                "usernameTest1",
                "parolaTest",
                5020911134143L,
                "0729123456"
        );
        Address address = new Address(
                "stradaTest",
                2,
                "judetTest"
        );
        services.registerSubscriber(subscriber, address);
    }
}