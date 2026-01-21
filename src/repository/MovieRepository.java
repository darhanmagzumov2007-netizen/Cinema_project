package repository;

import entity.Movie;
import java.sql.SQLException;
import java.util.List;

public interface MovieRepository {
    Movie save(Movie movie) throws SQLException;
    Movie findById(Integer id) throws SQLException;
    List<Movie> findAll() throws SQLException;
    List<Movie> findByGenre(String genre) throws SQLException;
    void update(Movie movie) throws SQLException;
    void delete(Integer id) throws SQLException;
}
