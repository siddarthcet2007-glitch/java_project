package com.college.eventsystem.ui;

import com.college.eventsystem.repository.UserRepository;
import com.college.eventsystem.service.AuthenticationService;
import com.college.eventsystem.service.UserSession;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {

    private final AuthenticationService authenticationService;

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegistrationFrame(
            AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;

        initializeUI();
    }

    private void initializeUI() {

        setTitle("College Event System - Student Registration");

        setSize(500, 450);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 15, 10, 15);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "Create Student Account",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(
                titleLabel,
                gbc
        );

        // =========================
        // NAME
        // =========================

        JLabel nameLabel =
                new JLabel("Full Name:");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        mainPanel.add(
                nameLabel,
                gbc
        );

        nameField =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                nameField,
                gbc
        );

        // =========================
        // EMAIL
        // =========================

        JLabel emailLabel =
                new JLabel("Email:");

        gbc.gridx = 0;
        gbc.gridy = 2;

        mainPanel.add(
                emailLabel,
                gbc
        );

        emailField =
                new JTextField();

        gbc.gridx = 1;

        mainPanel.add(
                emailField,
                gbc
        );

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel =
                new JLabel("Password:");

        gbc.gridx = 0;
        gbc.gridy = 3;

        mainPanel.add(
                passwordLabel,
                gbc
        );

        passwordField =
                new JPasswordField();

        gbc.gridx = 1;

        mainPanel.add(
                passwordField,
                gbc
        );

        // =========================
        // CONFIRM PASSWORD
        // =========================

        JLabel confirmPasswordLabel =
                new JLabel("Confirm Password:");

        gbc.gridx = 0;
        gbc.gridy = 4;

        mainPanel.add(
                confirmPasswordLabel,
                gbc
        );

        confirmPasswordField =
                new JPasswordField();

        gbc.gridx = 1;

        mainPanel.add(
                confirmPasswordField,
                gbc
        );

        // =========================
        // REGISTER BUTTON
        // =========================

        JButton registerButton =
                new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        mainPanel.add(
                registerButton,
                gbc
        );

        // =========================
        // BACK BUTTON
        // =========================

        JButton backButton =
                new JButton("Back to Login");

        gbc.gridy = 6;

        mainPanel.add(
                backButton,
                gbc
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        registerButton.addActionListener(
                e -> handleRegistration()
        );

        backButton.addActionListener(
                e -> dispose()
        );

        add(mainPanel);
    }

    private void handleRegistration() {

        String name =
                nameField.getText();

        String email =
                emailField.getText();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        // =========================
        // CONFIRM PASSWORD
        // =========================

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =========================
        // REGISTER STUDENT
        // =========================

        boolean registered =
                authenticationService.registerStudent(
                        name,
                        email,
                        password
                );

        if (!registered) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\n"
                            + "Please check your details "
                            + "or make sure the email is not "
                            + "already registered.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =========================
        // SUCCESS
        // =========================

        JOptionPane.showMessageDialog(
                this,
                "Student account created successfully!",
                "Registration Successful",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    public static void main(String[] args) {

        UserRepository userRepository =
                new UserRepository();

        AuthenticationService authenticationService =
                new AuthenticationService(
                        userRepository
                );

        SwingUtilities.invokeLater(
                () -> {

                    RegistrationFrame registrationFrame =
                            new RegistrationFrame(
                                    authenticationService
                            );

                    registrationFrame.setVisible(true);
                }
        );
    }
}