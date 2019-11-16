package executor;

import org.hibernate.Session;

import java.sql.Connection;

public interface ResultHandlerHibernate<T, V extends Throwable> {
    T handle(Session session) throws V;
}