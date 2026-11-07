package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;

import hotel.model.Room;
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
                    viewAllRooms();
                    break;
                case 2:
                    viewAvailableRooms();
                    break;
                case 3:
                    addRoom();
                    break;
                case 4:
                    updateRoom();
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

    private void displayRooms(ArrayList<Room> rooms) 
    {

        if (rooms.isEmpty()) 
        {
            System.out.println("No rooms found.");
            return;
        }

        for (Room room : rooms) 
        {
            System.out.println("\nRoom Number: " + room.getRoomNumber());
            System.out.println("Room Type: " + room.getRoomType());
            System.out.println("Price: PHP " + room.getPrice());
            System.out.println("Status: " + (room.isAvailable() ? "Available" : "Occupied"));
        }
    }

     // View All Rooms
    private void viewAllRooms() 
    {
        System.out.println("View All Rooms");
        displayRooms(roomService.getAllRooms());

    }

    // View Available Rooms
    private void viewAvailableRooms() 
    {
        System.out.println("View Available Rooms");
        displayRooms(roomService.getAvailableRooms());
    }

    // Add New Room
    private void addRoom() 
    {
        System.out.println("Add New Room");

        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Room Type: ");
        String roomType = scanner.nextLine();

        System.out.print("Enter Room Price: ");
        double price = scanner.nextDouble();

        Room room = new Room(roomNumber, roomType, price, true);

        if (roomService.addRoom(room)) {
            System.out.println("Room added successfully!");
        } else {
            System.out.println("Room number already exists!");
        }
    }

    //Update Room
    private void updateRoom() 
    {
        System.out.println("Update Room Information");

        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter New Room Type: ");
        String roomType = scanner.nextLine();

        System.out.print("Enter New Price: ");
        double price = scanner.nextDouble();

        System.out.print("Is the room available? (true/false): ");
        boolean available = scanner.nextBoolean();

        if (roomService.updateRoom(roomNumber, roomType, price, available)) 
        {
            System.out.println("Room updated successfully!");
        } else 
        {
            System.out.println("Room not found!");
        }
    }

}
