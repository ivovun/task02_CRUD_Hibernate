package service;

import dao.UserDao;
import dao.UserHibernateDAO;
import exception.DBException;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userServiceImpl;
    //before Сделать полем   private static UserDao getUserDaoImpl()

    private SessionFactory sessionFactory;

    private UserServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceImpl getInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl(DBHelper.getSessionFactory(new Class[]{User.class}));
        }
        return userServiceImpl;
    }

    private static UserDao getUserDaoImpl()  {
        return new UserHibernateDAO(getInstance().getSessionFactory().openSession());
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
