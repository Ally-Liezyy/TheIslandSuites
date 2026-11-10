
package hotel.service;

import java.util.ArrayList;
import java.time.LocalDate;

import hotel.model.Booking;
import hotel.model.Customer;
import hotel.model.Room;

public class BookingService {

    private ArrayList<Booking> bookings = new ArrayList<>();

    private RoomService roomService;
    private CustomerService customerService;

    // Constructor
    public BookingService(RoomService roomService,  CustomerService customerService) {

        this.roomService = roomService;
        this.customerService = customerService;
    }

    
    // Add a new booking
    public boolean addBooking(Booking booking) {

        // Check positive IDs
        if (booking.getBookingId() <= 0 ||
            booking.getCustomerId() <= 0 ||
            booking.getRoomNumber() <= 0) {
            return false;
        }

        // Check duplicate booking ID
        if (searchBooking(booking.getBookingId()) != null) {
            return false;
        }

        // Check if customer exists
        Customer customer = customerService.findCustomerById(
                booking.getCustomerId());

        if (customer == null) {
            return false;
        }

        // Check if room exists
        Room room = roomService.searchRoom(booking.getRoomNumber());

        if (room == null || !room.isAvailable()) {
            return false;
        }

        // Check booking dates
        if (booking.getCheckInDate() == null ||
            booking.getCheckOutDate() == null ||
            !booking.getCheckOutDate().isAfter(
                    booking.getCheckInDate())) {
            return false;
        }

        // Save booking
        bookings.add(booking);

        // Mark room as unavailable
        room.setAvailable(false);

        return true;
    }

    
    // View all bookings
    public ArrayList<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    // Search booking by ID
    public Booking searchBooking(int bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }

        return null;
    }


}
