package hotel.model;

public class Customer {

     private int customerId;
    private String name;
    private String contactNumber;
    private String email;

     // Constructor
    public Customer(int customerId, String name, String contactNumber, String email) {

        this.customerId = customerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
