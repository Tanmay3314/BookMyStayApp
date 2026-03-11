import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory manages the centralized storage of room availability.
 * It uses a HashMap to store room types and their available counts.
 *
 * This class ensures that all availability information is stored
 * in a single source of truth.
 *
 * @author Student
 * @version 3.0
 */
class RoomInventory {

    // HashMap to store room type and available count
    private Map<String, Integer> inventory;

    /**
     * Constructor initializes the inventory with predefined room types.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with initial availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /**
     * Retrieves availability for a given room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Updates availability for a given room type
     */
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    /**
     * Displays the entire inventory
     */
    public void displayInventory() {

        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}
/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates centralized management of room availability
 * using a HashMap data structure.
 *
 * @author Student
 * @version 3.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v3.1");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Suite Room availability...");

        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        inventory.displayInventory();
    }
}