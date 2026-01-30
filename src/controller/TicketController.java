package controller;

import dto.FullTicketInfoDTO;
import entity.Ticket;
import entity.User;
import service.CinemaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TicketController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public TicketController(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }

    public void bookTicket(User currentUser) throws SQLException {
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

    public void cancelBooking() throws SQLException {
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

    public void viewAvailableSeats() throws SQLException {
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

    public void viewFullTicketDetails() throws SQLException {
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
        System.out.printf("Status: %s\n", ticket.getBooked() ? "BOOKED" : "AVAILABLE");
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

    public void viewMyTickets(User currentUser) throws SQLException {
        System.out.println("\n=== MY TICKETS ===");

        List<Ticket> allTickets = cinemaService.ticketRepository.findAll();

        List<Ticket> myTickets = allTickets.stream()
                .filter(ticket -> ticket.getIsBooked() &&
                        currentUser.getUsername().equals(ticket.getCustomerName()))
                .toList();

        if (myTickets.isEmpty()) {
            System.out.println("You have no booked tickets.");
            return;
        }

        System.out.printf("\nTotal tickets: %d\n", myTickets.size());
        myTickets.forEach(ticket -> {
            System.out.printf("\nTicket ID: %d\n", ticket.getId());
            System.out.printf("Seat: %s\n", ticket.getSeatNumber());
            System.out.printf("Showtime ID: %d\n", ticket.getShowtimeId());
            System.out.println("-------------");
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
}
