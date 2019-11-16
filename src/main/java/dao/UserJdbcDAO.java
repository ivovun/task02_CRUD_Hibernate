package dao;

import exception.DBException;
import executor.ExecutorJDBC;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDao {
    private ExecutorJDBC executor;

    public UserJdbcDAO(Connection connection) {
        this.executor = new ExecutorJDBC(connection);
    }

    @Override
    public boolean insertUser(User user) throws DBException {
        executor.execUpdate("INSERT INTO users (name, email, country) VALUES (?, ?, ?)",
                new Object[]{user.getName(), user.getEmail(), user.getCountry()});
        return true;
    }

    @Override
    public User selectUser(Long id) throws DBException {
        return executor.execQuery("select id,name,email,country from users where id =?",
                new Object[]{id},(ResultSet result) -> {
                    if (!result.next()) {
                        return null;
                    }
                    return new User(result.getString("id"), result.getString("name"),
                            result.getString("email"),
                            result.getString("country"));
                });
    }

    @Override
    public List<User> selectAllUsers() throws DBException {
        return executor.execQuery("select * from users where ?", new Object[]{true}, (ResultSet result) -> {
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(new User(result.getString("id"), result.getString("name"),
                        result.getString("email"),
                        result.getString("country")));
            }
            return users;
        });
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        executor.execUpdate("delete from users where id = ?;", new Object[]{id});
        return true;
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        executor.execUpdate("update users set name = ?,email= ?, country =? where id = ?;",
                new Object[]{user.getName(), user.getEmail(), user.getCountry(), user.getId()});
        return true;
    }
}
