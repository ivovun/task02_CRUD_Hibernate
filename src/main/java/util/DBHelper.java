package util;

import exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection getConnection() throws DBException {
        try {
            Class.forName(PropertyReader.getProperty("jdbcSqlDriver"));
            return DriverManager.getConnection(PropertyReader.getProperty("jdbcURL"),
                    PropertyReader.getProperty("jdbcUsername"),
                    PropertyReader.getProperty("jdbcPassword"));
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            throw new DBException(e);
        }
    }
}
