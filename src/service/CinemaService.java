package service;

import entity.Movie;
import entity.Showtime;
import entity.Ticket;
import entity.User;
import entity.Hall;
import repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CinemaService {
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final HallRepository hallRepository;

    public CinemaService() {
        this.movieRepository = new MovieRepositoryImpl();
        this.showtimeRepository = new ShowtimeRepositoryImpl();
        this.ticketRepository = new TicketRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.hallRepository = new HallRepositoryImpl();
    }

    public Movie addMovie(Movie movie) throws SQLException {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) throws SQLException {
        return movieRepository.findByGenre(genre);
    }

    public Showtime addShowtime(Showtime showtime) throws SQLException {
        return showtimeRepository.save(showtime);
    }

    public List<Showtime> getShowtimesByMovie(Integer movieId) throws SQLException {
        return showtimeRepository.findByMovieId(movieId);
    }

    public List<Showtime> getShowtimesByDate(LocalDate date) throws SQLException {
        return showtimeRepository.findByDate(date);
    }

    public User addUser(User user) throws SQLException {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) throws SQLException {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Integer id) throws SQLException {
        userRepository.delete(id);
    }

    public Hall addHall(Hall hall) throws SQLException {
        return hallRepository.save(hall);
    }

    public List<Hall> getAllHalls() throws SQLException {
        return hallRepository.findAll();
    }

    public Hall getHallById(Integer id) throws SQLException {
        return hallRepository.findById(id);
    }

    public Ticket getTicketById(Integer id) throws SQLException {
        return ticketRepository.findById(id);
    }

    public boolean bookTicket(Integer ticketId, String username) throws SQLException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new SQLException("User '" + username + "' not found. Please register first.");
        }

        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new SQLException("Ticket not found");
        }

        if (ticket.getIsBooked()) {
            return false;
        }

        ticket.setCustomerName(username);
        ticket.setIsBooked(true);
        ticketRepository.update(ticket);

        return true;
    }

    public Ticket cancelBooking(Integer ticketId) throws SQLException {
        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new SQLException("Ticket not found");
        }

        if (!ticket.getIsBooked()) {
            return null;
        }

        ticket.setCustomerName(null);
        ticket.setIsBooked(false);
        ticketRepository.update(ticket);

        return ticket;
    }

    public List<Ticket> getAvailableSeats(Integer showtimeId) throws SQLException {
        return ticketRepository.findAvailableSeats(showtimeId);
    }

    public List<Ticket> getShowtimeTickets(Integer showtimeId) throws SQLException {
        return ticketRepository.findByShowtimeId(showtimeId);
    }

    public double calculateShowtimeRevenue(Integer showtimeId) throws SQLException {
        Showtime showtime = showtimeRepository.findById(showtimeId);

        if (showtime == null) {
            throw new SQLException("Showtime not found");
        }

        List<Ticket> tickets = ticketRepository.findByShowtimeId(showtimeId);
        long bookedCount = tickets.stream().filter(Ticket::getIsBooked).count();

        return bookedCount * showtime.getPrice();
    }

    public double calculateOccupancyRate(Integer showtimeId) throws SQLException {
        List<Ticket> allTickets = ticketRepository.findByShowtimeId(showtimeId);

        if (allTickets.isEmpty()) {
            return 0.0;
        }

        long bookedCount = allTickets.stream().filter(Ticket::getIsBooked).count();

        return (double) bookedCount / allTickets.size() * 100;
    }

    public void generateTicketsForShowtime(Integer showtimeId, int numberOfSeats) throws SQLException {
        for (int i = 1; i <= numberOfSeats; i++) {

            String seatNumber = String.format("A%d", i);
            Ticket ticket = new Ticket(null, showtimeId, seatNumber, null, false);

            ticketRepository.save(ticket);
        }
    }
}
