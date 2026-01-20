package repository;

import entity.Ticket;
import java.sql.SQLException;
import java.util.List;

public interface TicketRepository {
    Ticket save(Ticket ticket) throws  SQLException;
    Ticket findById(Integer Id) throws SQLException;
    List<Ticket> findAll() throws  SQLException;
    List<Ticket> findByShowtimeId(Integer showtimeId) throws SQLException;
    List<Ticket> findAvailableSeats(Integer showtimeId) throws SQLException;
    void update(Ticket ticket) throws SQLException;
    void delete(Integer Id) throws SQLException;
}
