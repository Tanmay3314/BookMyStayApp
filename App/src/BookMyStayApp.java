import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

// Wrapper class for system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load state from file
    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting with default state.");
        }
        return null;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();

        // Attempt to load previous state
        SystemState state = persistenceService.load();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        if (state != null) {
            bookingHistory = state.bookingHistory;
            inventory = state.inventory;
        } else {
            // Default initialization
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Booking System ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Save & Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.next();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.next();

                    System.out.print("Enter Room Type (Standard/Deluxe): ");
                    String type = sc.next();

                    if (inventory.getOrDefault(type, 0) > 0) {
                        bookingHistory.add(new Reservation(id, name, type));
                        inventory.put(type, inventory.get(type) - 1);
                        System.out.println("Booking Successful!");
                    } else {
                        System.out.println("No rooms available.");
                    }
                    break;

                case 2:
                    System.out.println("\nBooking History:");
                    for (Reservation r : bookingHistory) {
                        System.out.println(r);
                    }
                    break;

                case 3:
                    System.out.println("\nInventory:");
                    for (String key : inventory.keySet()) {
                        System.out.println(key + ": " + inventory.get(key));
                    }
                    break;

                case 4:
                    // Save state before exit
                    persistenceService.save(new SystemState(bookingHistory, inventory));
                    System.out.println("Exiting system...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}