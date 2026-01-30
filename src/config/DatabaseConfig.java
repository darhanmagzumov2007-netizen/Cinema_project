package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static Connection connection = null;

    public static Connection  getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("Database connected successfully!");

            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL Driver not found", e);
            }
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();

                System.out.println("Database connection closed.");

            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}