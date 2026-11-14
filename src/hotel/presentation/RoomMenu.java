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
            clearScreen();

            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║                 THE ISLAND SUITES                ║");
            System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║                                                  ║");
            System.out.println("║                 ROOM MANAGEMENT                  ║");
            System.out.println("║                                                  ║");
            System.out.println("║          [1]  View All Rooms                     ║");
            System.out.println("║          [2]  View Available Rooms               ║");
            System.out.println("║          [3]  Add New Room                       ║");
            System.out.println("║          [4]  Update Room Information            ║");
            System.out.println("║          [5]  Delete Room                        ║");
            System.out.println("║          [6]  Search Room                        ║");
            System.out.println("║          [7]  Back to Main Menu                  ║");
            System.out.println("║                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.print("║  Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    viewAllRooms();
                    pause();
                    break;

                case 2:
                    clearScreen();
                    viewAvailableRooms();
                    pause();
                    break;
                case 3:
                    clearScreen();
                    addRoom();
                    pause();
                    break;
                case 4:
                    clearScreen();
                    updateRoom();
                    pause();
                    break;
                case 5:
                    clearScreen();
                    deleteRoom();
                    pause();
                    break;
                case 6:
                    clearScreen();
                    searchRoom();
                    pause();
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    pause();
            }

        } while (choice != 7);
    }

    private void displayRooms(ArrayList<Room> rooms) 
    {
        clearScreen();
        if (rooms.isEmpty()) 
        {
            System.out.println("No rooms found.");
            return;
        }

       System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           ROOM RECORDS                              ║");
        System.out.println("╠══════════╦══════════════════╦══════════════╦════════════════════════╣");
        System.out.printf("║ %-8s ║ %-16s ║ %-12s ║ %-22s ║%n",
                "ROOM NO.", "ROOM TYPE", "PRICE", "STATUS");
        System.out.println("╠══════════╬══════════════════╬══════════════╬════════════════════════╣");

        for (Room room : rooms) {

            System.out.printf("║ %-8d ║ %-16s ║ PHP %-8.2f ║ %-22s ║%n",
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getPrice(),
                    room.isAvailable() ? "Available" : "Occupied");
        }

        System.out.println("╚══════════╩══════════════════╩══════════════╩════════════════════════╝");

        System.out.println("\nTotal Rooms: " + rooms.size());

    }

     // View All Rooms
    private void viewAllRooms() 
    {
        displayRooms(roomService.getAllRooms());

    }

    // View Available Rooms
    private void viewAvailableRooms() 
    {
        displayRooms(roomService.getAvailableRooms());
    }
    

   // GET NEXT ROOM NUMBER
   private int getNextRoomNumber() {

       int nextNumber = 101;

       for (Room room : roomService.getAllRooms()) {

           if (room.getRoomNumber() >= nextNumber) {
               nextNumber = room.getRoomNumber() + 1;
           }
       }

       return nextNumber;
   }

    // Add New Room
    private void addRoom() 
    {
    viewAllRooms();

    int roomNumber = getNextRoomNumber();

    System.out.println("\n╔════════════════════════════════════════════════════╗");
    System.out.println("║                    ADD NEW ROOM                    ║");
    System.out.println("╠════════════════════════════════════════════════════╣");

    System.out.println("║ Room Number      : " + roomNumber);

    System.out.print("║ Enter Room Type   : ");
    String roomType = scanner.nextLine();

    System.out.print("║ Enter Room Price  : PHP ");
    double price = scanner.nextDouble();
    scanner.nextLine();

    Room room = new Room(roomNumber, roomType, price, true);

    System.out.println("╠════════════════════════════════════════════════════╣");

    if (roomService.addRoom(room)) {
        System.out.println("║              ROOM ADDED SUCCESSFULLY!              ║");
    } else {
        System.out.println("║             ROOM NUMBER ALREADY EXISTS!            ║");
    }

    System.out.println("╚════════════════════════════════════════════════════╝");
    }

    //Update Room
    private void updateRoom() 
    {
        viewAllRooms();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                 UPDATE ROOM DETAILS                 ║");
        System.out.println("╠═════════════════════════════════════════════════════╣");
        
        System.out.print("║ Enter Room Number : ");
        int roomNumber = scanner.nextInt();

        scanner.nextLine();

        System.out.print("║ New Room Type     : ");
        String roomType = scanner.nextLine();

        System.out.print("║ New Room Price    : PHP ");
        double price = scanner.nextDouble();

        System.out.print("║ Is the room available? (true/false): ");
        boolean available = scanner.nextBoolean();
        scanner.nextLine();

        if (roomService.updateRoom(roomNumber, roomType, price, available)) 
        {
           System.out.println("╠════════════════════════════════════════════════════╣");
           System.out.println("║              ROOM UPDATED SUCCESSFULLY!            ║");
           System.out.println("╚════════════════════════════════════════════════════╝");
        } else 
        {
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║                  ROOM NOT FOUND!                   ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
        }
    }

     // Delete Room
    private void deleteRoom() 
    {
        viewAllRooms();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                    DELETE ROOM                     ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.print("║ Enter Room Number : ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (roomService.deleteRoom(roomNumber)) 
        {
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║              ROOM DELETED SUCCESSFULLY!            ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        } else 
        {
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║                   ROOM NOT FOUND!                  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        }
    }

    //Search Room
     private void searchRoom()
   {    
        viewAllRooms();

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            SEARCH ROOM                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");

        System.out.print("║ Enter Room Number : ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room room = roomService.searchRoom(roomNumber);

        if (room != null)
        {
        System.out.println("╠══════════╦══════════════════╦══════════════╦════════════════════════╣");
        System.out.printf("║ %-8s ║ %-16s ║ %-12s ║ %-22s ║%n","ROOM NO.", "ROOM TYPE", "PRICE", "STATUS");
        System.out.println("╠══════════╬══════════════════╬══════════════╬════════════════════════╣");
        System.out.printf("║ %-8d ║ %-16s ║ PHP %-8.2f ║ %-22s ║%n", room.getRoomNumber(), room.getRoomType(),  room.getPrice(), room.isAvailable() ? "Available" : "Occupied");

        System.out.println("╚══════════╩══════════════════╩══════════════╩════════════════════════╝");
        } else
        {
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                          ROOM NOT FOUND!                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
        }
   }
     // PAUSE SCREEN
    private void pause() {

       System.out.print("\nPress Enter to continue...");
       scanner.nextLine();
    }

    // CLEAR SCREEN
    private void clearScreen() {
         for (int i = 0; i < 40; i++) {
        System.out.println();
    }
    }


}
