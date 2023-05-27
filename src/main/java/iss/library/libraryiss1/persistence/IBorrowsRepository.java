package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Borrow;
import iss.library.libraryiss1.model.Pair;

import java.util.List;

public interface IBorrowsRepository extends ICrudRepository<Integer, Borrow> {
    void save(Borrow borrow);
    void update(Borrow borrow);
    List<Borrow> findBorrowsBySubscriberId(int subscriberId);
}
