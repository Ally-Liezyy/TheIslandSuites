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
            clearScreen();

            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║                 THE ISLAND SUITES                ║");
            System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║                                                  ║");
            System.out.println("║                 CUSTOMER MANAGEMENT              ║");
            System.out.println("║                                                  ║");
            System.out.println("║          [1]  Add New Customer                   ║");
            System.out.println("║          [2]  View All Customers                 ║");
            System.out.println("║          [3]  Search Customer                    ║");
            System.out.println("║          [4]  Update Customer Information        ║");
            System.out.println("║          [5]  Delete Customer                    ║");
            System.out.println("║          [6]  Back to Main Menu                  ║");
            System.out.println("║                                                  ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
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
        clearScreen();

        int id = 1;

        ArrayList<Customer> customers = customerService.getAllCustomers();

        // Auto-generate Customer ID
        for (Customer cust : customers) {
            if (cust.getCustomerId() >= id) {
                id = cust.getCustomerId() + 1;
            }
        }

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                  ADD NEW CUSTOMER                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.println("║ Customer ID      : " + id);

        System.out.print("║ Last Name         : ");
        String lastName = scanner.nextLine();

        System.out.print("║ First Name        : ");
        String firstName = scanner.nextLine();

        System.out.print("║ Middle Name       : ");
        String middleName = scanner.nextLine();

        System.out.print("║ Contact Number    : ");
        String contact = scanner.nextLine();

        System.out.print("║ Email Address     : ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, lastName, firstName, middleName, contact, email);

        System.out.println("╠════════════════════════════════════════════════════╣");

        if (customerService.addCustomer(customer)) {
            System.out.println("║            CUSTOMER ADDED SUCCESSFULLY!            ║");
        } else {
            System.out.println("║             FAILED TO ADD CUSTOMER!                ║");
        }

        System.out.println("╚════════════════════════════════════════════════════╝");

        pause();
    }

     // View all customers
    private void viewCustomers() {

        System.out.println("View Customers selected.");

        System.out.println("\n--- CUSTOMER LIST ---");

        ArrayList<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) 
        {
            System.out.println("No customers found.");
            return;
        }

       
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                      CUSTOMER RECORDS                                                       ║");
        System.out.println("╠════════╬══════════════════╬══════════════════╬══════════════════╬══════════════════╬════════════════════════╣");
        System.out.println("║ ID     ║ LAST NAME        ║ FIRST NAME       ║ MIDDLE NAME      ║ CONTACT NUMBER   ║ EMAIL                  ║");
        System.out.println("╠════════╬══════════════════╬══════════════════╬══════════════════╬══════════════════╬════════════════════════╣");

        for (Customer cust : customers) {
        

            System.out.printf(
                "║ %-6d ║ %-16s ║ %-16s ║ %-16s ║ %-16s ║ %-22s ║%n",
                cust.getCustomerId(),
                cust.getLastName(),
                cust.getFirstName(),
                cust.getMiddleName(),
                cust.getContactNumber(),
                cust.getEmail()
            );
        }

        System.out.println("╚════════╩══════════════════╩══════════════════╩══════════════════╩══════════════════╩════════════════════════╝");

        pause();
    }

      // Search customer
    private void searchCustomer() {
        
    clearScreen();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                   SEARCH CUSTOMER                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.print("║ Enter Customer ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        System.out.println("╠════════════════════════════════════════════════════╣");

        if (customer != null) {

            System.out.println("║                  CUSTOMER FOUND                    ║");
            System.out.println("║ Customer ID      : " + customer.getCustomerId());
            System.out.println("║ Last Name        : " + customer.getLastName());
            System.out.println("║ First Name       : " + customer.getFirstName());
            System.out.println("║ Middle Name      : " + customer.getMiddleName());
            System.out.println("║ Contact Number   : " + customer.getContactNumber());
            System.out.println("║ Email Address    : " + customer.getEmail());

        } else {
            System.out.println("║                CUSTOMER NOT FOUND!                 ║");
        }

        System.out.println("╚════════════════════════════════════════════════════╝");

        pause();
    }

   
    // Update customer
    private void updateCustomer() {

        clearScreen();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                   UPDATE CUSTOMER                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.print("║ Enter Customer ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {

            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║                CUSTOMER NOT FOUND!                 ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            pause();
            return;
        }

        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║             CURRENT CUSTOMER INFORMATION           ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.println("║ Customer ID      : " + customer.getCustomerId());
        System.out.println("║ Last Name        : " + customer.getLastName());
        System.out.println("║ First Name       : " + customer.getFirstName());
        System.out.println("║ Middle Name      : " + customer.getMiddleName());
        System.out.println("║ Contact Number   : " + customer.getContactNumber());
        System.out.println("║ Email Address    : " + customer.getEmail());

        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║                  NEW INFORMATION                   ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.print("║ New Last Name     : ");
        String lastName = scanner.nextLine();

        System.out.print("║ New First Name    : ");
        String firstName = scanner.nextLine();

        System.out.print("║ New Middle Name   : ");
        String middleName = scanner.nextLine();

        System.out.print("║ New Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("║ New Email Address : ");
        String email = scanner.nextLine();

        System.out.println("╠════════════════════════════════════════════════════╣");

        if (customerService.updateCustomer(id, lastName, firstName, middleName, contact, email)) {
            System.out.println("║           CUSTOMER UPDATED SUCCESSFULLY!           ║");
        } else {
            System.out.println("║             FAILED TO UPDATE CUSTOMER!             ║");
        }

        System.out.println("╚════════════════════════════════════════════════════╝");

        pause();
        }

    // Delete customer
    private void deleteCustomer() {

        clearScreen();

        viewCustomers();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                   DELETE CUSTOMER                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.print("║ Enter Customer ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {

            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║                CUSTOMER NOT FOUND!                 ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            pause();
            return;
        }

        System.out.println("║ Customer ID      : " + customer.getCustomerId());
        System.out.println("║ Last Name        : " + customer.getLastName());
        System.out.println("║ First Name       : " + customer.getFirstName());
        System.out.println("║ Middle Name      : " + customer.getMiddleName());

        System.out.println("╠════════════════════════════════════════════════════╣");

        if (customerService.deleteCustomer(id)) {
            System.out.println("║           CUSTOMER DELETED SUCCESSFULLY!           ║");
        } else {
            System.out.println("║             FAILED TO DELETE CUSTOMER!             ║");
        }

        System.out.println("╚════════════════════════════════════════════════════╝");

        pause();
    }

    // Clear Screen
     private void clearScreen() {
         for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    // Pause
    private void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
