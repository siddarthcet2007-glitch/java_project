package com.college.eventsystem.model;

public class Admin extends User {

    public Admin(
            int userId,
            String name,
            String email,
            String password) {

        super(
                userId,
                name,
                email,
                password,
                UserRole.ADMIN
        );
    }
}