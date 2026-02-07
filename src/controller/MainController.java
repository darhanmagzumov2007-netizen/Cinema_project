package controller;

import entity.User;
import service.CinemaService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainController {
    private final CinemaService cinemaService;
    private final Scanner scanner;
    private User currentUser;

    private final MovieController movieController;
    private final ShowtimeController showtimeController;
    private final TicketController ticketController;
    private final UserController userController;
    private final HallController hallController;

    public MainController(CinemaService service) {
        this.cinemaService = service;
        this.scanner = new Scanner(System.in);
        this.currentUser = null;

        this.movieController = new MovieController(cinemaService, scanner);
        this.showtimeController = new ShowtimeController(cinemaService, scanner);
        this.ticketController = new TicketController(cinemaService, scanner);
        this.userController = new UserController(cinemaService, scanner);
        this.hallController = new HallController(cinemaService, scanner);
    }

    public void start() {
        if (!loginMenu()) {
            System.out.println("Login required. Exiting...");
            return;
        }

        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            try {
                switch (choice) {
                    case 1:
                        movieController.viewAllMovies();
                        break;

                    case 2:
                        if (checkAdmin()) movieController.addNewMovie();
                        break;

                    case 3:
                        showtimeController.viewShowtimesByDate();
                        break;

                    case 4:
                        showtimeController.viewFullShowtimeDetails();
                        break;

                    case 5:
                        if (checkAdmin()) showtimeController.addNewShowtime();
                        break;

                    case 6:
                        ticketController.bookTicket(currentUser);
                        break;

                    case 7:
                        ticketController.cancelBooking();
                        break;

                    case 8:
                        ticketController.viewAvailableSeats();
                        break;

                    case 9:
                        ticketController.viewFullTicketDetails();
                        break;

                    case 10:
                        showtimeController.viewShowtimeRevenue();
                        break;

                    case 11:
                        showtimeController.viewOccupancyRate();
                        break;

                    case 12:
                        if (checkAdmin()) manageUsers();
                        break;

                    case 13:
                        if (checkAdmin()) manageHalls();
                        break;

                    case 14:
                        movieController.viewMoviesByCategory();
                        break;

                    case 15:
                        if (checkAdmin()) manageMovies();
                        break;

                    case 16:
                        if (checkAdmin()) manageShowtimes();
                        break;

                    case 17:
                        ticketController.viewMyTickets(currentUser);
                        break;

                    case 18:
                        logout();
                        return;

                    case 0:
                        System.out.println("Thank you for using Cinema Management System!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (IllegalArgumentException e) {
                System.err.println("Validation error: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private boolean loginMenu() {
        System.out.println("\n=== LOGIN ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter choice: ");

        int choice = getIntInput();

        try {
            if (choice == 1) {
                currentUser = userController.login();
                return currentUser != null;
            } else if (choice == 2) {
                currentUser = userController.register();
                return currentUser != null;
            } else {
                System.out.println("Invalid choice.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    private void logout() {
        System.out.println("\nLogged out successfully. Goodbye, " + currentUser.getUsername() + "!");
        currentUser = null;
    }

    private boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    private boolean checkAdmin() {
        if (!isAdmin()) {
            System.out.println("\nAccess Denied! This operation requires ADMIN role.");
            return false;
        }
        return true;
    }

    private void displayMainMenu() {
        System.out.println("\n----- CINEMA MANAGEMENT SYSTEM -----");
        System.out.println("Logged in as: " + currentUser.getUsername() + " [" + currentUser.getRole() + "]");
        System.out.println("------------------------------------");
        System.out.println("=== MOVIES ===");
        System.out.println("1. View All Movies");
        System.out.println("2. Add New Movie (Admin)");
        System.out.println("3. View Movies by Category");
        System.out.println("4. Manage Movies (Admin)");

        System.out.println("\n=== SHOWTIMES ===");
        System.out.println("5. View Showtimes by Date");
        System.out.println("6. View Full Showtime Details");
        System.out.println("7. Add New Showtime (Admin)");
        System.out.println("8. View Showtime Revenue");
        System.out.println("9. View Occupancy Rate");
        System.out.println("10. Manage Showtimes (Admin)");

        System.out.println("\n=== TICKETS ===");
        System.out.println("11. Book a Ticket");
        System.out.println("12. Cancel Booking");
        System.out.println("13. View Available Seats");
        System.out.println("14. View Full Ticket Details");
        System.out.println("15. View My Tickets");

        System.out.println("\n=== ADMINISTRATION ===");
        System.out.println("16. Manage Users (Admin)");
        System.out.println("17. Manage Halls (Admin)");

        System.out.println("\n=== SYSTEM ===");
        System.out.println("18. Logout");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private void manageUsers() throws SQLException {
        System.out.println("\n=== USER MANAGEMENT ===");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                userController.viewAllUsers();
                break;
            case 2:
                userController.addNewUser();
                break;
            case 3:
                userController.updateUser();
                break;
            case 4:
                userController.deleteUser();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void manageHalls() throws SQLException {
        System.out.println("\n=== HALL MANAGEMENT ===");
        System.out.println("1. View All Halls");
        System.out.println("2. Add New Hall");
        System.out.println("3. Update Hall");
        System.out.println("4. Delete Hall");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                hallController.viewAllHalls();
                break;
            case 2:
                hallController.addNewHall();
                break;
            case 3:
                hallController.updateHall();
                break;
            case 4:
                hallController.deleteHall();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void manageMovies() throws SQLException {
        System.out.println("\n=== MOVIE MANAGEMENT ===");
        System.out.println("1. View All Movies");
        System.out.println("2. Add New Movie");
        System.out.println("3. Update Movie");
        System.out.println("4. Delete Movie");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                movieController.viewAllMovies();
                break;
            case 2:
                movieController.addNewMovie();
                break;
            case 3:
                movieController.updateMovie();
                break;
            case 4:
                movieController.deleteMovie();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void manageShowtimes() throws SQLException {
        System.out.println("\n=== SHOWTIME MANAGEMENT ===");
        System.out.println("1. View Showtimes by Date");
        System.out.println("2. Add New Showtime");
        System.out.println("3. Update Showtime");
        System.out.println("4. Delete Showtime");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                showtimeController.viewShowtimesByDate();
                break;
            case 2:
                showtimeController.addNewShowtime();
                break;
            case 3:
                showtimeController.updateShowtime();
                break;
            case 4:
                showtimeController.deleteShowtime();
                break;
            default:
                System.out.println("Invalid choice.");
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
