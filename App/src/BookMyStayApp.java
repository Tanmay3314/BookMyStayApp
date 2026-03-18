import java.util.*;

// Booking Request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Shared Booking Processor
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue;
    private Map<String, Integer> inventory;

    public ConcurrentBookingProcessor() {
        bookingQueue = new LinkedList<>();
        inventory = new HashMap<>();

        // Sample inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
    }

    // Add booking request (Producer)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request added: " + request.getGuestName() + " -> " + request.getRoomType());
    }

    // Process booking request (Consumer)
    public synchronized void processRequest() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        BookingRequest request = bookingQueue.poll();

        String roomType = request.getRoomType();
        int available = inventory.getOrDefault(roomType, 0);

        // Critical section (protected)
        if (available > 0) {
            inventory.put(roomType, available - 1);
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType +
                    " to " + request.getGuestName());
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed: No " + roomType +
                    " available for " + request.getGuestName());
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Worker Thread
class BookingWorker extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) { // each thread processes 2 requests
            processor.processRequest();
            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulate multiple booking requests
        processor.addRequest(new BookingRequest("Tanmay", "Standard"));
        processor.addRequest(new BookingRequest("Amit", "Standard"));
        processor.addRequest(new BookingRequest("Priya", "Standard")); // extra request
        processor.addRequest(new BookingRequest("Riya", "Deluxe"));
        processor.addRequest(new BookingRequest("Karan", "Deluxe")); // extra request

        // Create multiple threads (Guests)
        BookingWorker t1 = new BookingWorker(processor, "Thread-1");
        BookingWorker t2 = new BookingWorker(processor, "Thread-2");
        BookingWorker t3 = new BookingWorker(processor, "Thread-3");

        // Start threads (concurrent execution)
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory state
        processor.displayInventory();
    }
}