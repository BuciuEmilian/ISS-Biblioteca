package iss.library.libraryiss1.services;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.model.Librarian;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.*;
import iss.library.libraryiss1.persistence.exceptions.RepositoryException;
import iss.library.libraryiss1.services.Observer.Observable;
import iss.library.libraryiss1.services.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Services implements Observable {
    private final IAddressesRepository addressesRepository;
    private final ILibrariansRepository librariansRepository;
    private final ISubscribersRepository subscribersRepository;
    private final IBooksRepository booksRepository;
    private final IBorrowsRepository borrowsRepository;
    public Services(
            IAddressesRepository addressesRepository,
            ILibrariansRepository librariansRepository,
            ISubscribersRepository subscribersRepository,
            IBooksRepository booksRepository,
            IBorrowsRepository borrowsRepository
    ) {
        this.addressesRepository = addressesRepository;
        this.librariansRepository = librariansRepository;
        this.subscribersRepository = subscribersRepository;
        this.booksRepository = booksRepository;
        this.borrowsRepository = borrowsRepository;
    }

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public Subscriber login(Subscriber subscriber) throws LibraryException {
        Subscriber loggedSubscriber = this
                .subscribersRepository
                .findBy(subscriber.getUsername(), subscriber.getPassword());
        if (loggedSubscriber != null) {
            return loggedSubscriber;
        }
        else {
            throw new LibraryException("Subscriber authentication failed.");
        }
    }

    public Librarian login(Librarian librarian) throws LibraryException {
        Librarian loggedLibrarian = this
                .librariansRepository
                .findBy(librarian.getUsername(), librarian.getPassword());
        if (loggedLibrarian != null) {
            return loggedLibrarian;
        }
        else {
            throw new LibraryException("Librarian authentication failed");
        }
    }

    public void addBook(Book book) {
        this.booksRepository.save(book);
        this.notifyObservers();
    }

    public List<Book> findAllBooks() {
        return this.booksRepository.findAll();
    }

    public void updateBook(Book book) {
        try {
            this.booksRepository.update(book);
            this.notifyObservers();
        }
        catch (RepositoryException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteBook(Book book) {
        try {
            this.booksRepository.delete(book);
            this.notifyObservers();
        }
        catch (RepositoryException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void registerSubscriber(Subscriber subscriber, Address address) {
        // if the address is the db, the new address is the same
        try {
            Address newAddress = this.addressesRepository.find(address);
            // otherwise we add it
            if (newAddress == null)
                newAddress = this.addressesRepository.save(address);

            // link the subscriber to his address
            subscriber.setAddressId(newAddress.getId());
            this.subscribersRepository.save(subscriber);
//        this.notifyObservers();
        }
        catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }
}
