package iss.library.libraryiss1.persistence.repository.hibernate;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.persistence.IAddressesRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AddressesRepositoryHibernate implements IAddressesRepository {

    private final HibernateUtils dbUtils;

    public AddressesRepositoryHibernate() {
        this.dbUtils = new HibernateUtils();
    }

    @Override
    public Address save(Address address) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(address);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return address;
    }

    @Override
    public Address find(Address address) {
        return null;
    }

    @Override
    public List<Address> findAll() {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Address> addresses = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                addresses = session
                        .createQuery("FROM Address", Address.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return addresses;
    }
}
