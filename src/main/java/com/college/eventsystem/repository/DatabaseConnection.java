package com.college.eventsystem.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/college_event_system";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            System.getenv("COLLEGE_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "COLLEGE_DB_PASSWORD environment variable is not set."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}