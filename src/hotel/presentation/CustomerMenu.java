package hotel.presentation;

import java.util.ArrayList;
import java.util.Scanner;

import hotel.model.Customer;
import hotel.service.CustomerService;

public class CustomerMenu {

    private Scanner scanner;
    private CustomerService customerService;

    // Constructor
    public CustomerMenu(Scanner scanner, CustomerService customerService) {

        this.scanner = scanner;
        this.customerService = customerService;
    }

    // Display Customer Management menu
    public void display() {

        int choice;

        do {
            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. Add New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Update Customer Information");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    viewCustomers();
                    break;

                case 3:
                    searchCustomer();
                    break;

                case 4:
                    updateCustomer();
                    break;

                case 5:
                    deleteCustomer();
                    break;

                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

     // Add customer
    private void addCustomer() 
    {
        System.out.println("Add Customer selected.");

        System.out.println("\n--- ADD NEW CUSTOMER ---");

        System.out.print("Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, name, contact, email);

        if (customerService.addCustomer(customer)) {
            System.out.println("Customer added successfully!");
        } else {
            System.out.println("Customer ID already exists!");
        }
    }

     // View all customers
    private void viewCustomers() {

        System.out.println("View Customers selected.");

        System.out.println("\n--- CUSTOMER LIST ---");

        ArrayList<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {

            System.out.println("-------------------------");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Contact: " + customer.getContactNumber());
            System.out.println("Email: " + customer.getEmail());
        }
    }

      // Search customer
    private void searchCustomer() {
        System.out.println("Search Customer selected.");

        System.out.print("\nEnter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        if (customer != null) {

            System.out.println("\nCustomer Found!");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Contact: " + customer.getContactNumber());
            System.out.println("Email: " + customer.getEmail());

        } else {
            System.out.println("Customer not found!");
        }

    }

    // Update customer
    private void updateCustomer() 
    {
        System.out.println("Update Customer selected.");

        System.out.print("\nEnter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("New Full Name: ");
        String name = scanner.nextLine();

        System.out.print("New Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("New Email: ");
        String email = scanner.nextLine();

        if (customerService.updateCustomer(id, name, contact, email)) {
            System.out.println("Customer updated successfully!");
        }
    }

     // Delete customer
    private void deleteCustomer() {

        System.out.println("Delete Customer selected.");


        System.out.print("\nEnter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (customerService.deleteCustomer(id)) {
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Customer not found!");
        }
    }
}
