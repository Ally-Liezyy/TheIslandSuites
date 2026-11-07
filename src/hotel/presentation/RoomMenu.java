package hotel.presentation;

import java.util.Scanner;
import hotel.service.RoomService;


public class RoomMenu {
    private Scanner scanner;
    private RoomService roomService;

    public RoomMenu(Scanner scanner, RoomService roomService) {
    this.scanner = scanner;
    this.roomService = roomService;
    }

    public void display() {

        int choice;

        do {
            System.out.println("\n===== ROOM MANAGEMENT =====");
            System.out.println("1. View All Rooms");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Add New Room");
            System.out.println("4. Update Room Information");
            System.out.println("5. Delete Room");
            System.out.println("6. Search Room");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("View All Rooms");
                    break;
                case 2:
                    System.out.println("View Available Rooms");
                    break;
                case 3:
                    System.out.println("Add New Room");
                    break;
                case 4:
                    System.out.println("Update Room Information");
                    break;
                case 5:
                    System.out.println("Delete Room");
                    break;
                case 6:
                    System.out.println("Search Room");
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }
}
