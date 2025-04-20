package tn.esprit.user.services;

import tn.esprit.user.entities.User;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.user.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Userservice {
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO user (email, password, nom, prenom, roles, failed_login_attempts, is_locked, date_naissance, photo, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.setString(3, user.getNom());
            stmt.setString(4, user.getPrenom());
            stmt.setString(5, user.getRole()); // Stores JSON-like string (e.g., ["ROLE_ADMIN"])
            stmt.setInt(6, user.getFailedLoginAttempts());
            stmt.setBoolean(7, user.isLocked());
            stmt.setObject(8, user.getDateNaissance() != null ? Date.valueOf(user.getDateNaissance()) : null);
            stmt.setString(9, user.getPhoto());
            stmt.setString(10, user.getTel());
            stmt.executeUpdate();
        }
    }

    public User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setDateNaissance(rs.getDate("date_naissance") != null ? rs.getDate("date_naissance").toLocalDate() : null);
                user.setPhoto(rs.getString("photo"));
                user.setRole(rs.getString("roles")); // Read from roles column
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                user.setLocked(rs.getBoolean("is_locked"));
                user.setTel(rs.getString("telephone"));

                if (user.isLocked()) {
                    return null;
                }

                if (BCrypt.checkpw(password, user.getPassword())) {
                    resetFailedLoginAttempts(user.getId());
                    return user;
                } else {
                    incrementFailedLoginAttempts(user.getId());
                    if (user.getFailedLoginAttempts() >= 3) {
                        lockUser(user.getId());
                    }
                    return null;
                }
            }
            return null;
        }
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET email = ?, nom = ?, prenom = ?, date_naissance = ?, photo = ?, telephone = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getNom());
            stmt.setString(3, user.getPrenom());
            stmt.setObject(4, user.getDateNaissance() != null ? Date.valueOf(user.getDateNaissance()) : null);
            stmt.setString(5, user.getPhoto());
            stmt.setString(6, user.getTel());
            stmt.setInt(7, user.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setDateNaissance(rs.getDate("date_naissance") != null ? rs.getDate("date_naissance").toLocalDate() : null);
                user.setPhoto(rs.getString("photo"));
                user.setRole(rs.getString("roles"));
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                user.setLocked(rs.getBoolean("is_locked"));
                user.setTel(rs.getString("telephone"));
                users.add(user);
            }
        }
        return users;
    }

    private void incrementFailedLoginAttempts(int userId) throws SQLException {
        String sql = "UPDATE user SET failed_login_attempts = failed_login_attempts + 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    private void resetFailedLoginAttempts(int userId) throws SQLException {
        String sql = "UPDATE user SET failed_login_attempts = 0 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    private void lockUser(int userId) throws SQLException {
        String sql = "UPDATE user SET is_locked = 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}