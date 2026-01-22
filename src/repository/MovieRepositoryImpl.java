package repository;

import config.DatabaseConfig;
import entity.Movie;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    @Override
    public Movie save(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, duration, rating, release_date) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setInt(3, movie.getDuration());
            stmt.setDouble(4, movie.getRating());
            stmt.setDate(5, Date.valueOf(movie.getReleaseDate()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movie.setId(rs.getInt("id"));
            }

            return movie;
        }
    }

    @Override
    public Movie findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMovie(rs);
            }
        }

        return null;
    }

    @Override
    public List<Movie> findAll() throws SQLException {
        String sql = "SELECT * FROM movies ORDER BY id";
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }
        }
        return movies;
    }

    @Override
    public List<Movie> findByGenre(String genre) throws SQLException {
        String sql = "SELECT * FROM movies WHERE genre = ?";
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }
        }

        return movies;
    }

    @Override
    public void update(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, duration = ?, rating = ?, release_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setInt(3, movie.getDuration());
            stmt.setDouble(4, movie.getRating());
            stmt.setDate(5, Date.valueOf(movie.getReleaseDate()));
            stmt.setInt(6, movie.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Movie mapResultSetToMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setGenre(rs.getString("genre"));
        movie.setDuration(rs.getInt("duration"));
        movie.setRating(rs.getDouble("rating"));
        movie.setReleaseDate(rs.getDate("release_date").toLocalDate());

        return movie;
    }
}

