package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "`name` varchar(255) NOT NULL," +
                "`lastName` varchar(255) NOT NULL," +
                "`age` tinyint NOT NULL," +
                "PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        Session session = Util.getSessionFactory().openSession();

        session
                .createSQLQuery(query)
                .executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS `users`;";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session
                .createSQLQuery(query)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw exception;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (session.get(User.class, id) != null) {
                session.delete(session.get(User.class, id));
            }
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw exception;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users = session.createQuery("From " + User.class.getSimpleName()).list();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE `users`;";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session
                .createSQLQuery(query)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
