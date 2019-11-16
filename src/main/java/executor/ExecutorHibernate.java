package executor;

import exception.DBException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExecutorHibernate  {
    private final Session session;

    public ExecutorHibernate(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public <T> T execQuery( ResultHandlerHibernate<T, DBException> handler) throws DBException  {
        // !!!!  ИСПОЛЬЗУЮ null так как офиц код https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/example-weblog-code.html
        // из официального туториала на jboss.org ищем "Transaction tx = null;"  -->> https://docs.jboss.org/hibernate/core/3.3/reference/en/html/transactions.html
        Transaction transaction = null;
        T value; // можно (T) new Object(); // если боимся null
        try {
            transaction = session.beginTransaction();
            value = handler.handle(session);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw he;
        } finally {
            session.close();
        }
        return value;
    }

}
