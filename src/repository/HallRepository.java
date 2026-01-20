package repository;

import entity.Hall;
import java.sql.SQLException;
import java.util.List;


public interface HallRepository {
    Hall save(Hall hall) throws SQLException;
    Hall findById(Integer id) throws SQLException;
    Hall findByName(String name) throws SQLException;
    List<Hall> findAll() throws SQLException;
    void update(Hall hall) throws SQLException;
    void delete(Integer id) throws SQLException;
}
