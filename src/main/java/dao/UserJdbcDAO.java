package dao;

import exception.DBException;
import executor.Executor;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDao {
    private Executor executor;

    public UserJdbcDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    @Override
    public void insertUser(User user) throws DBException {
        executor.execUpdatePrepared("INSERT INTO users (name, email, country) VALUES (?, ?, ?)",
                new Object[]{user.getName(), user.getEmail(), user.getCountry()});
    }

    @Override
    public User selectUser(Long id) throws DBException {
        return executor.execQueryPrepared("select id,name,email,country from users where id =?",
                new Object[]{id}, result -> {
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
        return executor.execQueryPrepared("select * from users where ?", new Object[]{true}, result -> {
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
        executor.execUpdatePrepared("delete from users where id = ?;", new Object[]{id});
        return true;
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        executor.execUpdatePrepared("update users set name = ?,email= ?, country =? where id = ?;",
                new Object[]{user.getName(), user.getEmail(), user.getCountry(), user.getId()});
        return true;
    }
}
