package iss.library.libraryiss1.persistence.repository.hibernate;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.model.Librarian;
import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.ILibrariansRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LibrariansRepositoryHibernate implements ILibrariansRepository {

    private final HibernateUtils dbUtils;

    public LibrariansRepositoryHibernate() {
        this.dbUtils = new HibernateUtils();
    }

    @Override
    public List<Librarian> findAll() {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Librarian> librarians = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                librarians = session
                        .createQuery("FROM Librarian", Librarian.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return librarians;
    }

    @Override
    public Librarian findBy(String username, String password) {
        Librarian librarian = null;

        SessionFactory sessionFactory =  dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "FROM Librarian WHERE username = :username and password = :password";
                List<Librarian> resultList = session
                        .createQuery(queryString, Librarian.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .list();
                librarian = resultList.get(0);
                tx.commit();
            }
            catch (RuntimeException e) {
                System.out.println("Eroare la select " + e);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return librarian;
    }
}
