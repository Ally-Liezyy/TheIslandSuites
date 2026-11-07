package hotel.config;

import java.util.Scanner;

import hotel.service.LogInService;
import hotel.service.RoomService;
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
        RoomService roomService = new RoomService();

        LogInMenu logInMenu =
                new LogInMenu(scanner, logInService);

        if (logInMenu.display()) {
            System.out.println("\nOpening Main Menu...");

             MainMenu mainMenu = new MainMenu(scanner, roomService);
            mainMenu.display();

        } else {
            System.out.println("\nAccess Denied!");
        }
    }
}
