package repository;

import config.DatabaseConfig;
import entity.Movie;

import java.sql.*;

public class MovieRepositoryImpl implements MovieRepository {
}
@Override
public Movie save(Movie movie) throws SQLException {
    String sql = "INSERT INTO movies (title, genre, duration, rating, release date) VALUES(?, ?. ?. ?, ?) RETURNING id";
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getGenre());
        stmt.setInt(3, movie.getDuration());
        stmt.setDouble(4, movie.getRating());

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
    (PreparedStatement stmt = conn.prepareStatement(sql)){
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            return (rs);
        }
        return null;


        @Override
        public List<Movie> findAll() throws SQLException {

            String sql = "SELECT * FROM movies ORDER BY id";
           List<Movie> movies = new ArrayList<>();
           try (Connection conn = DatabaseConfig.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){

               while (rs.next()){
                   movies.add((rs));
               }

           }
           }
    }





}

