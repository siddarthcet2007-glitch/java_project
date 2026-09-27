package com.college.eventsystem.repository;

import com.college.eventsystem.model.Admin;
import com.college.eventsystem.model.Student;
import com.college.eventsystem.model.User;
import com.college.eventsystem.model.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public User findByEmail(String email) {

        String sql = """
                SELECT user_id, name, email, password, role
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findById(int userId) {

        String sql = """
                SELECT user_id, name, email, password, role
                FROM users
                WHERE user_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean emailExists(String email) {

        String sql = """
                SELECT user_id
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addUser(User user) {

        String sql = """
                INSERT INTO users
                (name, email, password, role)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            ResultSet generatedKeys =
                    statement.getGeneratedKeys();

            if (generatedKeys.next()) {

                int generatedId =
                        generatedKeys.getInt(1);

                user.setUserId(generatedId);

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id, name, email, password, role
                FROM users
                ORDER BY user_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private User mapUser(ResultSet resultSet)
            throws SQLException {

        int userId =
                resultSet.getInt("user_id");

        String name =
                resultSet.getString("name");

        String email =
                resultSet.getString("email");

        String password =
                resultSet.getString("password");

        UserRole role =
                UserRole.valueOf(
                        resultSet
                                .getString("role")
                                .toUpperCase()
                );

        if (role == UserRole.ADMIN) {

            return new Admin(
                    userId,
                    name,
                    email,
                    password
            );
        }

        return new Student(
                userId,
                name,
                email,
                password
        );
    }
}