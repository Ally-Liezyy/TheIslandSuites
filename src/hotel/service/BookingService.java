
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
}
