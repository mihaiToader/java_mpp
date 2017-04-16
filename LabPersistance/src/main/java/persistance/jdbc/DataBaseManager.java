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

    public DataBaseManager() throws ClassNotFoundException, SQLException {
        Properties props=new Properties();
        try {
            props.load(DataBaseManager.class.getResourceAsStream("/bd.config"));
            System.out.println("Server properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        Class.forName(props.getProperty("jdbc.driver"));
        conn= DriverManager.getConnection(props.getProperty("jdbc.url"));
    }

    public Connection getConn() {
        return conn;
    }
}
