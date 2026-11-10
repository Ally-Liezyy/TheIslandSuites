
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
}
