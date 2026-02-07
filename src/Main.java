//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import config.DatabaseConfig;
import controller.MainController;
import service.CinemaService;

public class Main {
    public static void main(String[] args) {
        System.out.println("---- Welcome to Cinema Management System  ----");

        try {
            DatabaseConfig.getConnection();
            CinemaService cinemaService = CinemaService.getInstance();
            MainController controller = new MainController(cinemaService);
            controller.start();
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
        } finally {
            DatabaseConfig.closeConnection();
        }

    }
}