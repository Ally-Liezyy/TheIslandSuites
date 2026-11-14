
package hotel.model;

import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String status;
    private double cashReceived;
    private double change;

    // Original constructor - kept for compatibility
    public Payment(int paymentId, int bookingId, double amount, String paymentMethod, LocalDate paymentDate) {

        this(paymentId, bookingId, amount, paymentMethod, paymentDate, amount, 0);
    }

    // Constructor with cash received and change
    public Payment(int paymentId, int bookingId, double amount, String paymentMethod, LocalDate paymentDate, double cashReceived, double change) {

        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = "PAID";
        this.cashReceived = cashReceived;
        this.change = change;
    }

    // Getters
    public int getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public double getCashReceived() {
        return cashReceived;
    }

    public double getChange() {
        return change;
    }

    // Setters
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }

    public void setChange(double change) {
        this.change = change;
    }
}
