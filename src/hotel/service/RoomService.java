package hotel.service;

import java.util.ArrayList;
import hotel.model.Room;

public class RoomService {
    
    private ArrayList<Room> rooms = new ArrayList<>();

     // Add a new room
    public boolean addRoom(Room room) {

     for (Room existingRoom : rooms) 
     {
        if (existingRoom.getRoomNumber() == room.getRoomNumber()) {
            return false;
        }
     }

      rooms.add(room);
      return true;
    }
     
    // View all rooms
    public ArrayList<Room> getAllRooms()
    {
     return rooms;
    }

    // View available rooms
    public ArrayList<Room> getAvailableRooms()
    {

     ArrayList<Room> availableRooms = new ArrayList<>();

     for (Room room : rooms) 
     {
        if (room.isAvailable()) 
        {
            availableRooms.add(room);
        }
     }

     return availableRooms;
    }

    // Search room
    public Room searchRoom(int roomNumber) 
    {

     for (Room room : rooms) 
     {
        if (room.getRoomNumber() == roomNumber) 
        {
            return room;
        }
     }

     return null;
    }

    // Update room
    public boolean updateRoom(int roomNumber, String roomType, double price, boolean available) 
    {

     Room room = searchRoom(roomNumber);

     if (room != null) 
     {
        room.setRoomType(roomType);
        room.setPrice(price);
        room.setAvailable(available);
        return true;
     }

     return false;
    }

   // Delete room
    public boolean deleteRoom(int roomNumber) 
    {

     Room room = searchRoom(roomNumber);

     if (room != null) 
     {
        rooms.remove(room);
        return true;
     }

     return false;
    }
}


