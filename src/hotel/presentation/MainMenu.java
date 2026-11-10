package hotel.presentation;

import java.util.Scanner;

import hotel.service.RoomService;
import hotel.service.CustomerService;
import hotel.service.BookingService;

public class MainMenu {

    private Scanner scanner;
    private RoomService roomService;
    private CustomerService customerService;
    private BookingService bookingService;
    

   public MainMenu(Scanner scanner, RoomService roomService, CustomerService customerService, BookingService bookingService) {

    this.scanner = scanner;
    this.roomService = roomService;
    this.customerService = customerService;
    this.bookingService = bookingService;
}

    public void display() {

        int choice;

        do {
            System.out.println("\n===== THE ISLAND SUITES =====");
            System.out.println("1. Room Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Booking Management");
            System.out.println("4. Payment Management");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Room Management selected.");

                    RoomMenu roomMenu = new RoomMenu(scanner, roomService);
                    roomMenu.display();

                    break;

                case 2:
                    System.out.println("Customer Management selected.");
                    CustomerMenu customerMenu = new CustomerMenu(scanner, customerService);
                    customerMenu.display();
                    break;

               case 3:
                    BookingMenu bookingMenu = new BookingMenu(scanner, bookingService);
                    bookingMenu.display();
                    break;
                case 4:
                    System.out.println("Payment Management selected.");
                    break;

                case 5:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }
}