package repository;

import config.DatabaseConfig;
import entity.Hall;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallRepositoryImpl implements HallRepository {

    @Override
    public Hall save(Hall hall) throws SQLException {
        String sql = "INSERT INTO halls (name, capacity) VALUES (?, ?) RETURNING id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hall.setId(rs.getInt("id"));
            }

            return hall;
        }
    }

    @Override
    public Hall findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM halls WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHall(rs);
            }

            return null;
        }
    }

    @Override
    public Hall findByName(String name) throws SQLException {
        String sql = "SELECT * FROM halls WHERE name = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHall(rs);
            }

            return null;
        }
    }

    @Override
    public List<Hall> findAll() throws SQLException {
        String sql = "SELECT * FROM halls ORDER BY id";
        List<Hall> halls = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                halls.add(mapResultSetToHall(rs));
            }
        }

        return halls;
    }

    @Override
    public void update(Hall hall) throws SQLException {
        String sql = "UPDATE halls SET name = ?, capacity = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());
            stmt.setInt(3, hall.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM halls WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Hall mapResultSetToHall(ResultSet rs) throws SQLException {
        Hall hall = new Hall();

        hall.setId(rs.getInt("id"));
        hall.setName(rs.getString("name"));
        hall.setCapacity(rs.getInt("capacity"));

        return hall;
    }
}
