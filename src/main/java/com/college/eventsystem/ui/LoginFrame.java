package com.college.eventsystem.ui;

import com.college.eventsystem.model.User;
import com.college.eventsystem.model.UserRole;
import com.college.eventsystem.repository.UserRepository;
import com.college.eventsystem.service.AuthenticationService;
import com.college.eventsystem.service.UserSession;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthenticationService authenticationService;
    private final UserSession session;

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame(
            AuthenticationService authenticationService,
            UserSession session) {

        this.authenticationService = authenticationService;
        this.session = session;

        initializeUI();
    }

    private void initializeUI() {

        setTitle("College Event System - Login");

        setSize(450, 350);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
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
                        "College Event System",
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
        // EMAIL
        // =========================

        JLabel emailLabel =
                new JLabel("Email:");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

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
        gbc.gridy = 2;

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
        // LOGIN BUTTON
        // =========================

        JButton loginButton =
                new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        mainPanel.add(
                loginButton,
                gbc
        );

        // =========================
        // REGISTER BUTTON
        // =========================

        JButton registerButton =
                new JButton(
                        "Create Student Account"
                );

        gbc.gridy = 4;

        mainPanel.add(
                registerButton,
                gbc
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        loginButton.addActionListener(
                e -> handleLogin()
        );

        registerButton.addActionListener(
                e -> handleRegistration()
        );

        add(mainPanel);
    }

    private void handleLogin() {

        String email =
                emailField.getText();

        String password =
                new String(
                        passwordField.getPassword()
                );

        User user =
                authenticationService.login(
                        email,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Store the authenticated user
        session.login(user);

        System.out.println(
                "========== LOGIN SUCCESS =========="
        );

        System.out.println(
                "User ID: "
                        + user.getUserId()
        );

        System.out.println(
                "Name: "
                        + user.getName()
        );

        System.out.println(
                "Email: "
                        + user.getEmail()
        );

        System.out.println(
                "Role: "
                        + user.getRole()
        );

        // Open the correct dashboard
        openDashboard(user);
    }

    private void openDashboard(User user) {

        dispose();

        if (user.getRole() == UserRole.ADMIN) {

            AdminDashboard adminDashboard =
                    new AdminDashboard(session);

            adminDashboard.setVisible(true);

        } else if (user.getRole() == UserRole.STUDENT) {

            StudentDashboard studentDashboard =
                    new StudentDashboard(session);

            studentDashboard.setVisible(true);
        }
    }

    private void handleRegistration() {

        RegistrationFrame registrationFrame =
                new RegistrationFrame(
                        authenticationService
                );

        registrationFrame.setVisible(true);
    }

    public static void main(String[] args) {

        UserRepository userRepository =
                new UserRepository();

        AuthenticationService authenticationService =
                new AuthenticationService(
                        userRepository
                );

        UserSession session =
                new UserSession();

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