package repository;

import config.DatabaseConfig;
import dto.FullShowtimeDTO;
import dto.FullTicketInfoDTO;
import entity.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public Ticket save(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (showtime_id, seat_number, customer_name, is_booked) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getShowtimeId());
            stmt.setString(2, ticket.getSeatNumber());
            stmt.setString(3, ticket.getCustomerName());
            stmt.setBoolean(4, ticket.getIsBooked());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ticket.setId(rs.getInt("id"));
            }

            return ticket;
        }
    }

    @Override
    public Ticket findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTicket(rs);
            }

            return null;
        }
    }

    @Override
    public List<Ticket> findAll() throws SQLException {
        String sql = "SELECT * FROM tickets ORDER BY id";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }

        return tickets;
    }

    @Override
    public List<Ticket> findByShowtimeId(Integer showtimeId) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE showtime_id = ? ORDER BY seat_number";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }

        return tickets;
    }

    @Override
    public List<Ticket> findAvailableSeats(Integer showtimeId) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE showtime_id = ? AND is_booked = false ORDER BY seat_number";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }

    @Override
    public void update(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET showtime_id = ?, seat_number = ?, customer_name = ?, is_booked = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getShowtimeId());
            stmt.setString(2, ticket.getSeatNumber());
            stmt.setString(3, ticket.getCustomerName());
            stmt.setBoolean(4, ticket.getIsBooked());
            stmt.setInt(5, ticket.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();

        ticket.setId(rs.getInt("id"));
        ticket.setShowtimeId(rs.getInt("showtime_id"));
        ticket.setSeatNumber(rs.getString("seat_number"));
        ticket.setCustomerName(rs.getString("customer_name"));
        ticket.setIsBooked(rs.getBoolean("is_booked"));

        return ticket;
    }

    @Override
    public FullTicketInfoDTO getFullTicketInfo(Integer ticketId) throws SQLException {
        String sql = "SELECT " +
                "t.id as ticket_id, t.seat_number, t.customer_name, t.booked, " +
                "s.id as showtime_id, s.show_date, s.show_time, s.price, " +
                "m.id as movie_id, m.title, m.genre, m.category, m.duration, " +
                "h.id as hall_id, h.name as hall_name, h.capacity " +
                "FROM tickets t " +
                "JOIN showtimes s ON t.showtime_id = s.id " +
                "JOIN movies m ON s.movie_id = m.id " +
                "JOIN halls h ON s.hall_id = h.id " +
                "WHERE t.id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                return new FullTicketInfoDTO(
                        rs.getInt("ticket_id"),
                        rs.getInt("showtime_id"),
                        rs.getInt("movie_id"),
                        rs.getInt("duration"),
                        rs.getInt("hall_id"),
                        rs.getInt("capacity"),
                        rs.getString("seat_number"),
                        rs.getString("customer_name"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("category"),
                        rs.getString("hall_name"),
                        rs.getBoolean("booked"),
                        rs.getDate("show_date").toLocalDate(),
                        rs.getTime("show_time").toLocalTime()


                );
            }
            return null;
        }

    }


    @Override
    public List<FullShowtimeDTO> getFullTicketInfoByShowtime(Integer showtimeId) throws SQLException {
        return List.of();
    }


}
