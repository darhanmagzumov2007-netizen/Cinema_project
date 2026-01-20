package repository;

import entity.User;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User save(User user) throws SQLException;
    User findById(Integer id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User findByEmail(String email) throws SQLException;
    List<User> findAll() throws SQLException;
    void update(User user) throws SQLException;
    void delete(Integer id) throws SQLException;
}