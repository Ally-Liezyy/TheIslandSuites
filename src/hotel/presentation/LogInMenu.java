package hotel.presentation;

import java.util.Scanner;
import hotel.service.LogInService;

public class LogInMenu {

    private Scanner scanner;
    private LogInService logInService;

    public LogInMenu(Scanner scanner, LogInService loginService) {
        this.scanner = scanner;
        this.logInService = loginService;
    }

    public boolean display() {

        System.out.println("==============================");
        System.out.println("    HOTEL MANAGEMENT SYSTEM");
        System.out.println("==============================");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (logInService.login(username, password)) {

            System.out.println("\nLogin Successful!");
            System.out.println("Welcome, Administrator!");

            return true;

        } else {

            System.out.println("\nInvalid Username or Password!");

            return false;
        }
    }
}

