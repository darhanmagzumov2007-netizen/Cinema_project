package controller;


import dto.FullTicketInfoDTO;
import entity.Movie;
import entity.Showtime;
import entity.Ticket;
import entity.User;
import entity.Hall;
import service.CinemaService;
import dto.FullShowtimeDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


public class CinemaController {
    private final CinemaService cinemaService;
    private final Scanner scanner;
    private User currentUser;

    public CinemaController(CinemaService service) {
        this.cinemaService = service;
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
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
                        viewAllMovies();
                        break;

                    case 2:
                        addNewMovie();
                        break;

                    case 3:
                        viewShowtimesByDate();
                        break;

                    case 4:
                        viewFullShowtimeDetails();
                        break;

                    case 5:
                        addNewShowtime();
                        break;

                    case 6:
                        bookTicketMenu();
                        break;

                    case 7:
                        cancelBookingMenu();
                        break;

                    case 8:
                        viewAvailableSeats();
                        break;

                    case 9:
                        viewFullTicketDetails();
                        break;

                    case 10:
                        viewShowtimeRevenue();
                        break;

                    case 11:
                        viewOccupancyRate();
                        break;

                    case 12:
                        manageUsers();
                        break;

                    case 13:
                        manageHalls();
                        break;

                    case 14:
                        viewMoviesByCategory();
                        break;

                    case 15:
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
                return login();

            } else if (choice == 2) {
                return registerAndLogin();

            } else {
                System.out.println("Invalid choice.");

                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    private boolean login() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = cinemaService.login(username, password);

        if (user != null) {
            currentUser = user;
            System.out.println("\nLogin successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");
            return true;
        } else {
            System.out.println("\nLogin failed. Invalid username or password.");

            return false;
        }
    }

