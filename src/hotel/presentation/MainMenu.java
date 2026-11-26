
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

    // CONSTRUCTOR
    public MainMenu(
            Scanner scanner,
            RoomService roomService,
            CustomerService customerService,
            BookingService bookingService,
            PaymentService paymentService) {

        this.scanner = scanner;
        this.roomService = roomService;
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

    // MAIN MENU
    public void display() {

        int choice;

        do {
            clearScreen();

            header("MAIN MENU");

            System.out.println("║                                                  ║");
            System.out.println("║          [1]  Room Management                    ║");
            System.out.println("║          [2]  Customer Management                ║");
            System.out.println("║          [3]  Booking Management                 ║");
            System.out.println("║          [4]  Payment Management                 ║");
            System.out.println("║          [5]  Logout                             ║");
            System.out.println("║                                                  ║");

            divider();

            choice = readInt("║  Enter your choice: ");

            switch (choice) {

                case 1:
                    clearScreen();

                    System.out.println("Room Management selected.");

                    try {
                        RoomMenu roomMenu = new RoomMenu(
                            scanner,
                            roomService
                        );

                        roomMenu.display();

                    } catch (Exception e) {
                        System.out.println(
                            "\n[ERROR] Unable to open Room Management."
                        );

                        System.out.println(e.getMessage());
                        pauseScreen();
                    }

                    break;

                case 2:
                    clearScreen();

                    System.out.println("Customer Management selected.");

                    try {
                        CustomerMenu customerMenu = new CustomerMenu(
                            scanner,
                            customerService
                        );

                        customerMenu.display();

                    } catch (Exception e) {
                        System.out.println(
                            "\n[ERROR] Unable to open Customer Management."
                        );

                        System.out.println(e.getMessage());
                        pauseScreen();
                    }

                    break;

                case 3:
                    clearScreen();

                    System.out.println("Booking Management selected.");

                    try {
                        BookingMenu bookingMenu = new BookingMenu(
                            scanner,
                            bookingService,
                            paymentService,
                            customerService,
                            roomService
                        );

                        bookingMenu.display();

                    } catch (Exception e) {
                        System.out.println(
                            "\n[ERROR] Unable to open Booking Management."
                        );

                        System.out.println(e.getMessage());
                        pauseScreen();
                    }

                    break;

                case 4:
                    clearScreen();

                    System.out.println("Payment Management selected.");

                    try {
                        PaymentMenu paymentMenu = new PaymentMenu(
                            scanner,
                            paymentService,
                            bookingService,
                            customerService,
                            roomService
                        );

                        paymentMenu.display();

                    } catch (Exception e) {
                        System.out.println(
                            "\n[ERROR] Unable to open Payment Management."
                        );

                        System.out.println(e.getMessage());
                        pauseScreen();
                    }

                    break;

                case 5:
                    clearScreen();

                    System.out.println();

                    header("THANK YOU FOR VISITING!");

                    System.out.println("║                                                  ║");
                    System.out.println("║                 THE ISLAND SUITES                ║");
                    System.out.println("║                                                  ║");

                    footer();

                    break;

                default:
                    System.out.println(
                        "\n[INPUT ERROR] Invalid choice. Please select 1-5."
                    );

                    pauseScreen();
            }

        } while (choice != 5);
    }

    // INTEGER INPUT WITH EXCEPTION HANDLING
    private int readInt(String message) {

        while (true) {

            try {
                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Input cannot be empty."
                    );
                }

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                    "\n[INPUT ERROR] Please enter a valid whole number."
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                    "\n[INPUT ERROR] " + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                    "\n[SYSTEM ERROR] " + e.getMessage()
                );
            }
        }
    }

    // HEADER LAYOUT
    private void header(String title) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║                 THE ISLAND SUITES                ║");
        System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
        System.out.println("║                                                  ║");

        divider();

        System.out.println("║                                                  ║");
        System.out.printf("║                 %-32s ║%n", title);
        System.out.println("║                                                  ║");

        divider();
    }

    // REUSABLE DIVIDER
    private void divider() {
        System.out.println("╠══════════════════════════════════════════════════╣");
    }

    // BOTTOM BORDER
    private void footer() {
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // CLEAR SCREEN
    private void clearScreen() {

        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } catch (Exception e) {

            System.out.println(
                "[SYSTEM ERROR] Unable to clear screen: "
                + e.getMessage()
            );
        }
    }

    // PAUSE SCREEN
    private void pauseScreen() {

        try {
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();

        } catch (Exception e) {

            System.out.println(
                "[SYSTEM ERROR] Unable to pause screen: "
                + e.getMessage()
            );
        }
    }

}
