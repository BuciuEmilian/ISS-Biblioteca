package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Identifiable;

import java.util.List;

public interface ICrudRepository<T, E extends Identifiable<T>> {
    List<E> findAll();
}
