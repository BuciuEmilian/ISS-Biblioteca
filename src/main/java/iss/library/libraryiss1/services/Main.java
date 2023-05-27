package iss.library.libraryiss1.services;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.*;
import iss.library.libraryiss1.persistence.repository.hibernate.*;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws LibraryException {
        IAddressesRepository addressesRepository = new AddressesRepositoryHibernate();
        ILibrariansRepository librariansRepository = new LibrariansRepositoryHibernate();
        IBorrowsRepository borrowsRepository = new BorrowsRepositoryHibernate();
        IBooksRepository booksRepository = new BooksRepositoryHibernate();
        ISubscribersRepository subscribersRepository = new SubscribersRepositoryHibernate();
        Services services = new Services(
                addressesRepository,
                librariansRepository,
                subscribersRepository,
                booksRepository,
                borrowsRepository
        );

//        Subscriber subscriber = new Subscriber(
//                "numeTest",
//                "usernameTest12",
//                "parolaTest",
//                5020911134199L,
//                "0729123456"
//        );
//        Address address = new Address(
//                "stradaTest",
//                2,
//                "judetTest"
//        );
//        services.registerSubscriber(subscriber, address);

//        Book book = new Book(
//                "titluTest",
//                "autorTest",
//                "genTest",
//                1
//        );
//        services.addBook(book);

        Subscriber subscriber = services.login(new Subscriber("mihu", "1"));
        Book book = services.findAllBooks().get(0);
        services.borrowBook(book, subscriber);
        services.findBorrows(subscriber).forEach(b -> {
            System.out.println(b.getBook().getTitle());
            System.out.println(b.getSubscriber().getName());
        });


    }
}