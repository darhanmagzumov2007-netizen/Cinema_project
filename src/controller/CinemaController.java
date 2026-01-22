package controller;

import entity.Movie;
import entity.Showtime;
import entity.Ticket;
import entity.User;
import entity.Hall;
import service.CinemaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class CinemaController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public CinemaController() {
        this.cinemaService = new CinemaService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            try {
                switch (choice) {
                    case 1:
                        viewAllMovies();
                        break;

                    case 2:
                        addNewMovie();
                        break;

                    case 3:
                        viewShowtimesByDate();
                        break;

                    case 4:
                        addNewShowtime();
                        break;

                    case 5:
                        bookTicketMenu();
                        break;

                    case 6:
                        cancelBookingMenu();
                        break;

                    case 7:
                        viewAvailableSeats();
                        break;

                    case 8:
                        viewShowtimeRevenue();
                        break;

                    case 9:
                        viewOccupancyRate();
                        break;

                    case 10:
                        manageUsers();
                        break;

                    case 11:
                        manageHalls();
                        break;

                    case 0:
                        System.out.println("Thank you for using Cinema Management System!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMainMenu() {
        System.out.println("----- CINEMA MANAGEMENT SYSTEM -----");
        System.out.println("1. View All Movies");
        System.out.println("2. Add New Movie");
        System.out.println("3. View Showtimes by Date");
        System.out.println("4. Add New Showtime");
        System.out.println("5. Book a Ticket");
        System.out.println("6. Cancel Booking");
        System.out.println("7. View Available Seats");
        System.out.println("8. View Showtime Revenue");
        System.out.println("9. View Occupancy Rate");
        System.out.println("10. Manage Users");
        System.out.println("11. Manage Halls");
        System.out.println("0. Exit");

        System.out.print("\nEnter your choice: ");
    }

    private void viewAllMovies() throws SQLException {
        System.out.println("\n=== ALL MOVIES ===");
        List<Movie> movies = cinemaService.getAllMovies();

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        for (Movie movie : movies) {
            System.out.printf("\nID: %d\n", movie.getId());
            System.out.printf("Title: %s\n", movie.getTitle());
            System.out.printf("Genre: %s\n", movie.getGenre());
            System.out.printf("Duration: %d minutes\n", movie.getDuration());
            System.out.printf("Rating: %.1f/10\n", movie.getRating());
            System.out.printf("Release Date: %s\n", movie.getReleaseDate());
            System.out.println("-------------");
        }
    }

    private void addNewMovie() throws SQLException {
        System.out.println("\n=== ADD NEW MOVIE ===");

        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter duration (minutes): ");
        int duration = getIntInput();

        System.out.print("Enter rating (0-10): ");
        double rating = getDoubleInput();

        System.out.print("Enter release date (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie(null, title, genre, duration, rating, releaseDate);

        movie = cinemaService.addMovie(movie);

        System.out.println("\nMovie added successfully! ID: " + movie.getId());
    }

    private void viewShowtimesByDate() throws SQLException {
        System.out.println("\n=== VIEW SHOWTIMES ===");
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        List<Showtime> showtimes = cinemaService.getShowtimesByDate(date);

        if (showtimes.isEmpty()) {
            System.out.println("No showtimes found for this date.");
            return;
        }

        for (Showtime showtime : showtimes) {
            System.out.printf("\nShowtime ID: %d\n", showtime.getId());
            System.out.printf("Movie ID: %d | Hall ID: %d\n", showtime.getMovieId(), showtime.getHallId());
            System.out.printf("Time: %s\n", showtime.getShowTime());
            System.out.printf("Price: ₸%.2f\n", showtime.getPrice());
            System.out.println("-------------");
        }
    }

    private void addNewShowtime() throws SQLException {
        System.out.println("\n=== ADD NEW SHOWTIME ===");

        System.out.print("Enter movie ID: ");
        int movieId = getIntInput();

        System.out.print("Enter hall ID: ");
        int hallId = getIntInput();

        System.out.print("Enter show date (YYYY-MM-DD): ");
        LocalDate showDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter show time (HH:MM): ");
        LocalTime showTime = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter ticket price: ");
        double price = getDoubleInput();

        Showtime showtime = new Showtime(null, movieId, hallId, showDate, showTime, price);
        showtime = cinemaService.addShowtime(showtime);

        System.out.print("\nHow many seats to generate? ");
        int seats = getIntInput();
        cinemaService.generateTicketsForShowtime(showtime.getId(), seats);

        System.out.println("\nShowtime added successfully with " + seats + " seats!");
    }

    private void bookTicketMenu() throws SQLException {
        System.out.println("\n=== BOOK A TICKET ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        List<Ticket> availableSeats = cinemaService.getAvailableSeats(showtimeId);

        if (availableSeats.isEmpty()) {
            System.out.println("No available seats for this showtime.");
            return;
        }

        System.out.println("\nAvailable seats:");
        for (Ticket ticket : availableSeats) {
            System.out.printf("Ticket ID: %d - Seat: %s\n", ticket.getId(), ticket.getSeatNumber());
        }

        System.out.print("\nEnter ticket ID to book: ");
        int ticketId = getIntInput();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        try {
            boolean success = cinemaService.bookTicket(ticketId, username);

            if (success) {
                Ticket bookedTicket = cinemaService.getTicketById(ticketId);
                System.out.println("\nTicket booked successfully!");
                System.out.println("  Customer: " + username);
                System.out.println("  Seat: " + bookedTicket.getSeatNumber());
                System.out.println("  Status: BOOKED");
            } else {
                System.out.println("\nFailed to book ticket. Seat may already be booked.");
            }
        } catch (SQLException e) {
            System.err.println("\n✗ Error: " + e.getMessage());
            if (e.getMessage().contains("not found")) {
                System.out.println("\nTip: Register a new user first (Menu option 10)");
            }
        }
    }

    private void cancelBookingMenu() throws SQLException {
        System.out.println("\n=== CANCEL BOOKING ===");

        System.out.print("Enter ticket ID to cancel: ");
        int ticketId = getIntInput();

        Ticket cancelledTicket = cinemaService.cancelBooking(ticketId);

        if (cancelledTicket != null) {
            System.out.println("\n✓ Booking cancelled successfully!");
            System.out.println("  Seat: " + cancelledTicket.getSeatNumber());
            System.out.println("  Status: NOW AVAILABLE");
            System.out.println("  Showtime ID: " + cancelledTicket.getShowtimeId());
        } else {
            System.out.println("\n✗ Failed to cancel. Ticket may not be booked.");
        }
    }

    private void viewAvailableSeats() throws SQLException {
        System.out.println("\n=== AVAILABLE SEATS ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        List<Ticket> availableSeats = cinemaService.getAvailableSeats(showtimeId);

        System.out.printf("\nTotal available seats: %d\n", availableSeats.size());

        for (Ticket ticket : availableSeats) {
            System.out.printf("Seat: %s (Ticket ID: %d)\n", ticket.getSeatNumber(), ticket.getId());
        }
    }

    private void viewShowtimeRevenue() throws SQLException {
        System.out.println("\n=== SHOWTIME REVENUE ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        double revenue = cinemaService.calculateShowtimeRevenue(showtimeId);

        System.out.printf("\nTotal Revenue: $%.2f\n", revenue);
    }

    private void viewOccupancyRate() throws SQLException {
        System.out.println("\n=== OCCUPANCY RATE ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        double occupancy = cinemaService.calculateOccupancyRate(showtimeId);
        System.out.printf("\nOccupancy Rate: %.2f%%\n", occupancy);
    }

    private void manageUsers() throws SQLException {
        System.out.println("\n=== USER MANAGEMENT ===");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Delete User");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                viewAllUsers();
                break;
            case 2:
                addNewUser();
                break;
            case 3:
                deleteUser();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void viewAllUsers() throws SQLException {
        System.out.println("\n=== ALL USERS ===");
        List<User> users = cinemaService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.printf("\nID: %d\n", user.getId());
            System.out.printf("Username: %s\n", user.getUsername());
            System.out.printf("Email: %s\n", user.getEmail());
            System.out.printf("Phone: %s\n", user.getPhone());
            System.out.printf("Created: %s\n", user.getCreatedAt());
            System.out.println("-------------");
        }
    }

    private void addNewUser() throws SQLException {
        System.out.println("\n=== ADD NEW USER ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        User user = new User(null, username, email, phone, null);
        user = cinemaService.addUser(user);

        System.out.println("\nUser added successfully! ID: " + user.getId());
    }

    private void deleteUser() throws SQLException {
        System.out.println("\n=== DELETE USER ===");

        System.out.print("Enter user ID to delete: ");
        int userId = getIntInput();

        cinemaService.deleteUser(userId);
        System.out.println("\n✓ User deleted successfully!");
    }

    private void manageHalls() throws SQLException {
        System.out.println("\n=== HALL MANAGEMENT ===");
        System.out.println("1. View All Halls");
        System.out.println("2. Add New Hall");
        System.out.print("\nEnter your choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                viewAllHalls();
                break;
            case 2:
                addNewHall();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void viewAllHalls() throws SQLException {
        System.out.println("\n=== ALL HALLS ===");
        List<Hall> halls = cinemaService.getAllHalls();

        if (halls.isEmpty()) {
            System.out.println("No halls found.");
            return;
        }

        for (Hall hall : halls) {
            System.out.printf("\nID: %d\n", hall.getId());
            System.out.printf("Name: %s\n", hall.getName());
            System.out.printf("Capacity: %d seats\n", hall.getCapacity());
            System.out.println("-------------");
        }
    }

    private void addNewHall() throws SQLException {
        System.out.println("\n=== ADD NEW HALL ===");

        System.out.print("Enter hall name: ");
        String name = scanner.nextLine();

        System.out.print("Enter capacity (number of seats): ");
        int capacity = getIntInput();

        Hall hall = new Hall(null, name, capacity);
        hall = cinemaService.addHall(hall);

        System.out.println("\nHall added successfully! ID: " + hall.getId());
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

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());

            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
