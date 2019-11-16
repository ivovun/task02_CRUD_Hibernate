package dao;

import exception.DBException;
import model.User;

import java.util.List;

public class UserHibernateDAO implements UserDao {
    @Override
    public void insertUser(User user) throws DBException {

    }

    @Override
    public User selectUser(Long id) throws DBException {
        return null;
    }

    @Override
    public List<User> selectAllUsers() throws DBException {
        return null;
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        return false;
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        return false;
    }
}
