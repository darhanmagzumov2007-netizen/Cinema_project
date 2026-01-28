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
import java.util.regex.Pattern;

public class CinemaService {
    private static CinemaService instance;

    public final MovieRepository movieRepository;
    public final ShowtimeRepository showtimeRepository;
    public final TicketRepository ticketRepository;
    public final UserRepository userRepository;
    public final HallRepository hallRepository;

    private CinemaService() {
        this.movieRepository = new MovieRepositoryImpl();
        this.showtimeRepository = new ShowtimeRepositoryImpl();
        this.ticketRepository = new TicketRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.hallRepository = new HallRepositoryImpl();
    }

    public static CinemaService getInstance() {
        if (instance == null) {
            instance = new CinemaService();
        }

        return instance;
    }

    private void validateEmail(String email) throws IllegalArgumentException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!Pattern.matches(emailPattern, email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePassword(String password) throws IllegalArgumentException {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }

    private void validateUsername(String username) throws IllegalArgumentException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
    }

    private void validatePrice(Double price) throws IllegalArgumentException {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private void validateRating(Double rating) throws IllegalArgumentException {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
    }

    private void validateRole(String role) throws IllegalArgumentException {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }

        if (!role.equals("USER") && !role.equals("ADMIN")) {
            throw new IllegalArgumentException("Role must be either USER or ADMIN");
        }
    }

    public Movie addMovie(Movie movie) throws SQLException {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be empty");
        }

        if (movie.getGenre() == null || movie.getGenre().trim().isEmpty()) {
            throw new IllegalArgumentException("Movie genre cannot be empty");
        }

        if (movie.getCategory() == null || movie.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Movie category cannot be empty");
        }

        validateRating(movie.getRating());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) throws SQLException {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByCategory(String category) throws SQLException {
        return movieRepository
                .findAll()
                .stream()
                .filter(movie -> movie.getCategory() != null &&
                        movie.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public Showtime addShowtime(Showtime showtime) throws SQLException {
        validatePrice(showtime.getPrice());

        return showtimeRepository.save(showtime);
    }

    public List<Showtime> getShowtimesByMovie(Integer movieId) throws SQLException {
        return showtimeRepository.findByMovieId(movieId);
    }

    public List<Showtime> getShowtimesByDate(LocalDate date) throws SQLException {
        return showtimeRepository.findByDate(date);
    }

    public User addUser(User user) throws SQLException {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateRole(user.getRole());

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

    public User login(String username, String password) throws SQLException {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public Hall addHall(Hall hall) throws SQLException {
        if (hall.getName() == null || hall.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Hall name cannot be empty");
        }

        if (hall.getCapacity() == null || hall.getCapacity() <= 0) {
            throw new IllegalArgumentException("Hall capacity must be greater than 0");
        }

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
        return ticketRepository
                .findByShowtimeId(showtimeId)
                .stream()
                .filter(ticket -> !ticket.getIsBooked())
                .toList();
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
