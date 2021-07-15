package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL =
            "jdbc:mysql://localhost:3306/jmpt?autoReconnect=true&useSSL=false";
    private static final String USER = "jmpt";
    private static final String PASSWORD = "jmpt";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory sessionFactory;

    static {
        Properties properties = new Properties();
        properties.setProperty(Environment.URL, URL);
        properties.setProperty(Environment.USER, USER);
        properties.setProperty(Environment.PASS, PASSWORD);

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);

        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            sessionFactory.close();
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
