package repository;

import config.DatabaseConfig;
import entity.Showtime;
import java.sql.*;


public class ShowtimeRepositoryImpl implements ShowtimeRepository {

    @Override
    public Showtime save(Showtime showtime) throws SQLException {
        String sql = "INSERT INTO showtimes (movie_id, hall_id, show_date, show_time, price) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtime.getMovieId());
            stmt.setInt(2, showtime.getHallId());
            stmt.setDate(3, Date.valueOf(showtime.getShowDate()));
            stmt.setTime(4, Time.valueOf(showtime.getShowTime()));
            stmt.setDouble(5, showtime.getPrice());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                showtime.setId(rs.getInt("id"));
            }

            return showtime;

            }
        }

    }
}
