
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

    
    // Update booking information
    public boolean updateBooking(int bookingId,
                                 int customerId,
                                 int roomNumber,
                                 LocalDate checkInDate,
                                 LocalDate checkOutDate) {

        Booking booking = searchBooking(bookingId);

        if (booking == null || !booking.getStatus().equals("ACTIVE")) {
            return false;
        }

        if (customerId <= 0 || roomNumber <= 0) {
            return false;
        }

        // Check customer
        Customer customer = customerService.findCustomerById(customerId);

        if (customer == null) {
            return false;
        }

        // Check dates
        if (checkInDate == null || checkOutDate == null ||
            !checkOutDate.isAfter(checkInDate)) {
            return false;
        }

        // Check room
        Room newRoom = roomService.searchRoom(roomNumber);

        if (newRoom == null) {
            return false;
        }

        // Check if selected room is available
        if (booking.getRoomNumber() != roomNumber &&
            !newRoom.isAvailable()) {
            return false;
        }

        // Release previous room if changed
        if (booking.getRoomNumber() != roomNumber) {

            Room oldRoom = roomService.searchRoom(
                    booking.getRoomNumber());

            if (oldRoom != null) {
                oldRoom.setAvailable(true);
            }

            newRoom.setAvailable(false);
        }

        // Update booking details
        booking.setCustomerId(customerId);
        booking.setRoomNumber(roomNumber);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        return true;
    }


}
