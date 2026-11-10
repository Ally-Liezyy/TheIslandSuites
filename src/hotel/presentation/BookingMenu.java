
package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import hotel.model.Booking;
import hotel.service.BookingService;

public class BookingMenu {

    private Scanner scanner;
    private BookingService bookingService;

    // Constructor
    public BookingMenu(Scanner scanner, BookingService bookingService) {
        this.scanner = scanner;
        this.bookingService = bookingService;
    }

    // Display booking menu
    public void display() {

        int choice;

        do {
            System.out.println("\n===== BOOKING MANAGEMENT =====");
            System.out.println("1. Add New Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. Search Booking");
            System.out.println("4. Update Booking Information");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Complete Booking");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addBooking();
                    break;

                case 2:
                    viewBookings();
                    break;

                case 3:
                    searchBooking();
                    break;

                case 4:
                    updateBooking();
                    break;

                case 5:
                    cancelBooking();
                    break;

                case 6:
                    completeBooking();
                    break;

                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }

    // Read a date
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

    // Display booking information
    private void displayBooking(Booking booking) {

        System.out.println("\n----- BOOKING DETAILS -----");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Customer ID: " + booking.getCustomerId());
        System.out.println("Room Number: " + booking.getRoomNumber());
        System.out.println("Check-in Date: " + booking.getCheckInDate());
        System.out.println("Check-out Date: " + booking.getCheckOutDate());
        System.out.println("Status: " + booking.getStatus());
    }

    // Add booking
    private void addBooking() {

        System.out.println("\n===== ADD NEW BOOKING =====");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();

        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        LocalDate checkInDate = readDate("Enter Check-in Date (YYYY-MM-DD): ");
        LocalDate checkOutDate = readDate("Enter Check-out Date (YYYY-MM-DD): ");

        Booking booking = new Booking(
                bookingId, customerId, roomNumber,
                checkInDate, checkOutDate);

        if (bookingService.addBooking(booking)) {
            System.out.println("Booking successfully added!");
        } else {
            System.out.println("Unable to add booking. Please check the details.");
        }
    }

    // View all bookings
    private void viewBookings() {

        System.out.println("\n===== ALL BOOKINGS =====");

        ArrayList<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            displayBooking(booking);
        }
    }

    // Search booking
    private void searchBooking() {

        System.out.print("\nEnter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = bookingService.searchBooking(bookingId);

        if (booking != null) {
            displayBooking(booking);
        } else {
            System.out.println("Booking not found!");
        }
    }

    // Update booking
    private void updateBooking() {

        System.out.print("\nEnter Booking ID to update: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = bookingService.searchBooking(bookingId);

        if (booking == null || !booking.getStatus().equals("ACTIVE")) {
            System.out.println("Active booking not found!");
            return;
        }

        System.out.println("\nCurrent Booking Information:");
        displayBooking(booking);

        System.out.print("\nNew Customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("New Room Number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        LocalDate checkInDate = readDate("New Check-in Date (YYYY-MM-DD): ");
        LocalDate checkOutDate = readDate("New Check-out Date (YYYY-MM-DD): ");

        if (bookingService.updateBooking(
                bookingId, customerId, roomNumber,
                checkInDate, checkOutDate)) {

            System.out.println("Booking successfully updated!");

        } else {
            System.out.println("Unable to update booking. Please check the details.");
        }
    }

    // Cancel booking
    private void cancelBooking() {

        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = bookingService.searchBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        displayBooking(booking);

        if (bookingService.cancelBooking(bookingId)) {
            System.out.println("Booking successfully cancelled!");
        } else {
            System.out.println("Unable to cancel booking!");
        }
    }

    // Complete booking
    private void completeBooking() {

        System.out.print("\nEnter Booking ID to complete: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        Booking booking = bookingService.searchBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        displayBooking(booking);

        if (bookingService.completeBooking(bookingId)) {
            System.out.println("Booking successfully completed!");
        } else {
            System.out.println("Unable to complete booking!");
        }
    }
}