    private boolean registerAndLogin() throws SQLException {
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
            currentUser = user;

            System.out.println("\nRegistration successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");

            return true;
        } catch (IllegalArgumentException e) {

            System.err.println("\nValidation error: " + e.getMessage());

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

    private void displayMainMenu() {
        System.out.println("\n----- CINEMA MANAGEMENT SYSTEM -----");
        System.out.println("Logged in as: " + currentUser.getUsername() + " [" + currentUser.getRole() + "]");
        System.out.println("------------------------------------");
        System.out.println("1. View All Movies");
        System.out.println("2. Add New Movie");
        System.out.println("3. View Showtimes by Date");
        System.out.println("4. View Full Showtime Details");
        System.out.println("5. Add New Showtime");
        System.out.println("6. Book a Ticket");
        System.out.println("7. Cancel Booking");
        System.out.println("8. View Available Seats");
        System.out.println("9. View Full Ticket Details");
        System.out.println("10. View Showtime Revenue");
        System.out.println("11. View Occupancy Rate");
        System.out.println("12. Manage Users");
        System.out.println("13. Manage Halls");
        System.out.println("14. View Movies by Category");
        System.out.println("15. Logout");
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

        movies.forEach(movie -> {
            System.out.printf("\nID: %d\n", movie.getId());
            System.out.printf("Title: %s\n", movie.getTitle());
            System.out.printf("Genre: %s\n", movie.getGenre());
            System.out.printf("Category: %s\n", movie.getCategory());
            System.out.printf("Duration: %d minutes\n",
                    movie.getDuration());
            System.out.printf("Rating: %.1f/10\n", movie.getRating());
            System.out.printf("Release Date: %s\n",
                    movie.getReleaseDate());
            System.out.println("-------------");
        });
    }

    private void addNewMovie() throws SQLException {
        if (!isAdmin()) {
            System.out.println("\nAccess Denied! This operation requires ADMIN role.");
            return;
        }

        System.out.println("\n=== ADD NEW MOVIE ===");

        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter duration (minutes): ");
        int duration = getIntInput();

        System.out.print("Enter rating (0-10): ");
        double rating = getDoubleInput();

        System.out.print("Enter release date (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie();

        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setCategory(category);
        movie.setDuration(duration);
        movie.setRating(rating);
        movie.setReleaseDate(releaseDate);

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
            System.out.printf("Price: $%.2f\n", showtime.getPrice());
            System.out.println("-------------");
        }
    }

    private void viewFullShowtimeDetails() throws SQLException {
        System.out.println("\n=== FULL SHOWTIME DETAILS ===");
        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        FullShowtimeDTO showtime = cinemaService.showtimeRepository.getFullShowtimeInfo(showtimeId);

        if (showtime == null) {
            System.out.println("Showtime not found.");
            return;
        }

        System.out.println("\n--- SHOWTIME DETAILS ---");
        System.out.printf("Showtime ID: %d\n", showtime.getShowtimeId());
        System.out.printf("Date: %s\n", showtime.getShowDate());
        System.out.printf("Time: %s\n", showtime.getShowTime());
        System.out.printf("Price: $%.2f\n", showtime.getPrice());
        System.out.println("\n--- MOVIE DETAILS ---");
        System.out.printf("Movie: %s (ID: %d)\n", showtime.getMovieTitle(), showtime.getMovieId());
        System.out.printf("Genre: %s\n", showtime.getMovieGenre());
        System.out.printf("Category: %s\n", showtime.getMovieCategory());
        System.out.printf("Duration: %d minutes\n", showtime.getMovieDuration());
        System.out.printf("Rating: %.1f/10\n", showtime.getMovieRating());
        System.out.println("\n--- HALL DETAILS ---");
        System.out.printf("Hall: %s (ID: %d)\n", showtime.getHallName(), showtime.getHallId());
        System.out.printf("Capacity: %d seats\n", showtime.getHallCapacity());
    }

    private void addNewShowtime() throws SQLException {
        if (!isAdmin()) {
            System.out.println("\nAccess Denied! This operation requires ADMIN role.");

            return;
        }

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
        availableSeats.forEach(ticket ->
                System.out.printf("Ticket ID: %d - Seat: %s\n", ticket.getId(), ticket.getSeatNumber())
        );

        System.out.print("\nEnter ticket ID to book: ");

        int ticketId = getIntInput();

        try {
            boolean success = cinemaService.bookTicket(ticketId, currentUser.getUsername());

            if (success) {
                Ticket bookedTicket = cinemaService.getTicketById(ticketId);
                System.out.println("\nTicket booked successfully!");
                System.out.println("Customer: " + currentUser.getUsername());
                System.out.println("Seat: " + bookedTicket.getSeatNumber());
                System.out.println("Status: BOOKED");

            } else {
                System.out.println("\nFailed to book ticket. Seat may already be booked.");
            }

        } catch (SQLException e) {
            System.err.println("\nError: " + e.getMessage());
        }
    }

    private void cancelBookingMenu() throws SQLException {
        System.out.println("\n=== CANCEL BOOKING ===");
        System.out.print("Enter ticket ID to cancel: ");
        int ticketId = getIntInput();

        Ticket cancelledTicket = cinemaService.cancelBooking(ticketId);

        if (cancelledTicket != null) {
            System.out.println("\nBooking cancelled successfully!");
            System.out.println("Seat: " + cancelledTicket.getSeatNumber());
            System.out.println("Status: NOW AVAILABLE");
            System.out.println("Showtime ID: " + cancelledTicket.getShowtimeId());

        } else {
            System.out.println("\nFailed to cancel. Ticket may not be booked.");
        }
    }

    private void viewAvailableSeats() throws SQLException {
        System.out.println("\n=== AVAILABLE SEATS ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        List<Ticket> availableSeats = cinemaService.getAvailableSeats(showtimeId);
        System.out.printf("\nTotal available seats: %d\n", availableSeats.size());
        availableSeats.forEach(ticket ->
                System.out.printf("Seat: %s (Ticket ID: %d)\n",
                        ticket.getSeatNumber(), ticket.getId())
        );
    }

    private void viewFullTicketDetails() throws SQLException {
        System.out.println("\n=== FULL TICKET DETAILS ===");
        System.out.print("Enter ticket ID: ");
        int ticketId = getIntInput();

        FullTicketInfoDTO ticket = cinemaService.ticketRepository.getFullTicketInfo(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        System.out.println("\n--- TICKET DETAILS ---");
        System.out.printf("Ticket ID: %d\n", ticket.getTicketId());
        System.out.printf("Seat: %s\n", ticket.getSeatNumber());
        System.out.printf("Customer: %s\n", ticket.getCustomerName() != null ? ticket.getCustomerName() : "Not Booked");
        System.out.printf("Status: %s\n", ticket.getIsBooked() ? "BOOKED" : "AVAILABLE");
        System.out.println("\n--- SHOWTIME DETAILS ---");
        System.out.printf("Date: %s\n", ticket.getShowDate());
        System.out.printf("Time: %s\n", ticket.getShowTime());
        System.out.printf("Price: $%.2f\n", ticket.getPrice());
        System.out.println("\n--- MOVIE DETAILS ---");
        System.out.printf("Movie: %s\n", ticket.getMovieTitle());
        System.out.printf("Genre: %s\n", ticket.getMovieGenre());
        System.out.printf("Category: %s\n", ticket.getMovieCategory());
        System.out.printf("Duration: %d minutes\n", ticket.getMovieDuration());
        System.out.println("\n--- HALL DETAILS ---");
        System.out.printf("Hall: %s\n", ticket.getHallName());
        System.out.printf("Capacity: %d seats\n", ticket.getHallCapacity());
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
        if (!isAdmin()) {
            System.out.println("\nAccess Denied! This operation requires ADMIN role.");
            return;
        }

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

        users.forEach(user -> {
            System.out.printf("\nID: %d\n", user.getId());
            System.out.printf("Username: %s\n",
                    user.getUsername());
            System.out.printf("Email: %s\n", user.getEmail());
            System.out.printf("Phone: %s\n", user.getPhone());
            System.out.printf("Role: %s\n", user.getRole());
            System.out.printf("Created: %s\n",
                    user.getCreatedAt());
            System.out.println("-------------");
        });
    }

    private void addNewUser() throws SQLException {
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

    private void deleteUser() throws SQLException {
        System.out.println("\n=== DELETE USER ===");

        System.out.print("Enter user ID to delete: ");
        int userId = getIntInput();

        cinemaService.deleteUser(userId);
        System.out.println("\nUser deleted successfully!");
    }

    private void manageHalls() throws SQLException {
        if (!isAdmin()) {
            System.out.println("\nAccess Denied! This operation requires ADMIN role.");
            return;
        }

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

        halls.forEach(hall -> {
            System.out.printf("\nID: %d\n", hall.getId());
            System.out.printf("Name: %s\n", hall.getName());
            System.out.printf("Capacity: %d seats\n", hall.getCapacity());
            System.out.println("-------------");
        });
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

    private void viewMoviesByCategory() throws SQLException {
        System.out.println("\n=== MOVIES BY CATEGORY ===");
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        List<Movie> movies = cinemaService.getMoviesByCategory(category);

        if (movies.isEmpty()) {
            System.out.println("No movies found in category: " + category);
            return;
        }

        System.out.println("\nMovies in category '" + category + "':");
        movies.forEach(movie -> {
            System.out.printf("\n  ID: %d - %s\n",
                    movie.getId(), movie.getTitle());
            System.out.printf("  Genre: %s | Duration: %d min | Rating: %.1f/10\n",
                    movie.getGenre(), movie.getDuration(), movie.getRating());
        });
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
