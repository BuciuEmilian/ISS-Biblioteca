package iss.library.libraryiss1.model;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
