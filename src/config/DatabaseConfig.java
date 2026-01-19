package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/cinemadb";
    private static final String USER = "Adil";
    private static final String PASSWORD = "asd222555";

    private static Connection connection = null;

    public static Connection  getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("Database connected succesfully!");

            } catch (ClassNotFoundException e) {
                throw new SQLException("PsotgerSQL Driver not found", e);
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
