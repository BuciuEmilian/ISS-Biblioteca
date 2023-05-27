package iss.library.libraryiss1.persistence.repository.hibernate;

import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.model.Borrow;
import iss.library.libraryiss1.persistence.IBorrowsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowsRepositoryHibernate implements IBorrowsRepository {
    private final HibernateUtils dbUtils;

    public BorrowsRepositoryHibernate() {
        this.dbUtils = new HibernateUtils();
    }
    @Override
    public void save(Borrow borrow) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(borrow);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
    }

    @Override
    public void update(Borrow borrow) {

    }

    @Override
    public List<Borrow> findBorrowsBySubscriberId(int subscriberId) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Borrow> borrows = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                borrows = session
                        .createQuery("SELECT b FROM Borrow b " +
                                "INNER JOIN FETCH b.book " +
                                "INNER JOIN FETCH b.subscriber " +
                                "WHERE b.subscriber.id = :subscriber_id" , Borrow.class)
                        .setParameter("subscriber_id", subscriberId)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return borrows;
    }

    @Override
    public List<Borrow> findAll() {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Borrow> borrows = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                borrows = session
                        .createQuery("SELECT b FROM Borrow b INNER JOIN FETCH b.book INNER JOIN FETCH b.subscriber" , Borrow.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return borrows;
    }
}
