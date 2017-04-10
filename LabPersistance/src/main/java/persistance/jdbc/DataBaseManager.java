package persistance.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Mihai on 14.03.2017.
 */
public class DataBaseManager {
    private Connection conn;

    public DataBaseManager(Properties props) throws ClassNotFoundException, SQLException {
        Class.forName(props.getProperty("jdbc.driver"));
        conn= DriverManager.getConnection(props.getProperty("jdbc.url"));
    }

    public Connection getConn() {
        return conn;
    }
}
