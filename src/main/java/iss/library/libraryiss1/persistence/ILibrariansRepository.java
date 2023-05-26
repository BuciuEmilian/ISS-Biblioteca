package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Librarian;

public interface ILibrariansRepository extends ICrudRepository<Integer, Librarian> {
    Librarian findBy(String username, String password);
}
