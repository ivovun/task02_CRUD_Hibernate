package service;

import dao.UserDao;
import dao.UserHibernateDAO;
import dao.UserJdbcDAO;
import exception.DBException;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;
import util.PropertyReader;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userServiceImpl;

    private SessionFactory sessionFactory;

    private UserServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static boolean usingJDBC() {
        return PropertyReader.getProperty("if_JDBC_Then_1_if_Hibernate_Then_2").equals("1");
    }

    public static UserServiceImpl getInstance() {
        if (userServiceImpl == null) {
            if (usingJDBC()) {
                userServiceImpl = new UserServiceImpl(  null);
            } else {
                userServiceImpl = new UserServiceImpl(DBHelper.getSessionFactory(new Class[] {User.class}));
            }
        }
        return userServiceImpl;
    }

    private static UserDao getUserDaoImpl() throws DBException {
        if (usingJDBC()) {
            return new UserJdbcDAO(DBHelper.getConnection());
        } else  {
            return new UserHibernateDAO(getInstance().getSessionFactory().openSession());
        }
    }

    private SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public boolean insertUser(User user) throws DBException {
        getUserDaoImpl().insertUser(user);
        return true;
    }

    @Override
    public User selectUser(long id) throws DBException {
        return getUserDaoImpl().selectUser(id);
    }

    @Override
    public List<User> selectAllUsers() throws DBException {
        return getUserDaoImpl().selectAllUsers();
    }

    @Override
    public void deleteUser(long id) throws DBException {
        getUserDaoImpl().deleteUser(id);
    }

    @Override
    public void updateUser(User user) throws DBException {
        getUserDaoImpl().updateUser(user);
    }

}
