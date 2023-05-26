package iss.library.libraryiss1.persistence.repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private SessionFactory instance = null;

    private SessionFactory getNewSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        SessionFactory session = null;

        try {
            session = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("[HibernateUtils] Exception " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return session;
    }

    public SessionFactory getSessionFactory() {
        if (instance == null || instance.isClosed())
            instance = getNewSessionFactory();
        return instance;
    }

    public void close(){
        if (instance != null) {
            instance.close();
        }
    }
}
