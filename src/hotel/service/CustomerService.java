package hotel.service;

import java.util.ArrayList;
import hotel.model.Customer;

public class CustomerService {

     private ArrayList<Customer> customers = new ArrayList<>();

      // Add a new customer
    public boolean addCustomer(Customer customer) 
    {

        for (Customer existingCustomer : customers) 
        {

            if (existingCustomer.getCustomerId() == customer.getCustomerId()) 
            {
                return false;
            }
        }

        customers.add(customer);
        return true;
    }

     // Get all customers
    public ArrayList<Customer> getAllCustomers() 
    {
        return customers;
    }

     // Search customer by ID
    public Customer findCustomerById(int customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }

        return null;
    }

     // Update customer information
    public boolean updateCustomer(int customerId,  String name,  String contactNumber,  String email) 
    {
        Customer customer = findCustomerById(customerId);

        if (customer == null) 
        {
            return false;
        }

        customer.setName(name);
        customer.setContactNumber(contactNumber);
        customer.setEmail(email);

        return true;
    }
}

