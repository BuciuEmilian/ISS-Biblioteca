package iss.library.libraryiss1.persistence.repository.hibernate;

import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.persistence.IBooksRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookRepositoryHibernate implements IBooksRepository {
    private final HibernateUtils dbUtils;

    public BookRepositoryHibernate() {
        this.dbUtils = new HibernateUtils();
    }

    @Override
    public void save(Book book) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(book);
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
    public void update(Book book) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(book);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la delete " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        dbUtils.close();
    }

    @Override
    public void delete(Book book) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.delete(book);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la delete " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        dbUtils.close();
    }

    @Override
    public Book findBy(String title, String author) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Book> books = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                books = session
                        .createQuery("FROM Book", Book.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return books;
    }
}
