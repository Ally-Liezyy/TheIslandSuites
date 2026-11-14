package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public PaymentMenu(Scanner scanner, PaymentService paymentService,
            BookingService bookingService, CustomerService customerService,
            RoomService roomService) {

        this.scanner = scanner;
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    // PAYMENT MENU
    public void display() {
        int choice;

        do {
            clearScreen();
            header("PAYMENT MANAGEMENT");

            System.out.println("║                                                  ║");
            System.out.println("║          [1]  Add New Payment                    ║");
            System.out.println("║          [2]  View All Payments                  ║");
            System.out.println("║          [3]  Search Payment                     ║");
            System.out.println("║          [4]  Update Payment                     ║");
            System.out.println("║          [5]  Delete Payment                     ║");
            System.out.println("║          [6]  Back to Main Menu                  ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");

            choice = readInt("║  Enter your choice: ");

            switch (choice) {
                case 1: addPayment(); break;
                case 2: viewPayments(); break;
                case 3: searchPayment(); break;
                case 4: updatePayment(); break;
                case 5: deletePayment(); break;
                case 6: System.out.println("\nReturning to Main Menu..."); break;
                default:
                    System.out.println("\nInvalid choice!");
                    pauseScreen();
            }

        } while (choice != 6);
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

    private String getMethod() {
        System.out.println("\n[1] Cash");
        System.out.println("[2] GCash");
        System.out.println("[3] Card");

        int option;
        do {
            option = readInt("Select Payment Method: ");
            if (option < 1 || option > 3)
                System.out.println("Invalid payment method!");
        } while (option < 1 || option > 3);

        return option == 1 ? "Cash" : option == 2 ? "GCash" : "Card";
    }

    private Customer getCustomer(int id) {
        return customerService.findCustomerById(id);
    }

    private Room getRoom(int number) {
        return roomService.searchRoom(number);
    }

    private Payment getPaymentByBookingId(int id) {
        for (Payment p : paymentService.getAllPayments())
            if (p.getBookingId() == id) return p;

        return null;
    }

    // DISPLAY BOOKING INFORMATION
    private void displayBookingInformation(Booking b) {
        Customer c = getCustomer(b.getCustomerId());
        Room r = getRoom(b.getRoomNumber());

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║                  BOOKING INFORMATION             ║");
        System.out.println("╠══════════════════════════════════════════════════╣");

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

            long nights = ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate());
            System.out.printf("║ Booking Amount  : PHP %.2f%n", r.getPrice() * nights);
        }

        System.out.println("║ Check-in Date   : " + b.getCheckInDate());
        System.out.println("║ Check-out Date  : " + b.getCheckOutDate());
        System.out.println("║ Booking Status  : " + b.getStatus());
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // DISPLAY RECEIPT
    private void displayReceipt(Payment p) {
        Booking b = bookingService.searchBooking(p.getBookingId());

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║                                                  ║");
        System.out.println("║                 THE ISLAND SUITES                ║");
        System.out.println("║                  PAYMENT RECEIPT                 ║");
        System.out.println("║                                                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");

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

            if (r != null)
                System.out.println("║ Room Type       : " + r.getRoomType());

            System.out.println("║ Check-in Date   : " + b.getCheckInDate());
            System.out.println("║ Check-out Date  : " + b.getCheckOutDate());
        }

        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf("║ Amount          : PHP %.2f%n", p.getAmount());
        System.out.println("║ Payment Method  : " + p.getPaymentMethod());
        System.out.println("║ Payment Date    : " + p.getPaymentDate());
        System.out.println("║ Payment Status  : " + p.getStatus());
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║              THANK YOU FOR YOUR PAYMENT!         ║");
        System.out.println("║                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // ADD NEW PAYMENT
    private void addPayment() {
        clearScreen();
        header("ADD NEW PAYMENT");

        int bookingId = readInt("║  Enter Booking ID: ");
        Booking booking = bookingService.searchBooking(bookingId);

        if (booking == null) {
            System.out.println("\nBooking not found!");
            pauseScreen();
            return;
        }

        Payment existing = getPaymentByBookingId(bookingId);

        if (existing != null) {
            System.out.println("\nThis booking already has a payment!");
            System.out.println("Payment Status: " + existing.getStatus());
            pauseScreen();
            return;
        }

        displayBookingInformation(booking);

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║                   PAYMENT DETAILS                ║");
        System.out.println("╠══════════════════════════════════════════════════╣");

        double amount = readAmount("║ Enter Payment Amount: PHP ");
        String method = getMethod();

        System.out.print("║ Payment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\nPayment Summary");
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Payment Amount : PHP " + amount);
        System.out.println("Payment Method : " + method);
        System.out.println("Payment Date   : " + date);

        if (!confirm("Confirm this payment?")) {
            System.out.println("Payment was not saved.");
            pauseScreen();
            return;
        }

        Payment payment = new Payment(
                paymentService.getNextPaymentId(), bookingId,
                amount, method, date
        );

        if (paymentService.addPayment(payment)) {
            System.out.println("\n[SUCCESS] Payment successfully added!");
            displayReceipt(payment);
        } else {
            System.out.println("\n[ERROR] Unable to add payment!");
        }

        pauseScreen();
    }

    // VIEW ALL PAYMENTS
    private void viewPayments() {
        clearScreen();
        header("ALL PAYMENTS");

        ArrayList<Payment> payments = paymentService.getAllPayments();

        if (payments.isEmpty()) {
            System.out.println("\nNo payment records found.");
            pauseScreen();
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    PAYMENT RECORDS                                         ║");
        System.out.println("╠══════════╦══════════╦══════════════════════╦════════════════╦══════════════╦═══════════════╣");
        System.out.println("║ PAY ID   ║ BOOKING  ║ CUSTOMER NAME        ║ AMOUNT         ║ METHOD       ║ STATUS        ║");
        System.out.println("╠══════════╬══════════╬══════════════════════╬════════════════╬══════════════╬═══════════════╣");

        for (Payment p : payments) {
            Booking b = bookingService.searchBooking(p.getBookingId());
            Customer c = b == null ? null : getCustomer(b.getCustomerId());
            String name = c == null ? "Unknown" : c.getFirstName() + " " + c.getLastName();

            System.out.printf("║ %-8d ║ %-8d ║ %-20s ║ PHP %-10.2f ║ %-12s ║ %-13s ║%n",
                    p.getPaymentId(), p.getBookingId(), name,
                    p.getAmount(), p.getPaymentMethod(), p.getStatus());
        }

        System.out.println("╚══════════╩══════════╩══════════════════════╩════════════════╩══════════════╩═══════════════╝");
        System.out.println("\nTotal Payments: " + payments.size());

        pauseScreen();
    }

    // SEARCH PAYMENT
    private void searchPayment() {
        clearScreen();
        header("SEARCH PAYMENT");

        Payment p = paymentService.searchPayment(readInt("║  Enter Payment ID: "));

        if (p != null) displayReceipt(p);
        else System.out.println("\nPayment not found!");

        pauseScreen();
    }

    // UPDATE PAYMENT
    private void updatePayment() {
        clearScreen();
        header("UPDATE PAYMENT");

        int id = readInt("║  Enter Payment ID: ");
        Payment p = paymentService.searchPayment(id);

        if (p == null) {
            System.out.println("\nPayment not found!");
            pauseScreen();
            return;
        }

        displayReceipt(p);

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║                  UPDATE PAYMENT                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║                                                  ║");
        System.out.println("║       Only payment information can be changed.   ║");
        System.out.println("║                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        double amount = readAmount("New Payment Amount: PHP ");
        String method = getMethod();

        System.out.print("New Payment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

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

        pauseScreen();
    }

    // DELETE PAYMENT
    private void deletePayment() {
        clearScreen();
        header("DELETE PAYMENT");

        int id = readInt("║  Enter Payment ID: ");
        Payment p = paymentService.searchPayment(id);

        if (p == null) {
            System.out.println("\nPayment not found!");
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