package com.college.eventsystem.ui;

import com.college.eventsystem.model.User;
import com.college.eventsystem.repository.UserRepository;
import com.college.eventsystem.service.AuthenticationService;
import com.college.eventsystem.service.UserSession;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private final UserSession session;

    public StudentDashboard(UserSession session) {

        this.session = session;

        initializeUI();
    }

    private void initializeUI() {

        setTitle("College Event System - Student Dashboard");

        setSize(600, 400);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        User user = session.getCurrentUser();

        JPanel mainPanel = new JPanel(
                new BorderLayout()
        );

        JLabel titleLabel =
                new JLabel(
                        "Student Dashboard",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        JPanel informationPanel =
                new JPanel(
                        new GridLayout(3, 1, 10, 10)
                );

        informationPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        50,
                        40,
                        50
                )
        );

        informationPanel.add(
                new JLabel(
                        "Welcome, "
                                + user.getName()
                )
        );

        informationPanel.add(
                new JLabel(
                        "Email: "
                                + user.getEmail()
                )
        );

        informationPanel.add(
                new JLabel(
                        "Role: "
                                + user.getRole()
                )
        );

        mainPanel.add(
                informationPanel,
                BorderLayout.CENTER
        );

        JButton logoutButton =
                new JButton("Logout");

        logoutButton.addActionListener(
                e -> logout()
        );

        mainPanel.add(
                logoutButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void logout() {

        session.logout();

        dispose();

        UserRepository userRepository =
                new UserRepository();

        AuthenticationService authenticationService =
                new AuthenticationService(
                        userRepository
                );

        SwingUtilities.invokeLater(
                () -> {

                    LoginFrame loginFrame =
                            new LoginFrame(
                                    authenticationService,
                                    session
                            );

                    loginFrame.setVisible(true);
                }
        );
    }
}