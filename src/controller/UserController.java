package controller;

import entity.User;
import service.CinemaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public UserController(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }

    public void viewAllUsers() throws SQLException {
        System.out.println("\n=== ALL USERS ===");
        List<User> users = cinemaService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        users.forEach(user -> {
            System.out.printf("\nID: %d\n", user.getId());
            System.out.printf("Username: %s\n", user.getUsername());
            System.out.printf("Email: %s\n", user.getEmail());
            System.out.printf("Phone: %s\n", user.getPhone());
            System.out.printf("Role: %s\n", user.getRole());
            System.out.printf("Created: %s\n", user.getCreatedAt());
            System.out.println("-------------");
        });
    }

    public void addNewUser() throws SQLException {
        System.out.println("\n=== ADD NEW USER ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (USER/ADMIN): ");
        String role = scanner.nextLine().toUpperCase();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        user = cinemaService.addUser(user);

        System.out.println("\nUser added successfully! ID: " + user.getId());
    }

    public void updateUser() throws SQLException {
        System.out.println("\n=== UPDATE USER ===");

        System.out.print("Enter user ID to update: ");
        int userId = getIntInput();

        User user = cinemaService.userRepository.findById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\nCurrent user details:");
        System.out.printf("Username: %s\n", user.getUsername());
        System.out.printf("Email: %s\n", user.getEmail());
        System.out.printf("Phone: %s\n", user.getPhone());

        System.out.print("\nEnter new email (or press Enter to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        System.out.print("Enter new phone (or press Enter to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }

        cinemaService.userRepository.update(user);
        System.out.println("\nUser updated successfully!");
    }

    public void deleteUser() throws SQLException {
        System.out.println("\n=== DELETE USER ===");

        System.out.print("Enter user ID to delete: ");
        int userId = getIntInput();

        cinemaService.deleteUser(userId);
        System.out.println("\nUser deleted successfully!");
    }

    public User login() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = cinemaService.login(username, password);

        if (user != null) {
            System.out.println("\nLogin successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");
            return user;
        } else {
            System.out.println("\nLogin failed. Invalid username or password.");
            return null;
        }
    }

    public User register() throws SQLException {
        System.out.println("\n=== REGISTER ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (USER/ADMIN): ");
        String role = scanner.nextLine().toUpperCase();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(role);

        try {
            user = cinemaService.addUser(user);
            System.out.println("\nRegistration successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");
            return user;
        } catch (IllegalArgumentException e) {
            System.err.println("\nValidation error: " + e.getMessage());
            return null;
        }
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
