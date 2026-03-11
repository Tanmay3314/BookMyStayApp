import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory maintains centralized availability of room types.
 * Provides read-only access methods for search operations.
 *
 * @author Student
 * @version 3.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    /**
     * Returns the availability count for a given room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Returns the entire inventory map (read-only usage)
     */
    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}
/**
 * Abstract class representing a Room
 */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100);
    }
}
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180);
    }
}
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350);
    }
}


/**
 * Book My Stay App
 * Use Case 4: Room Search & Availability Check
 *
 * This program allows guests to view available rooms without
 * modifying inventory state.
 *
 * @author Student
 * @version 4.0
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v4.0");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Store rooms for search
        Room[] rooms = {single, doubleRoom, suite};

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Show only rooms that are available
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}