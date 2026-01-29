package repository;

import dto.FullShowtimeDTO;
import entity.Showtime;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ShowtimeRepository {
    Showtime save(Showtime showtime) throws SQLException;
    Showtime findById(Integer id) throws SQLException;
    List<Showtime> findAll() throws SQLException;
    List<Showtime> findByMovieId(Integer movieId) throws SQLException;
    List<Showtime> findByDate(LocalDate date) throws SQLException;
    void update(Showtime showtime) throws SQLException;
    void delete(Integer Id) throws SQLException;
FullShowtimeDTO getFullShowtimeInfo(Integer showtimeId) throws SQLException;
List<FullShowtimeDTO> getAllFullshowtimes()throws SQLException;
}
