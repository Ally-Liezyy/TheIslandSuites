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
}
