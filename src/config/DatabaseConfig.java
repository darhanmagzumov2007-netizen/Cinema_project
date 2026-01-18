package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection  getConnection() throws SQLException {
        if (connection == null connection.isClosed()); {

        }
    }




}