package controller;

import entity.Hall;
import service.CinemaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class HallController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public HallController(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }

    public void viewAllHalls() throws SQLException {
        System.out.println("\n=== ALL HALLS ===");

        List<Hall> halls = cinemaService.getAllHalls();

        if (halls.isEmpty()) {
            System.out.println("No halls found.");
            return;
        }

        halls.forEach(hall -> {
            System.out.printf("\nID: %d\n", hall.getId());
            System.out.printf("Name: %s\n", hall.getName());
            System.out.printf("Capacity: %d seats\n", hall.getCapacity());
            System.out.println("-------------");
        });
    }

    public void addNewHall() throws SQLException {
        System.out.println("\n=== ADD NEW HALL ===");

        System.out.print("Enter hall name: ");
        String name = scanner.nextLine();

        System.out.print("Enter capacity (number of seats): ");
        int capacity = getIntInput();

        Hall hall = new Hall(null, name, capacity);
        hall = cinemaService.addHall(hall);

        System.out.println("\nHall added successfully! ID: " + hall.getId());
    }

    public void updateHall() throws SQLException {
        System.out.println("\n=== UPDATE HALL ===");

        System.out.print("Enter hall ID to update: ");
        int hallId = getIntInput();

        Hall hall = cinemaService.hallRepository.findById(hallId);

        if (hall == null) {
            System.out.println("Hall not found.");
            return;
        }

        System.out.println("\nCurrent hall details:");
        System.out.printf("Name: %s\n", hall.getName());
        System.out.printf("Capacity: %d\n", hall.getCapacity());

        System.out.print("\nEnter new name (or press Enter to keep current): ");

        String name = scanner.nextLine();

        if (!name.isEmpty()) {
            hall.setName(name);
        }

        System.out.print("Enter new capacity (or -1 to keep current): ");
        int capacity = getIntInput();

        if (capacity > 0) {
            hall.setCapacity(capacity);
        }

        cinemaService.hallRepository.update(hall);

        System.out.println("\nHall updated successfully!");
    }

    public void deleteHall() throws SQLException {
        System.out.println("\n=== DELETE HALL ===");

        System.out.print("Enter hall ID to delete: ");
        int hallId = getIntInput();

        cinemaService.hallRepository.delete(hallId);
        System.out.println("\nHall deleted successfully!");
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
