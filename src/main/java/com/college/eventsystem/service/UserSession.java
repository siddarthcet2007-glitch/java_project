package com.college.eventsystem.service;

import com.college.eventsystem.model.User;
import com.college.eventsystem.model.UserRole;

public class UserSession {

    private User currentUser;

    public void login(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        this.currentUser = user;
    }

    public void logout() {

        this.currentUser = null;
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public boolean isLoggedIn() {

        return currentUser != null;
    }

    public boolean isAdmin() {

        return currentUser != null
                && currentUser.getRole() == UserRole.ADMIN;
    }

    public boolean isStudent() {

        return currentUser != null
                && currentUser.getRole() == UserRole.STUDENT;
    }
}