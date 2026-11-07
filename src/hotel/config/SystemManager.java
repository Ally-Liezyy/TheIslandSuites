package hotel.config;

import java.util.Scanner;

import hotel.service.LogInService;
import hotel.presentation.LogInMenu;
import hotel.presentation.MainMenu;

public class SystemManager {

    private Scanner scanner;

    // Constructor
    public SystemManager() {
        scanner = new Scanner(System.in);
    }

    // Start the system
    public void start() {

        LogInService logInService = new LogInService();

        LogInMenu logInMenu =
                new LogInMenu(scanner, logInService);

        if (logInMenu.display()) {
            System.out.println("\nOpening Main Menu...");

             MainMenu mainMenu = new MainMenu(scanner);
            mainMenu.display();

        } else {
            System.out.println("\nAccess Denied!");
        }
    }
}
