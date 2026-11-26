
package hotel.presentation;

import java.util.ArrayList;
import java.util.Scanner;

import hotel.model.Customer;
import hotel.service.CustomerService;

public class CustomerMenu {

    private Scanner scanner;
    private CustomerService customerService;

    // CONSTRUCTOR
    public CustomerMenu(Scanner scanner, CustomerService customerService) {
        this.scanner = scanner;
        this.customerService = customerService;
    }

    // MAIN MENU
    public void display() {

        int choice;

        do {
            clearScreen();

            displayTopBorder();
            displayBlankLine();
            displayCenteredText("THE ISLAND SUITES");
            displayCenteredText("LUXURY HOTEL MANAGEMENT");
            displayBlankLine();
            displayMiddleBorder();
            displayBlankLine();
            displayCenteredText("CUSTOMER MANAGEMENT");
            displayBlankLine();

            System.out.println("║          [1] Add New Customer                    ║");
            System.out.println("║          [2] View All Customers                  ║");
            System.out.println("║          [3] Search Customer                     ║");
            System.out.println("║          [4] Update Customer Information         ║");
            System.out.println("║          [5] Delete Customer                     ║");
            System.out.println("║          [6] Back to Main Menu                   ║");

            displayBlankLine();
            displayBottomBorder();

            choice = readInteger("Enter your choice: ");

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
                    pause();
            }

        } while (choice != 6);
    }

    // ADD CUSTOMER
    private void addCustomer() {

        clearScreen();

        try {

            int id = 1;

            ArrayList<Customer> customers = customerService.getAllCustomers();

            // AUTO-GENERATE CUSTOMER ID
            for (Customer cust : customers) {
                if (cust.getCustomerId() >= id) {
                    id = cust.getCustomerId() + 1;
                }
            }

            displayTitle("ADD NEW CUSTOMER");

            System.out.println("║ Customer ID      : " + id);

            String lastName = getValidName("║ Last Name        : ");

            String firstName = getValidName("║ First Name       : ");

            String middleName = getValidName("║ Middle Name      : ");

            String contact = getValidContactNumber();

            String email = getValidEmail();

            Customer customer = new Customer(
                id, lastName, firstName, middleName, contact, email
            );

            displayMiddleBorder();

            if (customerService.addCustomer(customer)) {
                displayCenteredText("CUSTOMER ADDED SUCCESSFULLY!");
            } else {
                displayCenteredText("FAILED TO ADD CUSTOMER!");
            }

            displayBottomBorder();

        } catch (Exception e) {
            displayError("adding customer", e);
        }

        pause();
    }

    // VIEW ALL CUSTOMERS
    private void viewCustomers() {

        clearScreen();

        try {

            ArrayList<Customer> customers = customerService.getAllCustomers();

            if (customers.isEmpty()) {

                displayTitle("CUSTOMER RECORDS");
                displayCenteredText("NO CUSTOMERS FOUND!");
                displayBottomBorder();

                pause();
                return;
            }

            displayCustomerTable(customers);

        } catch (Exception e) {
            displayError("retrieving customers", e);
        }

        pause();
    }

    // DISPLAY CUSTOMER TABLE
    private void displayCustomerTable(ArrayList<Customer> customers) {

        displayTopBorder();
        displayCenteredText("CUSTOMER RECORDS");
        displayMiddleBorder();

        System.out.println("║ ID     ║ LAST NAME        ║ FIRST NAME       ║ MIDDLE NAME      ║ CONTACT NUMBER   ║ EMAIL                  ║");

        displayMiddleBorder();

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

        displayBottomBorder();
    }

    // SEARCH CUSTOMER
    private void searchCustomer() {

        clearScreen();

        try {

            displayTitle("SEARCH CUSTOMER");

            int id = readInteger("║ Enter Customer ID : ");

            Customer customer = customerService.findCustomerById(id);

            displayMiddleBorder();

            if (customer != null) {

                displayCenteredText("CUSTOMER FOUND");

                displayMiddleBorder();

                System.out.println("║ Customer ID      : " + customer.getCustomerId());
                System.out.println("║ Last Name        : " + customer.getLastName());
                System.out.println("║ First Name       : " + customer.getFirstName());
                System.out.println("║ Middle Name      : " + customer.getMiddleName());
                System.out.println("║ Contact Number   : " + customer.getContactNumber());
                System.out.println("║ Email Address    : " + customer.getEmail());

            } else {
                displayCenteredText("CUSTOMER NOT FOUND!");
            }

            displayBottomBorder();

        } catch (Exception e) {
            displayError("searching customer", e);
        }

        pause();
    }

    // UPDATE CUSTOMER
    private void updateCustomer() {

        clearScreen();

        try {

            displayTitle("UPDATE CUSTOMER");

            int id = readInteger("║ Enter Customer ID : ");

            Customer customer = customerService.findCustomerById(id);

            if (customer == null) {

                displayMiddleBorder();
                displayCenteredText("CUSTOMER NOT FOUND!");
                displayBottomBorder();

                pause();
                return;
            }

            displayMiddleBorder();
            displayCenteredText("CURRENT CUSTOMER INFORMATION");
            displayMiddleBorder();

            System.out.println("║ Customer ID      : " + customer.getCustomerId());
            System.out.println("║ Last Name        : " + customer.getLastName());
            System.out.println("║ First Name       : " + customer.getFirstName());
            System.out.println("║ Middle Name      : " + customer.getMiddleName());
            System.out.println("║ Contact Number   : " + customer.getContactNumber());
            System.out.println("║ Email Address    : " + customer.getEmail());

            displayMiddleBorder();
            displayCenteredText("NEW INFORMATION");
            displayMiddleBorder();

            String lastName = getUpdatedName(
                "║ New Last Name     : ",
                customer.getLastName()
            );

            String firstName = getUpdatedName(
                "║ New First Name    : ",
                customer.getFirstName()
            );

            String middleName = getUpdatedName(
                "║ New Middle Name   : ",
                customer.getMiddleName()
            );

            String contact = getUpdatedContactNumber(
                customer.getContactNumber()
            );

            String email = getUpdatedEmail(
                customer.getEmail()
            );

            displayMiddleBorder();

            if (customerService.updateCustomer(
                    id, lastName, firstName, middleName, contact, email)) {

                displayCenteredText("CUSTOMER UPDATED SUCCESSFULLY!");

            } else {
                displayCenteredText("FAILED TO UPDATE CUSTOMER!");
            }

            displayBottomBorder();

        } catch (Exception e) {
            displayError("updating customer", e);
        }

        pause();
    }

    // DELETE CUSTOMER
    private void deleteCustomer() {

        clearScreen();

        try {

            ArrayList<Customer> customers = customerService.getAllCustomers();

            if (customers.isEmpty()) {

                displayTitle("DELETE CUSTOMER");
                displayCenteredText("NO CUSTOMERS FOUND!");
                displayBottomBorder();

                pause();
                return;
            }

            displayCustomerTable(customers);

            System.out.println();

            displayTitle("DELETE CUSTOMER");

            int id = readInteger("║ Enter Customer ID : ");

            Customer customer = customerService.findCustomerById(id);

            if (customer == null) {

                displayMiddleBorder();
                displayCenteredText("CUSTOMER NOT FOUND!");
                displayBottomBorder();

                pause();
                return;
            }

            displayMiddleBorder();
            displayCenteredText("CUSTOMER INFORMATION");
            displayMiddleBorder();

            System.out.println("║ Customer ID      : " + customer.getCustomerId());
            System.out.println("║ Last Name        : " + customer.getLastName());
            System.out.println("║ First Name       : " + customer.getFirstName());
            System.out.println("║ Middle Name      : " + customer.getMiddleName());

            displayMiddleBorder();

            if (customerService.deleteCustomer(id)) {
                displayCenteredText("CUSTOMER DELETED SUCCESSFULLY!");
            } else {
                displayCenteredText("FAILED TO DELETE CUSTOMER!");
            }

            displayBottomBorder();

        } catch (Exception e) {
            displayError("deleting customer", e);
        }

        pause();
    }

    // VALIDATE NAME
    private String getValidName(String message) {

        while (true) {

            System.out.print(message);
            String name = scanner.nextLine().trim();

            if (name.matches("[\\p{L}][\\p{L} .'-]*")) {
                return name;
            }

            System.out.println("Invalid name! Please enter letters only.");
        }
    }

    // VALIDATE CONTACT NUMBER
    private String getValidContactNumber() {

        while (true) {

            System.out.print("║ Contact Number   : ");
            String contact = scanner.nextLine().trim();

            if (contact.matches("09\\d{9}|63\\d{9,11}")) {
                return contact;
            }

            System.out.println(
                "Invalid contact number! Enter 11 digits starting with 09 or 11 to 13 digits starting with 63."
            );
        }
    }

    // VALIDATE GMAIL
    private String getValidEmail() {

        while (true) {

            System.out.print("║ Email Address    : ");
            String email = scanner.nextLine().trim();

            if (email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
                return email;
            }

            System.out.println(
                "Invalid email! Please enter a valid Gmail address (example@gmail.com)."
            );
        }
    }

    // UPDATE NAME
    private String getUpdatedName(String message, String currentValue) {

        while (true) {

            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                return currentValue;
            }

            if (value.matches("[\\p{L}][\\p{L} .'-]*")) {
                return value;
            }

            System.out.println("Invalid name! Please enter letters only.");
        }
    }

    // UPDATE CONTACT NUMBER
    private String getUpdatedContactNumber(String currentValue) {

        while (true) {

            System.out.print("║ New Contact Number: ");
            String contact = scanner.nextLine().trim();

            if (contact.isEmpty()) {
                return currentValue;
            }

            if (contact.matches("09\\d{9}|63\\d{9,11}")) {
                return contact;
            }

            System.out.println(
                "Invalid contact number! Enter 11 digits starting with 09 or 11 to 13 digits starting with 63."
            );
        }
    }

    // UPDATE GMAIL
    private String getUpdatedEmail(String currentValue) {

        while (true) {

            System.out.print("║ New Email Address : ");
            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                return currentValue;
            }

            if (email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
                return email;
            }

            System.out.println(
                "Invalid email! Please enter a valid Gmail address."
            );
        }
    }

    // INTEGER INPUT VALIDATION
    private int readInteger(String message) {

        while (true) {

            System.out.print(message);

            try {

                int number = Integer.parseInt(scanner.nextLine());
                return number;

            } catch (NumberFormatException e) {

                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    // DISPLAY TOP BORDER
    private void displayTopBorder() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
    }

    // DISPLAY MIDDLE BORDER
    private void displayMiddleBorder() {
        System.out.println("╠════════════════════════════════════════════════════╣");
    }

    // DISPLAY BOTTOM BORDER
    private void displayBottomBorder() {
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    // DISPLAY BLANK LINE
    private void displayBlankLine() {
        System.out.println("║                                                  ║");
    }

    // DISPLAY CENTERED TEXT
    private void displayCenteredText(String text) {

        int width = 52;
        int spaces = width - text.length();

        if (spaces < 0) {
            spaces = 0;
        }

        int left = spaces / 2;
        int right = spaces - left;

        System.out.println(
            "║" + " ".repeat(left) + text + " ".repeat(right) + "║"
        );
    }

    // DISPLAY SECTION TITLE
    private void displayTitle(String title) {

        displayTopBorder();
        displayCenteredText(title);
        displayMiddleBorder();
    }

    // DISPLAY ERROR MESSAGE
    private void displayError(String operation, Exception e) {

        displayMiddleBorder();
        displayCenteredText("AN ERROR OCCURRED!");
        displayMiddleBorder();

        System.out.println("║ Operation: " + operation);
        System.out.println("║ Error: " + e.getMessage());

        displayBottomBorder();
    }

    // CLEAR SCREEN
    private void clearScreen() {

        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    // PAUSE
    private void pause() {

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
