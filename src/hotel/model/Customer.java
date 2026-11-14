package hotel.model;

public class Customer {

    private int customerId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String contactNumber;
    private String email;

     // Constructor
    public Customer(int customerId, String lastName, String firstName, String middleName, String contactNumber, String email) {

        this.customerId = customerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

     public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    // Setters
     public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
