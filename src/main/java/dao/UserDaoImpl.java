package dao;

import executor.Executor;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
	private Executor executor;

	public UserDaoImpl(Connection connection) {
		this.executor = new Executor(connection);
	}

    @Override
	public void insertUser(User user)  throws SQLException {
		executor.execUpdatePrepared("INSERT INTO users (name, email, country) VALUES (?, ?, ?)",
				new Object[] {user.getName(), user.getEmail(), user.getCountry()});
	}

    @Override
	public User selectUser(Long id) {
		try {
			return executor.execQueryPrepared("select id,name,email,country from users where id =?",
					new Object[] {id}, result -> {
						if (!result.next()) {
							return null;
						}
						return new User( result.getString("id"), result.getString("name"),
								result.getString("email"),
								result.getString("country"));
					});
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

    @Override
	public List<User> selectAllUsers() {
		try {
			return executor.execQueryPrepared("select * from users where ?", new Object[] {true} , result -> {
				List<User> users = new ArrayList<>();
				while (result.next()) {
					users.add(new User(result.getString("id"),result.getString("name"),
							result.getString("email"),
							result.getString("country")));
				}
				return users;
			});
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}

    @Override
	public boolean deleteUser(Long id) throws SQLException {
		executor.execUpdatePrepared("delete from users where id = ?;", new Object[]{id});
		return true;
	}

    @Override
	public boolean updateUser(User user) throws SQLException {

		executor.execUpdatePrepared("update users set name = ?,email= ?, country =? where id = ?;",
				new Object[] {user.getName(), user.getEmail(), user.getCountry(), user.getId()});
		return true;
	}
}
