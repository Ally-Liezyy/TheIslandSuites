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
}
