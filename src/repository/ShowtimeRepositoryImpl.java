package repository;

import config.DatabaseConfig;
import entity.Showtime;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Showtime findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM showtimes WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToShowtime(rs);
            }

            return null;
        }
    }

    @Override
    public List<Showtime> findAll() throws SQLException {
        String sql = "SELECT * FROM showtimes ORDER BY show_date, show_time";
        List<Showtime> showtimes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                showtimes.add(mapResultSetToShowtime(rs));
            }
        }

        return showtimes;
    }

    @Override
    public List<Showtime> findByMovieId(Integer movieId) throws SQLException {
        String sql = "SELECT * FROM showtimes WHERE movie_id = ? ORDER BY show_date, show_time";
        List<Showtime> showtimes = new ArrayList<>();

        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                showtimes.add(mapResultSetToShowtime(rs));
            }
        }

        return showtimes;
    }

    @Override
    public List<Showtime> findByDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM showtimes WHERE show_date = ? ORDER BY show_time";
        List<Showtime> showtimes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                showtimes.add(mapResultSetToShowtime(rs));
            }
        }

        return showtimes;
    }

    @Override
    public void update(Showtime showtime) throws SQLException {
        String sql = "UPDATE showtimes SET movie_id = ?, hall_id = ?, show_date = ?, show_time = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, showtime.getMovieId());
            stmt.setInt(2, showtime.getHallId());
            stmt.setDate(3, Date.valueOf(showtime.getShowDate()));
            stmt.setTime(4, Time.valueOf(showtime.getShowTime()));
            stmt.setDouble(5, showtime.getPrice());
            stmt.setInt(6, showtime.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM showtimes WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Showtime mapResultSetToShowtime(ResultSet rs) throws SQLException {
        Showtime showtime = new Showtime();

        showtime.setId(rs.getInt("id"));
        showtime.setMovieId(rs.getInt("movie_id"));
        showtime.setHallId(rs.getInt("hall_id"));
        showtime.setShowDate(rs.getDate("show_date").toLocalDate());
        showtime.setShowTime(rs.getTime("show_time").toLocalTime());
        showtime.setPrice(rs.getDouble("price"));

        return showtime;
    }
}