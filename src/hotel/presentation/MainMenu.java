package hotel.presentation;

import java.util.Scanner;

import hotel.service.RoomService;
import hotel.service.CustomerService;
import hotel.service.BookingService;
import hotel.service.PaymentService;

public class MainMenu {

    private Scanner scanner;
    private RoomService roomService;
    private CustomerService customerService;
    private BookingService bookingService;
    private PaymentService paymentService;
    

  public MainMenu(Scanner scanner, RoomService roomService, CustomerService customerService, BookingService bookingService, PaymentService paymentService)
  {

    this.scanner = scanner;
    this.roomService = roomService;
    this.customerService = customerService;
    this.bookingService = bookingService;
    this.paymentService = paymentService;
  }


    public void display() {

        int choice;

        do {
           clearScreen();

            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║                 THE ISLAND SUITES                ║");
            System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║                                                  ║");
            System.out.println("║                    MAIN MENU                     ║");
            System.out.println("║                                                  ║");
            System.out.println("║          [1]  Room Management                    ║");
            System.out.println("║          [2]  Customer Management                ║");
            System.out.println("║          [3]  Booking Management                 ║");
            System.out.println("║          [4]  Payment Management                 ║");
            System.out.println("║          [5]  Logout                             ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║                                                  ║");
            System.out.print("║  Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                   clearScreen();
                    System.out.println("Room Management selected.");

                    RoomMenu roomMenu = new RoomMenu(scanner, roomService);
                    roomMenu.display();

                    break;

                case 2:
                    clearScreen();
                    System.out.println("Customer Management selected.");
                    CustomerMenu customerMenu = new CustomerMenu(scanner, customerService);
                    customerMenu.display();
                    break;

               case 3:
                    clearScreen();
                    BookingMenu bookingMenu = new BookingMenu(scanner, bookingService, paymentService, customerService, roomService);
                    bookingMenu.display();
                    break;
                case 4:
                    clearScreen();
                    PaymentMenu paymentMenu = new PaymentMenu( scanner, paymentService, bookingService, customerService, roomService);
                    paymentMenu.display();
                    break;
                case 5:
                   clearScreen();
                    System.out.println();
                    System.out.println("╔══════════════════════════════════════════════════╗");
                    System.out.println("║                                                  ║");
                    System.out.println("║             THANK YOU FOR VISITING!              ║");
                    System.out.println("║                  THE ISLAND SUITES               ║");
                    System.out.println("║                                                  ║");
                    System.out.println("╚══════════════════════════════════════════════════╝");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }

    //CLEAR SCREEN
    private void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}