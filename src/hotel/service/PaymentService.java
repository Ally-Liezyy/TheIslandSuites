
package hotel.service;

import java.util.ArrayList;
import hotel.model.Payment;
import hotel.model.Booking;

public class PaymentService {

    private ArrayList<Payment> payments = new ArrayList<>();

    private BookingService bookingService;

    // Constructor
    public PaymentService(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    
    // Add a new payment
    public boolean addPayment(Payment payment) {

        // Check positive IDs
        if (payment.getPaymentId() <= 0 ||
            payment.getBookingId() <= 0) {
            return false;
        }

        // Check payment amount
        if (payment.getAmount() <= 0) {
            return false;
        }

        // Check payment date and method
        if (payment.getPaymentDate() == null ||
            payment.getPaymentMethod() == null ||
            payment.getPaymentMethod().trim().isEmpty()) {
            return false;
        }

        // Check duplicate payment ID
        if (searchPayment(payment.getPaymentId()) != null) {
            return false;
        }

        // Check if booking exists
        Booking booking = bookingService.searchBooking(
                payment.getBookingId());

        if (booking == null ||
            booking.getStatus().equals("CANCELLED")) {
            return false;
        }

        // Prevent multiple payments for the same booking
        for (Payment existingPayment : payments) {

            if (existingPayment.getBookingId() == payment.getBookingId()) {
                return false;
            }
        }

        // Save payment
        payments.add(payment);

        return true;
    }

    
    // View all payments
    public ArrayList<Payment> getAllPayments() {

        return new ArrayList<>(payments);
    }

    // Search payment by ID
    public Payment searchPayment(int paymentId) {

        for (Payment payment : payments) {

            if (payment.getPaymentId() == paymentId) {
                return payment;
            }
        }

        return null;
    }


}
