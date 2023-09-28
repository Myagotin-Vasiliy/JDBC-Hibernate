package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE;

public class UserDaoHibernateImpl implements UserDao {
    Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            String sqlQuery = "CREATE TABLE IF NOT EXISTS Users (id serial PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);";
            session.createSQLQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.getStatus() == ACTIVE) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sqlQuery = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.getStatus() == ACTIVE) {
                transaction.rollback();
            }
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.getStatus() == ACTIVE) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = session.get(User.class, id);
            if (user != null) session.delete(user);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
            if (transaction.getStatus() == ACTIVE) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        userList = session.createQuery("from User", User.class).list();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
