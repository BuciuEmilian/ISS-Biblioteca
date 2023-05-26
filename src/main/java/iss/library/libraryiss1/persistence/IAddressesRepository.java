package iss.library.libraryiss1.persistence;

import iss.library.libraryiss1.model.Address;

public interface IAddressesRepository extends ICrudRepository<Integer, Address> {
    Address save(Address address);

    Address find(Address address);
}
