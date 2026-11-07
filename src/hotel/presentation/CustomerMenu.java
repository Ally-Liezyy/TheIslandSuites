package hotel.presentation;

import java.util.Scanner;
import hotel.service.CustomerService;

public class CustomerMenu {
    
    private Scanner scanner;
    private CustomerService customerService;

    // Constructor
    public CustomerMenu(Scanner scanner,
                        CustomerService customerService) {

        this.scanner = scanner;
        this.customerService = customerService;
    }

}
