package executor;

import exception.DBException;

public interface Executor {
    void execUpdate(String update, Object[] params) throws DBException;

    <T> T execQuery(String query, Object[] params,
                    ResultHandler<T> handler)
            throws DBException;
}
