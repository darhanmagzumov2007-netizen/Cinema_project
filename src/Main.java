import config.DatabaseConfig;
import controller.CinemaController;

public class Main {
    public static void main(String[] args) {
        System.out.println("---- Welcome to Cinema Management System  ----");

        try {
            DatabaseConfig.getConnection();

            CinemaController controller = new CinemaController();
            controller.start();

        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());

        } finally {
            DatabaseConfig.closeConnection();
       }
    }
}