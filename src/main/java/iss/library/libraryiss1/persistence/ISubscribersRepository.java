package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Subscriber;

public interface ISubscribersRepository extends ICrudRepository<Integer, Subscriber> {
    void save(Subscriber subscriber);

    Subscriber findBy(String username, String password);
}
