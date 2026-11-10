
package hotel.model;

import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String status;

    // Constructor
    public Payment(int paymentId, int bookingId, double amount,
                   String paymentMethod, LocalDate paymentDate) {

        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = "PAID";
    }
}
