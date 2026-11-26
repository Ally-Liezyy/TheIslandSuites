
package hotel.presentation;

import java.util.Scanner;
import java.util.ArrayList;

import hotel.model.Room;
import hotel.service.RoomService;

public class RoomMenu {

    private Scanner scanner;
    private RoomService roomService;

    // CONSTRUCTOR
    public RoomMenu(Scanner scanner, RoomService roomService) {
        this.scanner = scanner;
        this.roomService = roomService;
    }

    // MAIN MENU
    public void display() {

        int choice;

        do {
            clearScreen();
            header("ROOM MANAGEMENT");

            System.out.println("║          [1]  View All Rooms                    ║");
            System.out.println("║          [2]  View Available Rooms              ║");
            System.out.println("║          [3]  Add New Room                      ║");
            System.out.println("║          [4]  Update Room Information           ║");
            System.out.println("║          [5]  Delete Room                       ║");
            System.out.println("║          [6]  Search Room                       ║");
            System.out.println("║          [7]  Back to Main Menu                 ║");
            System.out.println("║                                                ║");

            divider();

            choice = readInt("║  Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewAllRooms();
                        pause();
                        break;

                    case 2:
                        viewAvailableRooms();
                        pause();
                        break;

                    case 3:
                        addRoom();
                        pause();
                        break;

                    case 4:
                        updateRoom();
                        pause();
                        break;

                    case 5:
                        deleteRoom();
                        pause();
                        break;

                    case 6:
                        searchRoom();
                        pause();
                        break;

                    case 7:
                        System.out.println("\nReturning to Main Menu...");
                        break;

                    default:
                        System.out.println("\n[INPUT ERROR] Invalid choice. Please select 1-7.");
                        pause();
                }

            } catch (Exception e) {
                System.out.println("\n[SYSTEM ERROR] Something went wrong.");
                System.out.println("Error: " + e.getMessage());
                pause();
            }

        } while (choice != 7);
    }

    // DISPLAY ROOM RECORDS
    private void displayRooms(ArrayList<Room> rooms) {

        try {
            clearScreen();

            if (rooms == null || rooms.isEmpty()) {
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

        } catch (Exception e) {
            System.out.println("\n[DISPLAY ERROR] Unable to display room records.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW ALL ROOMS
    private void viewAllRooms() {
        try {
            displayRooms(roomService.getAllRooms());
        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to retrieve all rooms.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW AVAILABLE ROOMS
    private void viewAvailableRooms() {
        try {
            displayRooms(roomService.getAvailableRooms());
        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to retrieve available rooms.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // GET NEXT ROOM NUMBER
    private int getNextRoomNumber() {

        try {
            int nextNumber = 101;

            for (Room room : roomService.getAllRooms()) {
                if (room.getRoomNumber() >= nextNumber) {
                    nextNumber = room.getRoomNumber() + 1;
                }
            }

            return nextNumber;

        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to generate room number.");
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    // ADD NEW ROOM
    private void addRoom() {

        try {
            viewAllRooms();

            int roomNumber = getNextRoomNumber();

            if (roomNumber <= 0) {
                System.out.println("[SYSTEM ERROR] Cannot generate room number.");
                return;
            }

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                    ADD NEW ROOM                    ║");
            divider();

            System.out.println("║ Room Number      : " + roomNumber);

            String roomType = readText("║ Enter Room Type  : ");
            double price = readPositiveDouble("║ Enter Room Price : PHP ");

            Room room = new Room(roomNumber, roomType, price, true);

            divider();

            if (roomService.addRoom(room)) {
                System.out.println("║              ROOM ADDED SUCCESSFULLY!              ║");
            } else {
                System.out.println("║             ROOM NUMBER ALREADY EXISTS!            ║");
            }

            footer();

        } catch (Exception e) {
            System.out.println("\n[ADD ROOM ERROR] Unable to add room.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // UPDATE ROOM
    private void updateRoom() {

        try {
            viewAllRooms();

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                 UPDATE ROOM DETAILS                ║");
            divider();

            int roomNumber = readPositiveInt("║ Enter Room Number : ");
            String roomType = readText("║ New Room Type     : ");
            double price = readPositiveDouble("║ New Room Price    : PHP ");
            boolean available = readBoolean("║ Is the room available? (true/false): ");

            if (roomService.updateRoom(roomNumber, roomType, price, available)) {
                divider();
                System.out.println("║              ROOM UPDATED SUCCESSFULLY!            ║");
            } else {
                divider();
                System.out.println("║                  ROOM NOT FOUND!                    ║");
            }

            footer();

        } catch (Exception e) {
            System.out.println("\n[UPDATE ROOM ERROR] Unable to update room.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DELETE ROOM
    private void deleteRoom() {

        try {
            viewAllRooms();

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║                    DELETE ROOM                     ║");
            divider();

            int roomNumber = readPositiveInt("║ Enter Room Number : ");

            if (roomService.deleteRoom(roomNumber)) {
                divider();
                System.out.println("║              ROOM DELETED SUCCESSFULLY!            ║");
            } else {
                divider();
                System.out.println("║                  ROOM NOT FOUND!                    ║");
            }

            footer();

        } catch (Exception e) {
            System.out.println("\n[DELETE ROOM ERROR] Unable to delete room.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // SEARCH ROOM
    private void searchRoom() {

        try {
            viewAllRooms();

            System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            SEARCH ROOM                               ║");
            divider();

            int roomNumber = readPositiveInt("║ Enter Room Number : ");

            Room room = roomService.searchRoom(roomNumber);

            if (room != null) {

                System.out.println("╠══════════╦══════════════════╦══════════════╦════════════════════════╣");

                System.out.printf("║ %-8s ║ %-16s ║ %-12s ║ %-22s ║%n",
                        "ROOM NO.", "ROOM TYPE", "PRICE", "STATUS");

                System.out.println("╠══════════╬══════════════════╬══════════════╬════════════════════════╣");

                System.out.printf("║ %-8d ║ %-16s ║ PHP %-8.2f ║ %-22s ║%n",
                        room.getRoomNumber(),
                        room.getRoomType(),
                        room.getPrice(),
                        room.isAvailable() ? "Available" : "Occupied");

                System.out.println("╚══════════╩══════════╩══════════════╩════════════════════════╝");

            } else {
                System.out.println("║                      ROOM NOT FOUND!                               ║");
                footer();
            }

        } catch (Exception e) {
            System.out.println("\n[SEARCH ERROR] Unable to search room.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // READ INTEGER INPUT
    private int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty.");
                }

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("\n[INPUT ERROR] Please enter a valid whole number.");

            } catch (IllegalArgumentException e) {
                System.out.println("\n[INPUT ERROR] " + e.getMessage());

            } catch (Exception e) {
                System.out.println("\n[INPUT ERROR] Unable to read input.");
            }
        }
    }

    // READ POSITIVE INTEGER
    private int readPositiveInt(String message) {

        while (true) {
            try {
                int number = readInt(message);

                if (number > 0) {
                    return number;
                }

                System.out.println("[INPUT ERROR] Number must be greater than zero.");

            } catch (Exception e) {
                System.out.println("[INPUT ERROR] Invalid room number.");
            }
        }
    }

    // READ POSITIVE DECIMAL
    private double readPositiveDouble(String message) {

        while (true) {
            try {
                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Price cannot be empty.");
                }

                double price = Double.parseDouble(input);

                if (Double.isNaN(price) || Double.isInfinite(price) || price <= 0) {
                    throw new IllegalArgumentException("Price must be a valid amount greater than zero.");
                }

                return price;

            } catch (NumberFormatException e) {
                System.out.println("\n[INPUT ERROR] Please enter a valid price.");

            } catch (IllegalArgumentException e) {
                System.out.println("\n[INPUT ERROR] " + e.getMessage());

            } catch (Exception e) {
                System.out.println("\n[INPUT ERROR] Unable to read price.");
            }
        }
    }

    // READ TEXT INPUT
    private String readText(String message) {

        while (true) {
            try {
                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    return input;
                }

                System.out.println("[INPUT ERROR] This field cannot be empty.");

            } catch (Exception e) {
                System.out.println("[INPUT ERROR] Unable to read text.");
            }
        }
    }

    // READ BOOLEAN INPUT
    private boolean readBoolean(String message) {

        while (true) {
            try {
                System.out.print(message);

                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("true")) {
                    return true;

                } else if (input.equalsIgnoreCase("false")) {
                    return false;
                }

                System.out.println("[INPUT ERROR] Please enter only true or false.");

            } catch (Exception e) {
                System.out.println("[INPUT ERROR] Unable to read availability.");
            }
        }
    }

    // HEADER LAYOUT
    private void header(String title) {

        try {
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║                 THE ISLAND SUITES                ║");
            System.out.println("║              LUXURY HOTEL MANAGEMENT             ║");
            System.out.println("║                                                  ║");
            divider();
            System.out.println("║                                                  ║");
            System.out.printf("║                 %-32s ║%n", title);
            System.out.println("║                                                  ║");
            divider();

        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to display header.");
        }
    }

    // REUSABLE DIVIDER
    private void divider() {
        System.out.println("╠══════════════════════════════════════════════════╣");
    }

    // FOOTER LAYOUT
    private void footer() {
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    // PAUSE SCREEN
    private void pause() {

        try {
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();

        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to pause screen: " + e.getMessage());
        }
    }

    // CLEAR SCREEN
    private void clearScreen() {

        try {
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("[SYSTEM ERROR] Unable to clear screen: " + e.getMessage());
        }
    }
}

