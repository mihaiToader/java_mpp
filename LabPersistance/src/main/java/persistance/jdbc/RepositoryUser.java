package persistance.jdbc;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistance.IRepository;
import persistance.IUserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihai on 14.03.2017.
 */
public class RepositoryUser implements IUserRepository {

    private SessionFactory sessionFactory;

    public RepositoryUser() {
        initialize();
    }

    private void initialize(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Override
    public void add(User e) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(User e, Integer id) {

    }

    @Override
    public Iterable<User> getAll() {
      return null;
    }

    public User validateUser(String username, String password) {
        Session session = sessionFactory.openSession();

        Transaction tx=null;
        User u = null;
        try{
            tx = session.beginTransaction();
            List<User> users =
                    session.createQuery("from User Where username=? and password=?",User.class)
                            .setParameter(0, username)
                            .setParameter(1, password)
                            .list();
            if (users.size() == 1) {
                u = users.get(0);
            }
            tx.commit();
        }catch(RuntimeException ex){
            if (tx!=null)
                tx.rollback();
        }finally{
            session.close();
        }
        return u;
    }
}
