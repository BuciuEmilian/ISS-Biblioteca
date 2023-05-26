package iss.library.libraryiss1.persistence.repository.jdbc;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties jdbcProps;
    public JdbcUtils(Properties jdbcProps) {
        this.jdbcProps = jdbcProps;
    }
    private Connection instance = null;

    private Connection getNewConnection() {
        String url = jdbcProps.getProperty("racing.jdbc.url");
        String username = jdbcProps.getProperty("racing.jdbc.username");
        String password = jdbcProps.getProperty("racing.jdbc.password");

        Connection connection = null;
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        try {
            if (username != null && password != null)
                connection = DriverManager.getConnection(url, username, password);
            else
                connection = DriverManager.getConnection(url, config.toProperties());
        }
        catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }

        return connection;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();
        }
        catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return instance;
    }
}
