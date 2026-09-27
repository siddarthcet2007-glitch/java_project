package com.college.eventsystem;

import com.college.eventsystem.repository.UserRepository;
import com.college.eventsystem.service.AuthenticationService;

public class Main {

    public static void main(String[] args) {

        UserRepository userRepository =
                new UserRepository();

        AuthenticationService authenticationService =
                new AuthenticationService(userRepository);

        System.out.println(
                "========== VALID REGISTRATION =========="
        );

        boolean valid =
                authenticationService.registerStudent(
                        "Validation Test",
                        "validationtest@gmail.com",
                        "1234"
                );

        System.out.println(
                "Valid registration: " + valid
        );

        System.out.println();

        System.out.println(
                "========== EMPTY NAME TEST =========="
        );

        boolean emptyName =
                authenticationService.registerStudent(
                        "",
                        "emptyname@gmail.com",
                        "1234"
                );

        System.out.println(
                "Registration accepted: " + emptyName
        );

        System.out.println();

        System.out.println(
                "========== INVALID EMAIL TEST =========="
        );

        boolean invalidEmail =
                authenticationService.registerStudent(
                        "Invalid Email",
                        "invalid-email",
                        "1234"
                );

        System.out.println(
                "Registration accepted: " + invalidEmail
        );

        System.out.println();

        System.out.println(
                "========== SHORT PASSWORD TEST =========="
        );

        boolean shortPassword =
                authenticationService.registerStudent(
                        "Short Password",
                        "shortpassword@gmail.com",
                        "123"
                );

        System.out.println(
                "Registration accepted: " + shortPassword
        );

        System.out.println();

        System.out.println(
                "========== DUPLICATE EMAIL TEST =========="
        );

        boolean duplicateEmail =
                authenticationService.registerStudent(
                        "Duplicate Test",
                        "validationtest@gmail.com",
                        "5678"
                );

        System.out.println(
                "Registration accepted: " + duplicateEmail
        );
    }
}