package util;

import exception.DBException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection getConnection() throws DBException {
        try {
            Class.forName(PropertyReader.getProperty("jdbcSqlDriver"));
            return DriverManager.getConnection(PropertyReader.getProperty("jdbcURL"),
                    PropertyReader.getProperty("jdbcUsername"),
                    PropertyReader.getProperty("jdbcPassword"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(Class[] classes) {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory(classes);
        }
        return sessionFactory;
    }

    private static void setConfigurationPropertyForName(Configuration configuration, String name)  {
        configuration.setProperty(name, PropertyReader.getProperty(name));
    }

    @SuppressWarnings("UnusedDeclaration")
    private static Configuration getMySqlConfiguration(Class[] classes) {
        Configuration configuration = new Configuration();
        for (Class concreteClass : classes) {
            configuration.addAnnotatedClass(concreteClass);
        }

        setConfigurationPropertyForName(configuration, "hibernate.dialect");
        setConfigurationPropertyForName(configuration, "hibernate.connection.driver_class");
        setConfigurationPropertyForName(configuration, "hibernate.connection.url");
        setConfigurationPropertyForName(configuration, "hibernate.connection.serverTimezone");
        setConfigurationPropertyForName(configuration, "hibernate.connection.username");
        setConfigurationPropertyForName(configuration, "hibernate.connection.password");
        setConfigurationPropertyForName(configuration, "hibernate.show_sql");
        setConfigurationPropertyForName(configuration, "hibernate.hbm2ddl.auto");

        return configuration;
    }

    private static SessionFactory createSessionFactory(Class[] classes) {
        Configuration configuration = getMySqlConfiguration(classes);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
