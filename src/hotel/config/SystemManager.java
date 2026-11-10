package hotel.config;

import java.util.Scanner;

import hotel.service.LogInService;
import hotel.service.RoomService;
import  hotel.service.CustomerService;
import hotel.presentation.LogInMenu;
import hotel.presentation.MainMenu;
import hotel.service.BookingService;
import hotel.service.PaymentService;

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
        CustomerService customerService = new CustomerService();
        BookingService bookingService = new BookingService(roomService, customerService);
        PaymentService paymentService = new PaymentService(bookingService);

        LogInMenu logInMenu =
                new LogInMenu(scanner, logInService);

        if (logInMenu.display()) {
            System.out.println("\nOpening Main Menu...");

           MainMenu mainMenu = new MainMenu(scanner, roomService, customerService, bookingService, paymentService);
            mainMenu.display();
            
        } else {
            System.out.println("\nAccess Denied!");
        }
    }
}
