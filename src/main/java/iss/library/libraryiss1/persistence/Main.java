package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.*;
import iss.library.libraryiss1.persistence.repository.jdbc.*;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Main.class.getResourceAsStream("/db.properties"));
            System.out.println("Services properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find db.properties " + e);
        }

//        IBooksRepository booksRepository = new BooksRepositoryJdbc(props);
//        Book book = new Book("carteTest", "autorTest", "genTest", 2);
//        booksRepository.save(book);
//        book.setId(29);
//        book.setTitle("TEEEEEEEEST");
//        booksRepository.update(book);
//        booksRepository.delete(book);
//        booksRepository.findAll().forEach(System.out::println);

//        IAddressesRepository addressesRepository = new AdressesRepositoryJdbc(props);
//        Address address = new Address("stradaTest", 1, "judetTest");
//        addressesRepository.save(address);
//        addressesRepository.findAll().forEach(System.out::println);

//        ISubscribersRepository subscribersRepository = new SubscribersRepositoryJdbc(props);
//        Subscriber subscriber = new Subscriber(
//                "numeTest",
//                "usernameTest",
//                "parolaTest",
//                9999999999999L,
//                "0729008560",
//                1
//        );
//        System.out.println(subscriber.getUsername());
//        subscribersRepository.save(subscriber);
//        subscribersRepository.findAll().forEach(System.out::println);

//        ILibrariansRepository librariansRepository = new LibrariansRepositoryJdbc(props);
//        System.out.println(librariansRepository.findBy("b1", "1").getName());

        IBorrowsRepository borrowsRepository = new BorrowsRepositoryJdbc(props);
        Borrow borrow = new Borrow();
        borrow.setBookId(5);
        borrow.setSubscriberId(3);
        borrowsRepository.save(borrow);
    }
}