import java.util.*;

// Booking Request Model
class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> roomInventory;

    public InventoryService() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

// Booking Service
public class BookMyStayApp {

    // Queue for FIFO booking requests
    private static Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Set to ensure unique room IDs
    private static Set<String> allocatedRoomIds = new HashSet<>();

    // Map roomType -> allocated room IDs
    private static Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    private static InventoryService inventoryService = new InventoryService();

    // Generate unique Room ID
    private static String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 5);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    // Process booking queue
    private static void processBookings() {
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();

            System.out.println("\nProcessing booking for: " + request.customerName);

            // Check availability
            if (!inventoryService.isAvailable(request.roomType)) {
                System.out.println("No " + request.roomType + " rooms available. Booking failed.");
                continue;
            }

            // Generate unique room ID
            String roomId = generateRoomId(request.roomType);

            // Atomic logical operation (allocation + update)
            allocatedRoomIds.add(roomId);

            roomAllocationMap
                    .computeIfAbsent(request.roomType, k -> new HashSet<>())
                    .add(roomId);

            inventoryService.decrement(request.roomType);

            // Confirm booking
            System.out.println("Booking Confirmed!");
            System.out.println("Customer: " + request.customerName);
            System.out.println("Room Type: " + request.roomType);
            System.out.println("Allocated Room ID: " + roomId);
        }
    }

    // Display allocation summary
    private static void displayAllocations() {
        System.out.println("\nRoom Allocation Summary:");
        for (Map.Entry<String, Set<String>> entry : roomAllocationMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        // Add booking requests (FIFO)
        bookingQueue.add(new BookingRequest("Alice", "Single"));
        bookingQueue.add(new BookingRequest("Bob", "Double"));
        bookingQueue.add(new BookingRequest("Charlie", "Single"));
        bookingQueue.add(new BookingRequest("David", "Suite"));
        bookingQueue.add(new BookingRequest("Eve", "Suite")); // Should fail

        // Process bookings
        processBookings();

        // Show results
        displayAllocations();
        inventoryService.displayInventory();
    }
}