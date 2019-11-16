package service;

import dao.UserJdbcDAO;
import exception.DBException;
import model.User;
import util.DBHelper;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserJdbcDAO getUserDaoImpl() throws DBException {
        return new UserJdbcDAO(DBHelper.getConnection());
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
