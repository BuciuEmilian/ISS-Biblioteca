package iss.library.libraryiss1.persistence.repository.hibernate;

import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.ISubscribersRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SubscribersRepositoryHibernate implements ISubscribersRepository {
    private final HibernateUtils dbUtils;

    public SubscribersRepositoryHibernate() {
        this.dbUtils = new HibernateUtils();
    }

    @Override
    public Subscriber findBy(String username, String password) {
        Subscriber subscriber = null;

        SessionFactory sessionFactory =  dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "FROM Subscriber WHERE username = :username and password = :password";
                List<Subscriber> resultList = session
                        .createQuery(queryString, Subscriber.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .list();
                subscriber = resultList.get(0);
                tx.commit();
            }
            catch (RuntimeException e) {
                System.out.println("Eroare la select " + e);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return subscriber;
    }

    @Override
    public void save(Subscriber subscriber) {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("salvez asta");
                session.save(subscriber);
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
    public List<Subscriber> findAll() {
        SessionFactory sessionFactory = this.dbUtils.getSessionFactory();
        List<Subscriber> subscribers = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                subscribers = session
                        .createQuery("FROM Subscriber ", Subscriber.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        dbUtils.close();
        return subscribers;
    }
}
