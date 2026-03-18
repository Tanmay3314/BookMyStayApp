import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        this.isActive = false;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId +
                ", Status: " + (isActive ? "Active" : "Cancelled");
    }
}

// Cancellation Service
class CancellationService {

    // Store reservations
    private Map<String, Reservation> reservations;

    // Inventory: Room Type -> Count
    private Map<String, Integer> inventory;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack;

    public CancellationService() {
        reservations = new HashMap<>();
        inventory = new HashMap<>();
        rollbackStack = new Stack<>();

        // Sample inventory
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);

        // Sample confirmed bookings
        reservations.put("R101", new Reservation("R101", "Standard", "S1"));
        reservations.put("R102", new Reservation("R102", "Deluxe", "D1"));
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }

    // Display reservations
    public void displayReservations() {
        System.out.println("\nReservations:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }

    // Cancel booking with rollback
    public void cancelBooking(String reservationId) {

        // Validate existence
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation res = reservations.get(reservationId);

        // Validate already cancelled
        if (!res.isActive()) {
            System.out.println("Cancellation Failed: Booking already cancelled.");
            return;
        }

        // Step 1: Record room ID in stack (LIFO)
        rollbackStack.push(res.getRoomId());

        // Step 2: Restore inventory
        String roomType = res.getRoomType();
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Step 3: Update reservation status
        res.cancel();

        System.out.println("Cancellation Successful for Reservation ID: " + reservationId);
    }

    // Display rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CancellationService service = new CancellationService();

        while (true) {
            System.out.println("\n--- Booking Cancellation System ---");
            System.out.println("1. View Reservations");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Inventory");
            System.out.println("4. View Rollback Stack");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    service.displayReservations();
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String id = sc.next();
                    service.cancelBooking(id);
                    break;

                case 3:
                    service.displayInventory();
                    break;

                case 4:
                    service.displayRollbackStack();
                    break;

                case 5:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}