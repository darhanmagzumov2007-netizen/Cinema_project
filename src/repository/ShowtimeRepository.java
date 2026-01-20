package repository;

import entity.Showtime;
import java.sql.SQLException;

public interface ShowtimeRepository {
    Showtime save(Showtime showtime) throws SQLException;
    Showtime findById(Integer id) throws SQLException;
    List<Showtime> findAll() throws SQLException;
    void update(Showtime showtime) throws SQLException;
    void delete(Integer Id) throws SQLException;
}
