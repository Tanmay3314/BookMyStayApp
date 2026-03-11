/**
 * Abstract class representing a generic Room.
 * This class defines common properties shared by all room types.
 *
 * @author Student
 * @version 2.0
 */
abstract class Room {

    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    /**
     * Constructor to initialize common room attributes
     */
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Method to display room details
     */
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}
/**
 * Concrete class representing a Single Room
 *
 * @author Student
 * @version 2.0
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}
/**
 * Concrete class representing a Double Room
 *
 * @author Student
 * @version 2.0
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}
/**
 * Concrete class representing a Suite Room
 *
 * @author Student
 * @version 2.0
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}
/**
 * Book My Stay App
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates object-oriented modeling using
 * abstraction, inheritance, and polymorphism.
 * It initializes different room types and displays their availability.
 *
 * @author Student
 * @version 2.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v2.1");
        System.out.println("======================================");

        // Creating room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display room details
        System.out.println("\n--- Room Details ---");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);

        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);

        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
    }
}