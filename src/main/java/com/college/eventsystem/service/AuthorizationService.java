package com.college.eventsystem.service;

import com.college.eventsystem.model.User;
import com.college.eventsystem.model.UserRole;

public class AuthorizationService {

    private final UserSession session;

    public AuthorizationService(UserSession session) {
        this.session = session;
    }

    public boolean canAccessAdminFeatures() {

        return session.isLoggedIn()
                && session.getCurrentUser().getRole() == UserRole.ADMIN;
    }

    public boolean canAccessStudentFeatures() {

        return session.isLoggedIn()
                && session.getCurrentUser().getRole() == UserRole.STUDENT;
    }

    public void requireAdmin() {

        if (!canAccessAdminFeatures()) {
            throw new SecurityException(
                    "Access denied: Admin privileges required."
            );
        }
    }

    public void requireStudent() {

        if (!canAccessStudentFeatures()) {
            throw new SecurityException(
                    "Access denied: Student privileges required."
            );
        }
    }

    public void requireLogin() {

        if (!session.isLoggedIn()) {
            throw new SecurityException(
                    "Access denied: User must be logged in."
            );
        }
    }
}