
package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hotel.model.Payment;
import hotel.service.PaymentService;

public class PaymentMenu {

    private Scanner scanner;
    private PaymentService paymentService;

    // Constructor
    public PaymentMenu(Scanner scanner, PaymentService paymentService) {

        this.scanner = scanner;
        this.paymentService = paymentService;
    }

    // Display payment menu
    public void display() {

        int choice;

        do {
            System.out.println("\n===== PAYMENT MANAGEMENT =====");
            System.out.println("1. Add New Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Search Payment");
            System.out.println("4. Update Payment Information");
            System.out.println("5. Delete Payment");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // Read payment date
    private LocalDate readDate(String message) {

        while (true) {

            System.out.print(message);
            String date = scanner.nextLine();

            try {
                return LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Use YYYY-MM-DD format.");
            }
        }
    }

    // Display payment information
    private void displayPayment(Payment payment) {

        System.out.println("\n----- PAYMENT DETAILS -----");
        System.out.println("Payment ID: " + payment.getPaymentId());
        System.out.println("Booking ID: " + payment.getBookingId());
        System.out.println("Amount: PHP " + payment.getAmount());
        System.out.println("Payment Method: " + payment.getPaymentMethod());
        System.out.println("Payment Date: " + payment.getPaymentDate());
        System.out.println("Status: " + payment.getStatus());
    }

    // Add payment
    private void addPayment() {

        System.out.println("\n===== ADD NEW PAYMENT =====");

        System.out.print("Enter Payment ID: ");
        int paymentId = scanner.nextInt();

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();

        System.out.print("Enter Payment Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Payment Method (Cash/GCash/Card): ");
        String paymentMethod = scanner.nextLine();

        LocalDate paymentDate = readDate(
                "Enter Payment Date (YYYY-MM-DD): ");

        Payment payment = new Payment(
                paymentId,
                bookingId,
                amount,
                paymentMethod,
                paymentDate
        );

        if (paymentService.addPayment(payment)) {
            System.out.println("Payment successfully added!");
        } else {
            System.out.println("Unable to add payment. Please check the details.");
        }
    }

    // View all payments
    private void viewPayments() {

        System.out.println("\n===== ALL PAYMENTS =====");

        ArrayList<Payment> payments = paymentService.getAllPayments();

        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }

        for (Payment payment : payments) {
            displayPayment(payment);
        }
    }

    // Search payment
    private void searchPayment() {

        System.out.print("\nEnter Payment ID: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine();

        Payment payment = paymentService.searchPayment(paymentId);

        if (payment != null) {
            displayPayment(payment);
        } else {
            System.out.println("Payment not found!");
        }
    }

    // Update payment
    private void updatePayment() {

        System.out.print("\nEnter Payment ID to update: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine();

        Payment payment = paymentService.searchPayment(paymentId);

        if (payment == null) {
            System.out.println("Payment not found!");
            return;
        }

        System.out.println("\nCurrent Payment Information:");
        displayPayment(payment);

        System.out.print("\nNew Payment Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("New Payment Method (Cash/GCash/Card): ");
        String paymentMethod = scanner.nextLine();

        LocalDate paymentDate = readDate(
                "New Payment Date (YYYY-MM-DD): ");

        if (paymentService.updatePayment(
                paymentId, amount, paymentMethod, paymentDate)) {

            System.out.println("Payment successfully updated!");

        } else {
            System.out.println("Unable to update payment. Please check the details.");
        }
    }

    // Delete payment
    private void deletePayment() {

        System.out.print("\nEnter Payment ID to delete: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine();

        Payment payment = paymentService.searchPayment(paymentId);

        if (payment == null) {
            System.out.println("Payment not found!");
            return;
        }

        System.out.println("\nPayment Information:");
        displayPayment(payment);

        System.out.print("\nAre you sure you want to delete this payment? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {

            if (paymentService.deletePayment(paymentId)) {
                System.out.println("Payment successfully deleted!");
            } else {
                System.out.println("Unable to delete payment!");
            }

        } else {
            System.out.println("Payment deletion cancelled.");
        }
    }
}
