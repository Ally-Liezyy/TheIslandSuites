
package hotel.service;

import java.util.ArrayList;
import java.time.LocalDate;

import hotel.model.Booking;
import hotel.model.Customer;
import hotel.model.Room;

public class BookingService {

    private ArrayList<Booking> bookings = new ArrayList<>();

    private int nextBookingId = 1;

    private RoomService roomService;
    private CustomerService customerService;

    // Constructor
    public BookingService(RoomService roomService,
                          CustomerService customerService) {

        this.roomService = roomService;
        this.customerService = customerService;
    }

    // Add a new booking
    public boolean addBooking(Booking booking) {

        if (booking.getBookingId() != nextBookingId ||
            booking.getCustomerId() <= 0 ||
            booking.getRoomNumber() <= 0) {
            return false;
        }

        if (searchBooking(booking.getBookingId()) != null) {
            return false;
        }

        Customer customer = customerService.findCustomerById(
                booking.getCustomerId());

        if (customer == null) {
            return false;
        }

        Room room = roomService.searchRoom(booking.getRoomNumber());

        if (room == null || !room.isAvailable()) {
            return false;
        }

        if (booking.getCheckInDate() == null ||
            booking.getCheckOutDate() == null ||
            booking.getCheckInDate().isBefore(LocalDate.now()) ||
            !booking.getCheckOutDate().isAfter(
                    booking.getCheckInDate())) {
            return false;
        }

        bookings.add(booking);

        room.setAvailable(false);

        // Increase ID only after successful saving
        nextBookingId++;

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

    // Update booking
    public boolean updateBooking(int bookingId,
                                 int customerId,
                                 int roomNumber,
                                 LocalDate checkInDate,
                                 LocalDate checkOutDate) {

        Booking booking = searchBooking(bookingId);

        if (booking == null ||
            !booking.getStatus().equals("ACTIVE")) {
            return false;
        }

        if (customerId <= 0 || roomNumber <= 0) {
            return false;
        }

        Customer customer = customerService.findCustomerById(customerId);

        if (customer == null) {
            return false;
        }

        if (checkInDate == null ||
            checkOutDate == null ||
            checkInDate.isBefore(LocalDate.now()) ||
            !checkOutDate.isAfter(checkInDate)) {
            return false;
        }

        Room newRoom = roomService.searchRoom(roomNumber);

        if (newRoom == null) {
            return false;
        }

        // Allow the booking to keep its current room
        if (booking.getRoomNumber() != roomNumber &&
            !newRoom.isAvailable()) {
            return false;
        }

        // Release previous room if room was changed
        if (booking.getRoomNumber() != roomNumber) {

            Room oldRoom = roomService.searchRoom(
                    booking.getRoomNumber());

            if (oldRoom != null) {
                oldRoom.setAvailable(true);
            }

            newRoom.setAvailable(false);
        }

        booking.setCustomerId(customerId);
        booking.setRoomNumber(roomNumber);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        return true;
    }

    // Cancel booking
    public boolean cancelBooking(int bookingId) {

        Booking booking = searchBooking(bookingId);

        if (booking == null ||
            !booking.getStatus().equals("ACTIVE")) {
            return false;
        }

        Room room = roomService.searchRoom(booking.getRoomNumber());

        if (room != null) {
            room.setAvailable(true);
        }

        booking.setStatus("CANCELLED");

        return true;
    }

    // Complete booking
    public boolean completeBooking(int bookingId) {

        Booking booking = searchBooking(bookingId);

        if (booking == null ||
            !booking.getStatus().equals("ACTIVE")) {
            return false;
        }

        Room room = roomService.searchRoom(booking.getRoomNumber());

        if (room != null) {
            room.setAvailable(true);
        }

        booking.setStatus("COMPLETED");

        return true;
    }

    // Get next Booking ID
    public int getNextBookingId() {
        return nextBookingId;
    }
}
