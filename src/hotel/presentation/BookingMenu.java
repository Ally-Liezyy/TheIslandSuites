
package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import hotel.model.Booking;
import hotel.model.Payment;
import hotel.model.Customer;
import hotel.model.Room;

import hotel.service.CustomerService;
import hotel.service.RoomService;
import hotel.service.BookingService;
import hotel.service.PaymentService;

public class BookingMenu {

    private Scanner scanner;
    private BookingService bookingService;
    private PaymentService paymentService;
    private CustomerService customerService;
    private RoomService roomService;

    // Constructor
    public BookingMenu(Scanner scanner, BookingService bookingService, PaymentService paymentService, CustomerService customerService, RoomService roomService) {

        this.scanner = scanner;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    // BOOKING MENU
    public void display() {

        int choice;

        do {
            clearScreen();
            header("BOOKING MANAGEMENT");

            System.out.println("║                                                  ║");
            System.out.println("║          [1]  Add New Booking                    ║");
            System.out.println("║          [2]  View All Bookings                  ║");
            System.out.println("║          [3]  Search Booking                     ║");
            System.out.println("║          [4]  Update Booking                     ║");
            System.out.println("║          [5]  Cancel Booking                     ║");
            System.out.println("║          [6]  Complete Booking                   ║");
            System.out.println("║          [7]  Back to Main Menu                  ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");

            choice = readInt("║  Enter your choice: ");

            switch (choice) {
                case 1: addBooking(); break;
                case 2: viewBookings(); break;
                case 3: searchBooking(); break;
                case 4: updateBooking(); break;
                case 5: cancelBooking(); break;
                case 6: completeBooking(); break;
                case 7:
                    System.out.println("\nReturning to Main Menu...");
                    break;
                default:
                    System.out.println("\nInvalid choice!");
                    pauseScreen();
            }

        } while (choice != 7);
    }

    // HEADER
    private void header(String title) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║                 THE ISLAND SUITES                ║");
        System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
        System.out.println("║                                                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║                                                  ║");
        System.out.printf("║                 %-32s ║%n", title);
        System.out.println("║                                                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
    }

    private void section(String title) {

        System.out.println("\n==================================================");
        System.out.println("                  " + title);
        System.out.println("==================================================");
    }

    // INPUT METHODS
    private int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a whole number.");
            }
        }
    }

    private double readAmount(String message) {

        while (true) {
            System.out.print(message);

            try {
                double amount = Double.parseDouble(scanner.nextLine());

                if (amount > 0) return amount;

                System.out.println("Amount must be greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount!");
            }
        }
    }

    private boolean confirm(String message) {

        System.out.print("\n" + message + " (Y/N): ");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    // CUSTOMER AND ROOM INFORMATION
    private Customer getCustomer(int customerId) {
        return customerService.findCustomerById(customerId);
    }

    private Room getRoom(int roomNumber) {
        return roomService.searchRoom(roomNumber);
    }

    private String getCustomerName(int customerId) {

        Customer customer = getCustomer(customerId);

        if (customer == null) return "Unknown Customer";

        return customer.getFirstName() + " " + customer.getLastName();
    }

    private double calculateTotal(Booking booking) {

        Room room = getRoom(booking.getRoomNumber());

        if (room == null) return 0;

        long nights = ChronoUnit.DAYS.between(
                booking.getCheckInDate(), booking.getCheckOutDate());

        return room.getPrice() * nights;
    }

    // DISPLAY CUSTOMERS
    private void displayCustomers() {

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              CUSTOMER RECORDS                              ║");
        System.out.println("╠══════════╦══════════════════════╦══════════════════════╦═══════════════════╣");
        System.out.println("║ ID       ║ LAST NAME            ║ FIRST NAME           ║ CONTACT NUMBER    ║");
        System.out.println("╠══════════╬══════════════════════╬══════════════════════╬═══════════════════╣");

        for (Customer c : customerService.getAllCustomers()) {

            System.out.printf("║ %-8d ║ %-20s ║ %-20s ║ %-17s ║%n",
                    c.getCustomerId(),
                    c.getLastName(),
                    c.getFirstName(),
                    c.getContactNumber());
        }

        System.out.println("╚══════════╩══════════════════════╩══════════════════════╩═══════════════════╝");

        System.out.println("Total Customers: " + customerService.getAllCustomers().size());
    }

    // DISPLAY AVAILABLE ROOMS
    private void displayAvailableRooms() {

        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      AVAILABLE ROOM RECORDS                      ║");
        System.out.println("╠════════════╦══════════════════════╦══════════════════╦═══════════╣");
        System.out.println("║ ROOM NO.   ║ ROOM TYPE            ║ PRICE            ║ STATUS    ║");
        System.out.println("╠════════════╬══════════════════════╬══════════════════╬═══════════╣");

        for (Room r : roomService.getAvailableRooms()) {

            System.out.printf("║ %-10d ║ %-20s ║ PHP %-12.2f ║ %-9s ║%n",
                    r.getRoomNumber(),
                    r.getRoomType(),
                    r.getPrice(),
                    "Available");
        }

        System.out.println("╚════════════╩══════════════════════╩══════════════════╩═══════════╝");

        System.out.println("Total Available Rooms: " + roomService.getAvailableRooms().size());
    }

    // PAYMENT INFORMATION
    private Payment getPayment(int bookingId) {

        for (Payment p : paymentService.getAllPayments()) {
            if (p.getBookingId() == bookingId) return p;
        }

        return null;
    }

    private String getPaymentStatus(int bookingId) {

        Payment payment = getPayment(bookingId);

        return payment == null ? "UNPAID" : payment.getStatus();
    }

    // DISPLAY BOOKING DETAILS
    private void displayBooking(Booking b) {

        section("BOOKING DETAILS");

        Room room = getRoom(b.getRoomNumber());

        System.out.println("Booking ID      : " + b.getBookingId());
        System.out.println("Customer ID     : " + b.getCustomerId());
        System.out.println("Customer Name   : " + getCustomerName(b.getCustomerId()));
        System.out.println("Room Number     : " + b.getRoomNumber());

        if (room != null) {
            System.out.println("Room Type       : " + room.getRoomType());
            System.out.printf("Price per Night : PHP %.2f%n", room.getPrice());
        }

        System.out.println("Check-in Date   : " + b.getCheckInDate());
        System.out.println("Check-out Date  : " + b.getCheckOutDate());
        System.out.printf("Total Amount    : PHP %.2f%n", calculateTotal(b));
        System.out.println("Booking Status  : " + b.getStatus());
        System.out.println("Payment Status  : " + getPaymentStatus(b.getBookingId()));

        Payment p = getPayment(b.getBookingId());

        if (p != null) {

            section("PAYMENT DETAILS");

            System.out.println("Payment ID      : " + p.getPaymentId());
            System.out.printf("Amount          : PHP %.2f%n", p.getAmount());
            System.out.println("Payment Method  : " + p.getPaymentMethod());
            System.out.println("Payment Date    : " + p.getPaymentDate());

            if (p.getPaymentMethod().equalsIgnoreCase("Cash")) {
                System.out.printf("Cash Received   : PHP %.2f%n", p.getCashReceived());
                System.out.printf("Change          : PHP %.2f%n", p.getChange());
            }

            System.out.println("Payment Status  : " + p.getStatus());
        }
    }

    // ADD BOOKING
    private void addBooking() {

        clearScreen();
        header("ADD NEW BOOKING");

        int bookingId = bookingService.getNextBookingId();

        System.out.println("║  Generated Booking ID: " + bookingId);
        System.out.println("╚══════════════════════════════════════════════════╝");

        displayCustomers();

        if (customerService.getAllCustomers().isEmpty()) {
            pauseScreen();
            return;
        }

        int customerId = readInt("\nEnter Customer ID: ");
        Customer customer = getCustomer(customerId);

        if (customer == null) {
            System.out.println("Customer not found!");
            pauseScreen();
            return;
        }

        displayAvailableRooms();

        if (roomService.getAvailableRooms().isEmpty()) {
            pauseScreen();
            return;
        }

        int roomNumber = readInt("\nEnter Room Number: ");
        Room room = getRoom(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println("Room not found or unavailable!");
            pauseScreen();
            return;
        }

        section("BOOKING DATES");

        System.out.print("║ Check-in Date  : ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());

        System.out.print("║ Check-out Date : ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = room.getPrice() * nights;

        // PAYMENT OPTION
        section("PAYMENT OPTION");

        System.out.println("[1] Pay Now");
        System.out.println("[2] Pay Later");

        int paymentChoice;

        do {
            paymentChoice = readInt("\nSelect payment option: ");

            if (paymentChoice != 1 && paymentChoice != 2) {
                System.out.println("Invalid choice!");
            }

        } while (paymentChoice != 1 && paymentChoice != 2);

        String method = "Not yet paid";
        double cash = 0;
        double change = 0;

        if (paymentChoice == 1) {

            System.out.printf("\nBooking Amount: PHP %.2f%n", total);

            System.out.println("[1] Cash");
            System.out.println("[2] GCash");
            System.out.println("[3] Card");

            int option;

            do {
                option = readInt("Select payment method: ");

                if (option < 1 || option > 3) {
                    System.out.println("Invalid payment method!");
                }

            } while (option < 1 || option > 3);

            method = option == 1 ? "Cash" : option == 2 ? "GCash" : "Card";

            if (option == 1) {

                do {
                    cash = readAmount("Cash Received: PHP ");

                    if (cash < total) {
                        System.out.println("Insufficient cash!");
                    }

                } while (cash < total);

                change = cash - total;
            }
        }

        // BOOKING SUMMARY
        section("BOOKING SUMMARY");

        System.out.println("Booking ID      : " + bookingId);
        System.out.println("Customer        : " + customer.getFirstName()
                + " " + customer.getLastName());
        System.out.println("Room Number     : " + roomNumber);
        System.out.println("Room Type       : " + room.getRoomType());
        System.out.printf("Price per Night : PHP %.2f%n", room.getPrice());
        System.out.println("Check-in        : " + checkIn);
        System.out.println("Check-out       : " + checkOut);
        System.out.println("Number of Nights: " + nights);
        System.out.printf("Total Amount    : PHP %.2f%n", total);
        System.out.println("Payment Method  : " + method);

        if (paymentChoice == 1 && method.equals("Cash")) {
            System.out.printf("Cash Received   : PHP %.2f%n", cash);
            System.out.printf("Change          : PHP %.2f%n", change);
        }

        System.out.println("Payment Status  : "
                + (paymentChoice == 1 ? "PAID" : "UNPAID"));

        if (!confirm("Confirm this booking?")) {
            System.out.println("Booking was not saved.");
            pauseScreen();
            return;
        }

        Booking booking = new Booking(
                bookingId, customerId, roomNumber, checkIn, checkOut);

        if (!bookingService.addBooking(booking)) {
            System.out.println("Unable to save booking!");
            pauseScreen();
            return;
        }

        if (paymentChoice == 1) {

            Payment payment = new Payment(
                    paymentService.getNextPaymentId(),
                    bookingId, total, method, LocalDate.now(), cash, change);

            if (paymentService.addPayment(payment)) {
                System.out.println("\n[SUCCESS] Booking and payment saved!");
            } else {
                System.out.println("\n[WARNING] Booking saved, but payment failed.");
            }

        } else {
            System.out.println("\n[SUCCESS] Booking saved!");
            System.out.println("Payment can be settled later.");
        }

        pauseScreen();
    }

    // VIEW ALL BOOKINGS
    private void viewBookings() {

        clearScreen();
        header("ALL BOOKINGS");

        ArrayList<Booking> bookings = bookingService.getAllBookings();

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                        BOOKING RECORDS                                                                ║");
        System.out.println("╠══════════╦══════════════════════╦═══════════╦══════════════╦══════════════╦════════════════╦════════════╦════════════════╣");
        System.out.println("║ BOOKING  ║ CUSTOMER NAME        ║ ROOM NO.  ║ CHECK-IN     ║ CHECK-OUT    ║ TOTAL          ║ STATUS     ║ PAYMENT        ║");
        System.out.println("╠══════════╬══════════════════════╬═══════════╬══════════════╬══════════════╬════════════════╬════════════╬════════════════╣");

        for (Booking b : bookings) {

            System.out.printf("║ %-8d ║ %-20s ║ %-9d ║ %-12s ║ %-12s ║ PHP %-10.2f ║ %-10s ║ %-14s ║%n",
                    b.getBookingId(),
                    getCustomerName(b.getCustomerId()),
                    b.getRoomNumber(),
                    b.getCheckInDate(),
                    b.getCheckOutDate(),
                    calculateTotal(b),
                    b.getStatus(),
                    getPaymentStatus(b.getBookingId()));
        }

        System.out.println("╚══════════╩══════════════════════╩═══════════╩══════════════╩══════════════╩════════════════╩════════════╩════════════════╝");

        System.out.println("\nTotal Bookings: " + bookings.size());

        pauseScreen();
    }

    // SEARCH BOOKING
    private void searchBooking() {

        clearScreen();
        header("SEARCH BOOKING");

        int id = readInt("║  Enter Booking ID: ");
        Booking b = bookingService.searchBooking(id);

        if (b != null) {
            displayBooking(b);
        } else {
            System.out.println("\nBooking not found!");
        }

        pauseScreen();
    }

    // UPDATE BOOKING
    private void updateBooking() {

        clearScreen();
        header("UPDATE BOOKING");

        int id = readInt("║  Enter Booking ID: ");
        Booking b = bookingService.searchBooking(id);

        if (b == null || !b.getStatus().equals("ACTIVE")) {
            System.out.println("\nActive booking not found!");
            pauseScreen();
            return;
        }

        displayBooking(b);

        if (!confirm("Do you want to update this booking?")) {
            System.out.println("No changes were made.");
            pauseScreen();
            return;
        }

        displayCustomers();

        int customerId = readInt("\nNew Customer ID: ");

        if (getCustomer(customerId) == null) {
            System.out.println("Customer not found!");
            pauseScreen();
            return;
        }

        // ROOM SELECTION
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         ROOM SELECTION                            ║");
        System.out.println("╠════════════╦══════════════════════╦══════════════════╦════════════╣");
        System.out.println("║ ROOM NO.   ║ ROOM TYPE            ║ PRICE            ║ STATUS     ║");
        System.out.println("╠════════════╬══════════════════════╬══════════════════╬════════════╣");

        for (Room r : roomService.getAllRooms()) {

            if (r.isAvailable() || r.getRoomNumber() == b.getRoomNumber()) {

                String status = r.getRoomNumber() == b.getRoomNumber()
                        ? "CURRENT ROOM" : "AVAILABLE";

                System.out.printf("║ %-10d ║ %-20s ║ PHP %-12.2f ║ %-10s ║%n",
                        r.getRoomNumber(),
                        r.getRoomType(),
                        r.getPrice(),
                        status);
            }
        }

        System.out.println("╚════════════╩══════════════════════╩══════════════════╩════════════╝");

        int roomNumber = readInt("\nNew Room Number: ");

        System.out.print("New Check-in Date  : ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());

        System.out.print("New Check-out Date : ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        if (!confirm("Confirm booking update?")) {
            System.out.println("Update cancelled.");
            pauseScreen();
            return;
        }

        if (bookingService.updateBooking(id, customerId, roomNumber,
                checkIn, checkOut)) {

            System.out.println("\n[SUCCESS] Booking successfully updated!");
            System.out.println("Please check the payment amount if the total changed.");

        } else {
            System.out.println("\n[ERROR] Unable to update booking.");
        }

        pauseScreen();
    }

    // CANCEL BOOKING
    private void cancelBooking() {

        clearScreen();
        header("CANCEL BOOKING");

        int id = readInt("║  Enter Booking ID: ");
        Booking b = bookingService.searchBooking(id);

        if (b == null || !b.getStatus().equals("ACTIVE")) {
            System.out.println("\nActive booking not found!");
            pauseScreen();
            return;
        }

        displayBooking(b);

        if (confirm("Are you sure you want to cancel this booking?")) {

            if (bookingService.cancelBooking(id)) {
                System.out.println("\n[SUCCESS] Booking cancelled!");
                System.out.println("Room " + b.getRoomNumber() + " is now available.");
            } else {
                System.out.println("\nUnable to cancel booking!");
            }

        } else {
            System.out.println("Cancellation stopped.");
        }

        pauseScreen();
    }

    // COMPLETE BOOKING
    private void completeBooking() {

        clearScreen();
        header("COMPLETE BOOKING");

        int id = readInt("║  Enter Booking ID: ");
        Booking b = bookingService.searchBooking(id);

        if (b == null || !b.getStatus().equals("ACTIVE")) {
            System.out.println("\nActive booking not found!");
            pauseScreen();
            return;
        }

        displayBooking(b);

        if (confirm("Are you sure you want to complete this booking?")) {

            if (bookingService.completeBooking(id)) {
                System.out.println("\n[SUCCESS] Booking completed!");
                System.out.println("Room " + b.getRoomNumber() + " is now available.");
            } else {
                System.out.println("\nUnable to complete booking!");
            }

        } else {
            System.out.println("Completion cancelled.");
        }

        pauseScreen();
    }

    // CLEAR SCREEN
    private void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // PAUSE SCREEN
    private void pauseScreen() {

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

}
