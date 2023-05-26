package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Book;

public interface IBooksRepository extends ICrudRepository<Integer, Book> {
    void save(Book book);

    void update(Book book);

    void delete(Book book);

    Book findBy(String title, String author);
}
