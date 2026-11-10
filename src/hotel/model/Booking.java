
package hotel.model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int customerId;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    // Constructor
    public Booking(int bookingId, int customerId, int roomNumber,
                   LocalDate checkInDate, LocalDate checkOutDate) {

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = "ACTIVE";
    }
}
