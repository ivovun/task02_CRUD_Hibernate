package executor;

import exception.DBException;

import java.sql.ResultSet;

public interface Executor {
    void execUpdate(String update, Object[] params) throws DBException;

    <T, U > T execQuery(String query, Object[] params,
                     ResultHandler<T, U, ? extends Throwable> handler)
            throws DBException;
}
