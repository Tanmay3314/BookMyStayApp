import java.util.*;

// Custom Exception for invalid booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
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

// Validator class
class InvalidBookingValidator {

    private Set<String> validRoomTypes;
    private Map<String, Integer> inventory;

    public InvalidBookingValidator() {
        // Valid room types
        validRoomTypes = new HashSet<>(Arrays.asList("Standard", "Deluxe", "Suite"));

        // Sample inventory
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 0);
    }

    // Validate booking input
    public void validate(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Validate availability
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }

    // Update inventory safely
    public void allocateRoom(String roomType) throws InvalidBookingException {
        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("Cannot allocate room. Inventory exhausted.");
        }

        inventory.put(roomType, available - 1);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InvalidBookingValidator validator = new InvalidBookingValidator();

        while (true) {
            try {
                System.out.println("\n--- Booking System ---");
                validator.displayInventory();

                System.out.print("Enter Reservation ID: ");
                String id = sc.next();

                System.out.print("Enter Guest Name: ");
                String name = sc.next();

                System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
                String roomType = sc.next();

                // Validation (Fail-Fast)
                validator.validate(roomType);

                // Allocation
                validator.allocateRoom(roomType);

                Reservation reservation = new Reservation(id, name, roomType);

                System.out.println("Booking Successful!");
                System.out.println(reservation);

            } catch (InvalidBookingException e) {
                // Graceful error handling
                System.out.println("Booking Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error occurred.");
            }

            System.out.println("\nDo you want to continue? (yes/no)");
            String choice = sc.next();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }

        sc.close();
        System.out.println("System exited safely.");
    }
}