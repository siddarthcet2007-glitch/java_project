package com.college.eventsystem.service;

import com.college.eventsystem.model.Admin;
import com.college.eventsystem.model.Student;
import com.college.eventsystem.model.User;
import com.college.eventsystem.model.UserRole;
import com.college.eventsystem.repository.UserRepository;

public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {

        if (email == null || email.isBlank()) {
            return null;
        }

        if (password == null || password.isBlank()) {
            return null;
        }

        email = email.trim().toLowerCase();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public boolean registerStudent(
            String name,
            String email,
            String password) {

        if (!isValidName(name)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        name = name.trim();
        email = email.trim().toLowerCase();

        if (userRepository.emailExists(email)) {
            return false;
        }

        Student student = new Student(
                0,
                name,
                email,
                password
        );

        return userRepository.addUser(student);
    }

    public boolean createAdmin(
            String name,
            String email,
            String password) {

        if (!isValidName(name)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        name = name.trim();
        email = email.trim().toLowerCase();

        if (userRepository.emailExists(email)) {
            return false;
        }

        Admin admin = new Admin(
                0,
                name,
                email,
                password
        );

        return userRepository.addUser(admin);
    }

    public boolean isAdmin(User user) {

        return user != null
                && user.getRole() == UserRole.ADMIN;
    }

    public boolean isStudent(User user) {

        return user != null
                && user.getRole() == UserRole.STUDENT;
    }

    private boolean isValidName(String name) {

        return name != null
                && !name.isBlank();
    }

    private boolean isValidEmail(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return email.trim().matches(emailRegex);
    }

    private boolean isValidPassword(String password) {

        return password != null
                && password.length() >= 4;
    }
}