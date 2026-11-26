
package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

import hotel.model.Payment;
import hotel.model.Booking;
import hotel.model.Customer;
import hotel.model.Room;

import hotel.service.PaymentService;
import hotel.service.BookingService;
import hotel.service.CustomerService;
import hotel.service.RoomService;

public class PaymentMenu {

    private Scanner scanner;
    private PaymentService paymentService;
    private BookingService bookingService;
    private CustomerService customerService;
    private RoomService roomService;

    // CONSTRUCTOR
    public PaymentMenu(Scanner scanner, PaymentService paymentService,
            BookingService bookingService, CustomerService customerService,
            RoomService roomService) {

        this.scanner = scanner;
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    // MAIN MENU
    public void display() {

        int choice;

        do {
            clearScreen();

            header("PAYMENT MANAGEMENT");

            displayBlankLine();
            System.out.println("║          [1] Add New Payment                     ║");
            System.out.println("║          [2] View All Payments                   ║");
            System.out.println("║          [3] Search Payment                      ║");
            System.out.println("║          [4] Update Payment                      ║");
            System.out.println("║          [5] Delete Payment                      ║");
            System.out.println("║          [6] Back to Main Menu                   ║");
            displayBlankLine();
            displayBottomBorder();

            choice = readInt("║ Enter your choice: ");

            switch (choice) {

                case 1:
                    addPayment();
                    break;

                case 2:
                    viewPayments();
                    break;

                case 3:
                    searchPayment();
                    break;

                case 4:
                    updatePayment();
                    break;

                case 5:
                    deletePayment();
                    break;

                case 6:
                    System.out.println("\nReturning to Main Menu...");
                    break;

                default:
                    System.out.println("\nInvalid choice!");
                    pauseScreen();
            }

        } while (choice != 6);
    }

    // HEADER
    private void header(String title) {

        displayTopBorder();
        displayBlankLine();
        displayCenteredText("THE ISLAND SUITES");
        displayCenteredText("LUXURY HOTEL MANAGEMENT");
        displayBlankLine();
        displayMiddleBorder();
        displayBlankLine();
        displayCenteredText(title);
        displayBlankLine();
        displayMiddleBorder();
    }

    // ADDITIONAL INPUT METHODS

    // INTEGER INPUT
    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid input! Enter a whole number.");

            } catch (Exception e) {

                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    // AMOUNT INPUT
    private double readAmount(String message) {

        while (true) {

            System.out.print(message);

            try {

                double amount = Double.parseDouble(scanner.nextLine());

                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }

                System.out.println("Amount must be greater than zero.");

            } catch (NumberFormatException e) {

                System.out.println("Invalid amount! Please enter a valid amount.");

            } catch (Exception e) {

                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    // DATE INPUT
    private LocalDate readDate(String message) {

        while (true) {

            System.out.print(message);

            try {

                return LocalDate.parse(scanner.nextLine());

            } catch (DateTimeParseException e) {

                System.out.println("Invalid date! Use YYYY-MM-DD format.");

            } catch (Exception e) {

                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    // CONFIRMATION
    private boolean confirm(String message) {

        System.out.print("\n" + message + " (Y/N): ");

        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    // PAYMENT METHOD
    private String getMethod() {

        System.out.println("\n[1] Cash");
        System.out.println("[2] GCash");
        System.out.println("[3] Card");

        int option;

        do {

            option = readInt("Select Payment Method: ");

            if (option < 1 || option > 3) {
                System.out.println("Invalid payment method!");
            }

        } while (option < 1 || option > 3);

        return option == 1 ? "Cash" : option == 2 ? "GCash" : "Card";
    }

    // GET CUSTOMER
    private Customer getCustomer(int id) {
        return customerService.findCustomerById(id);
    }

    // GET ROOM
    private Room getRoom(int number) {
        return roomService.searchRoom(number);
    }

    // GET PAYMENT BY BOOKING ID
    private Payment getPaymentByBookingId(int id) {

        for (Payment p : paymentService.getAllPayments()) {

            if (p.getBookingId() == id) {
                return p;
            }
        }

        return null;
    }

    // DISPLAY BOOKING INFORMATION
    private void displayBookingInformation(Booking b) {

        Customer c = getCustomer(b.getCustomerId());
        Room r = getRoom(b.getRoomNumber());

        displayTitle("BOOKING INFORMATION");

        System.out.println("║ Booking ID      : " + b.getBookingId());
        System.out.println("║ Customer ID     : " + b.getCustomerId());

        if (c != null) {
            System.out.println("║ Customer Name   : " + c.getFirstName() + " " + c.getLastName());
            System.out.println("║ Contact Number  : " + c.getContactNumber());
        }

        System.out.println("║ Room Number     : " + b.getRoomNumber());

        if (r != null) {

            System.out.println("║ Room Type       : " + r.getRoomType());
            System.out.printf("║ Price per Night : PHP %.2f%n", r.getPrice());

            long nights = ChronoUnit.DAYS.between(
                    b.getCheckInDate(), b.getCheckOutDate());

            System.out.printf("║ Booking Amount  : PHP %.2f%n", r.getPrice() * nights);
        }

        System.out.println("║ Check-in Date   : " + b.getCheckInDate());
        System.out.println("║ Check-out Date  : " + b.getCheckOutDate());
        System.out.println("║ Booking Status  : " + b.getStatus());

        displayBottomBorder();
    }

    // DISPLAY PAYMENT RECEIPT
    private void displayReceipt(Payment p) {

        Booking b = bookingService.searchBooking(p.getBookingId());

        displayTopBorder();
        displayBlankLine();
        displayCenteredText("THE ISLAND SUITES");
        displayCenteredText("PAYMENT RECEIPT");
        displayBlankLine();
        displayMiddleBorder();

        System.out.println("║ Payment ID      : " + p.getPaymentId());
        System.out.println("║ Booking ID      : " + p.getBookingId());

        if (b != null) {

            Customer c = getCustomer(b.getCustomerId());
            Room r = getRoom(b.getRoomNumber());

            System.out.println("║ Customer ID     : " + b.getCustomerId());

            if (c != null) {
                System.out.println("║ Customer Name   : " + c.getFirstName() + " " + c.getLastName());
                System.out.println("║ Contact Number  : " + c.getContactNumber());
            }

            System.out.println("║ Room Number     : " + b.getRoomNumber());

            if (r != null) {
                System.out.println("║ Room Type       : " + r.getRoomType());
            }

            System.out.println("║ Check-in Date   : " + b.getCheckInDate());
            System.out.println("║ Check-out Date  : " + b.getCheckOutDate());
        }

        displayMiddleBorder();

        System.out.printf("║ Amount          : PHP %.2f%n", p.getAmount());
        System.out.println("║ Payment Method  : " + p.getPaymentMethod());
        System.out.println("║ Payment Date    : " + p.getPaymentDate());
        System.out.println("║ Payment Status  : " + p.getStatus());

        displayMiddleBorder();
        displayCenteredText("THANK YOU FOR YOUR PAYMENT!");
        displayBlankLine();
        displayBottomBorder();
    }

    // ADD NEW PAYMENT
    private void addPayment() {

        clearScreen();

        try {

            header("ADD NEW PAYMENT");

            int bookingId = readInt("║ Enter Booking ID: ");

            Booking booking = bookingService.searchBooking(bookingId);

            if (booking == null) {

                displayErrorMessage("BOOKING NOT FOUND!");
                pauseScreen();
                return;
            }

            Payment existing = getPaymentByBookingId(bookingId);

            if (existing != null) {

                displayErrorMessage("THIS BOOKING ALREADY HAS A PAYMENT!");

                System.out.println("║ Payment Status: " + existing.getStatus());

                pauseScreen();
                return;
            }

            displayBookingInformation(booking);

            displayTitle("PAYMENT DETAILS");

            double amount = readAmount("║ Enter Payment Amount: PHP ");

            String method = getMethod();

            LocalDate date = readDate("║ Payment Date (YYYY-MM-DD): ");

            displayBottomBorder();

            System.out.println("\nPAYMENT SUMMARY");
            System.out.println("Booking ID     : " + bookingId);
            System.out.printf("Payment Amount : PHP %.2f%n", amount);
            System.out.println("Payment Method : " + method);
            System.out.println("Payment Date   : " + date);

            if (!confirm("Confirm this payment?")) {

                System.out.println("Payment was not saved.");
                pauseScreen();
                return;
            }

            Payment payment = new Payment(
                    paymentService.getNextPaymentId(),
                    bookingId,
                    amount,
                    method,
                    date
            );

            if (paymentService.addPayment(payment)) {

                System.out.println("\n[SUCCESS] Payment successfully added!");
                displayReceipt(payment);

            } else {

                System.out.println("\n[ERROR] Unable to add payment!");
            }

        } catch (Exception e) {

            displayError("adding payment", e);
        }

        pauseScreen();
    }

    // VIEW ALL PAYMENTS
    private void viewPayments() {

        clearScreen();

        try {

            header("ALL PAYMENTS");

            ArrayList<Booking> bookings = bookingService.getAllBookings();

            if (bookings.isEmpty()) {

                displayErrorMessage("NO BOOKINGS FOUND!");

                pauseScreen();
                return;
            }

            System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            displayCenteredText("PAYMENT RECORDS");
            System.out.println("╠══════════╦══════════╦══════════════════════╦════════════════╦════════════════╦════════════════╦═══════════════╣");

            System.out.println("║ PAY ID   ║ BOOKING  ║ CUSTOMER NAME        ║ TOTAL AMOUNT   ║ PAID AMOUNT    ║ BALANCE        ║ STATUS        ║");

            System.out.println("╠══════════╬══════════╬══════════════════════╬════════════════╬════════════════╬════════════════╬═══════════════╣");

            int paidCount = 0;
            int unpaidCount = 0;
            int cancelledCount = 0;

            double totalAmount = 0;
            double totalPaid = 0;
            double totalBalance = 0;

            for (Booking b : bookings) {

                Customer c = getCustomer(b.getCustomerId());
                Room r = getRoom(b.getRoomNumber());

                String name = c == null
                        ? "Unknown"
                        : c.getFirstName() + " " + c.getLastName();

                double bookingAmount = 0;

                if (r != null) {

                    long nights = ChronoUnit.DAYS.between(
                            b.getCheckInDate(), b.getCheckOutDate());

                    bookingAmount = r.getPrice() * nights;
                }

                Payment p = getPaymentByBookingId(b.getBookingId());

                double paidAmount = p == null ? 0 : p.getAmount();
                double balance = bookingAmount - paidAmount;

                String status;

                if (b.getStatus().equals("CANCELLED")) {

                    status = "CANCELLED";
                    cancelledCount++;

                } else if (p == null) {

                    status = "UNPAID";
                    unpaidCount++;

                } else if (paidAmount >= bookingAmount) {

                    status = "PAID";
                    paidCount++;
                    balance = 0;

                } else {

                    status = "UNDERPAID";
                    unpaidCount++;
                }

                if (balance < 0) {
                    balance = 0;
                }

                totalAmount += bookingAmount;
                totalPaid += paidAmount;
                totalBalance += balance;

                String payId = p == null
                        ? "-"
                        : String.valueOf(p.getPaymentId());

                System.out.printf(
                    "║ %-8s ║ %-8d ║ %-20s ║ PHP %-10.2f ║ PHP %-10.2f ║ PHP %-10.2f ║ %-13s ║%n",
                    payId,
                    b.getBookingId(),
                    name,
                    bookingAmount,
                    paidAmount,
                    balance,
                    status
                );
            }

            System.out.println("╚══════════╩══════════╩══════════════════════╩════════════════╩════════════════╩════════════════╩═══════════════╝");

            displayTitle("PAYMENT SUMMARY");

            System.out.println("║ Total Bookings      : " + bookings.size());
            System.out.println("║ Paid Bookings       : " + paidCount);
            System.out.println("║ Unpaid Bookings     : " + unpaidCount);
            System.out.println("║ Cancelled Bookings  : " + cancelledCount);

            System.out.printf("║ Total Amount        : PHP %.2f%n", totalAmount);
            System.out.printf("║ Total Paid          : PHP %.2f%n", totalPaid);
            System.out.printf("║ Total Balance       : PHP %.2f%n", totalBalance);

            displayBottomBorder();

        } catch (Exception e) {

            displayError("retrieving payment records", e);
        }

        pauseScreen();
    }

    // SEARCH PAYMENT
    private void searchPayment() {

        clearScreen();

        try {

            header("SEARCH PAYMENT");

            int id = readInt("║ Enter Payment ID: ");

            Payment p = paymentService.searchPayment(id);

            if (p != null) {

                displayReceipt(p);

            } else {

                displayErrorMessage("PAYMENT NOT FOUND!");
            }

        } catch (Exception e) {

            displayError("searching payment", e);
        }

        pauseScreen();
    }

    // UPDATE PAYMENT
    private void updatePayment() {

        clearScreen();

        try {

            header("UPDATE PAYMENT");

            int id = readInt("║ Enter Payment ID: ");

            Payment p = paymentService.searchPayment(id);

            if (p == null) {

                displayErrorMessage("PAYMENT NOT FOUND!");

                pauseScreen();
                return;
            }

            displayReceipt(p);

            displayTitle("UPDATE PAYMENT");

            displayCenteredText("ONLY PAYMENT INFORMATION CAN BE CHANGED.");
            displayBottomBorder();

            double amount = readAmount("New Payment Amount: PHP ");

            String method = getMethod();

            LocalDate date = readDate("New Payment Date (YYYY-MM-DD): ");

            if (!confirm("Confirm payment update?")) {

                System.out.println("Payment update cancelled.");

                pauseScreen();
                return;
            }

            if (paymentService.updatePayment(id, amount, method, date)) {

                System.out.println("\n[SUCCESS] Payment successfully updated!");

                displayReceipt(paymentService.searchPayment(id));

            } else {

                System.out.println("\n[ERROR] Unable to update payment.");
            }

        } catch (Exception e) {

            displayError("updating payment", e);
        }

        pauseScreen();
    }

    // DELETE PAYMENT
    private void deletePayment() {

        clearScreen();

        try {

            header("DELETE PAYMENT");

            int id = readInt("║ Enter Payment ID: ");

            Payment p = paymentService.searchPayment(id);

            if (p == null) {

                displayErrorMessage("PAYMENT NOT FOUND!");

                pauseScreen();
                return;
            }

            displayReceipt(p);

            if (confirm("Are you sure you want to delete this payment?")) {

                if (paymentService.deletePayment(id)) {

                    System.out.println("\n[SUCCESS] Payment successfully deleted!");
                    System.out.println("The booking is now UNPAID.");

                } else {

                    System.out.println("\n[ERROR] Unable to delete payment!");
                }

            } else {

                System.out.println("\nPayment deletion cancelled.");
            }

        } catch (Exception e) {

            displayError("deleting payment", e);
        }

        pauseScreen();
    }

    // REUSABLE LAYOUT METHODS

    // DISPLAY TOP BORDER
    private void displayTopBorder() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
    }

    // DISPLAY MIDDLE BORDER
    private void displayMiddleBorder() {
        System.out.println("╠══════════════════════════════════════════════════╣");
    }

    // DISPLAY BOTTOM BORDER
    private void displayBottomBorder() {
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // DISPLAY BLANK LINE
    private void displayBlankLine() {
        System.out.println("║                                                  ║");
    }

    // DISPLAY CENTERED TEXT
    private void displayCenteredText(String text) {

        int width = 50;
        int spaces = width - text.length();

        if (spaces < 0) {
            spaces = 0;
        }

        int left = spaces / 2;
        int right = spaces - left;

        System.out.println(
            "║" + " ".repeat(left) + text + " ".repeat(right) + "║"
        );
    }

    // DISPLAY TITLE
    private void displayTitle(String title) {

        displayTopBorder();
        displayCenteredText(title);
        displayMiddleBorder();
    }

    // DISPLAY ERROR MESSAGE
    private void displayErrorMessage(String message) {

        displayMiddleBorder();
        displayCenteredText(message);
        displayBottomBorder();
    }

    // DISPLAY EXCEPTION
    private void displayError(String operation, Exception e) {

        displayMiddleBorder();
        displayCenteredText("AN ERROR OCCURRED!");
        displayMiddleBorder();

        System.out.println("║ Operation: " + operation);
        System.out.println("║ Error: " + e.getMessage());

        displayBottomBorder();
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
