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

        clearScreen();

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║                 THE ISLAND SUITES                ║");
        System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
        System.out.println("║                                                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║                                                  ║");
        System.out.println("║                    ADMIN LOGIN                   ║");
        System.out.println("║                                                  ║");
        System.out.println("║                                                  ║");
        System.out.print("║  Username: ");
        String username = scanner.nextLine();

        System.out.print("║  Password: ");
        String password = scanner.nextLine();

        System.out.println("║                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        if (logInService.login(username, password)) {

            clearScreen();

            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║                 LOGIN SUCCESSFUL!                ║");
            System.out.println("║                                                  ║");
            System.out.println("║             Welcome, Administrator!              ║");
            System.out.println("║                                                  ║");
            System.out.println("║              THE ISLAND SUITES                   ║");
            System.out.println("║                                                  ║");
            System.out.println("╚══════════════════════════════════════════════════╝");

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();

            return true;

        } else {

            System.out.println("\nInvalid Username or Password!");
            System.out.println("Please check your login credentials.");

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();


            return false;
        }
      
    }
     private void clearScreen() {

        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}

