package School_Management_System.DAO.impl;

import School_Management_System.DAO.UserDAO;
import School_Management_System.DataBase_Connection.DBConnection;
import School_Management_System.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    // ✅ UPDATED QUERIES
    private static final String SELECT_BY_USERNAME =
            "SELECT id, username, plain_password, role, linked_person_id, full_name, email, phone FROM users WHERE username = ?";

    private static final String INSERT_USER =
            "INSERT INTO users (username, plain_password, role, linked_person_id, full_name, email, phone) VALUES (?,?,?,?::user_role,?,?,?,?)";

    private static final String UPDATE_ROLE =
            "UPDATE users SET role = ?::user_role WHERE username = ?";

    private static final String DELETE_BY_USERNAME =
            "DELETE FROM users WHERE username = ?";

    private static final String SELECT_ALL =
            "SELECT id, username, plain_password, role, linked_person_id, full_name, email, phone FROM users ORDER BY username";

    @Override
    public User findByUsername(String username) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("plain_password"),
                            User.Role.valueOf(rs.getString("role")),
                            rs.getString("linked_person_id"),
                            rs.getString("full_name"),   // ✅ NEW
                            rs.getString("email"),       // ✅ NEW
                            rs.getString("phone")        // ✅ NEW
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public User create(User user) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getplainPassword());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getLinkedPersonId());
            ps.setString(5, user.getFullName());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPhone());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }

            return user;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateRole(String username, User.Role role) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_ROLE)) {

            ps.setString(1, role.name());
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteByUsername(String username) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_BY_USERNAME)) {

            ps.setString(1, username);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> listAll() {
        List<User> out = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                out.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("plain_password"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getString("linked_person_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return out;
    }
}
