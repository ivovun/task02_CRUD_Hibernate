package dao;

import exception.DBException;
import executor.ExecutorHibernate;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserHibernateDAO implements UserDao {
    private ExecutorHibernate executor;

    public UserHibernateDAO(Session session) {
        this.executor = new ExecutorHibernate(session);
    }

    @Override
    public boolean insertUser(User user) throws DBException {
        try {
            executor.getSession().save(user);
            return true;
        } catch (Throwable e) {
            throw new DBException(e);
        }
    }

    @Override
    public User selectUser(Long id) throws DBException {
        try {
            return (User) executor.getSession().byId(User.class).getReference(id);
        } catch (Throwable e) {
            throw new DBException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> selectAllUsers() throws DBException {
        try {
            return executor.execQuery(result -> (List<User>) executor.getSession().createQuery("select * from users").list());
        } catch (Throwable e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        return executor.execQuery((Session session) -> {
            session.delete(session.byId(User.class).getReference(id));
            return true;
        });
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        return executor.execQuery((Session session) -> {
            session.update(user);
            return true;
        });
    }
}
