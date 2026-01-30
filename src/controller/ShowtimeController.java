package controller;

import dto.FullShowtimeDTO;
import entity.Showtime;
import service.CinemaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ShowtimeController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public ShowtimeController(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }

    public void viewShowtimesByDate() throws SQLException {
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

    public void viewFullShowtimeDetails() throws SQLException {
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

    public void addNewShowtime() throws SQLException {
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

    public void viewShowtimeRevenue() throws SQLException {
        System.out.println("\n=== SHOWTIME REVENUE ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        double revenue = cinemaService.calculateShowtimeRevenue(showtimeId);
        System.out.printf("\nTotal Revenue: $%.2f\n", revenue);
    }

    public void viewOccupancyRate() throws SQLException {
        System.out.println("\n=== OCCUPANCY RATE ===");

        System.out.print("Enter showtime ID: ");
        int showtimeId = getIntInput();

        double occupancy = cinemaService.calculateOccupancyRate(showtimeId);
        System.out.printf("\nOccupancy Rate: %.2f%%\n", occupancy);
    }

    public void updateShowtime() throws SQLException {
        System.out.println("\n=== UPDATE SHOWTIME ===");

        System.out.print("Enter showtime ID to update: ");
        int showtimeId = getIntInput();

        Showtime showtime = cinemaService.showtimeRepository.findById(showtimeId);
        if (showtime == null) {
            System.out.println("Showtime not found.");
            return;
        }

        System.out.print("Enter new price (or -1 to keep current): ");
        double price = getDoubleInput();
        if (price >= 0) {
            showtime.setPrice(price);
            cinemaService.showtimeRepository.update(showtime);
            System.out.println("\nShowtime updated successfully!");
        }
    }

    public void deleteShowtime() throws SQLException {
        System.out.println("\n=== DELETE SHOWTIME ===");

        System.out.print("Enter showtime ID to delete: ");
        int showtimeId = getIntInput();

        cinemaService.showtimeRepository.delete(showtimeId);
        System.out.println("\nShowtime deleted successfully!");
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
