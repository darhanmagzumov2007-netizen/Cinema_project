package repository;

import config.DatabaseConfig;
import entity.Movie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

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

    public Movie findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

        return null;
    }

    public List<Movie> findByGenre(String genre) throws SQLException {
        String sql = "SELECT * FROM movies WHERE genre = ?";
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDuration(rs.getInt("duration"));
                movie.setRating(rs.getDouble("rating"));
                movie.setReleaseDate(rs.getDate("release_date").toLocalDate());
                movies.add(movie);
            }
        }

        return movies;
    }
}

