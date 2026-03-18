import java.util.*;

// Add-On Service class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add services to a reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Add-On Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        // Sample available services
        AddOnService breakfast = new AddOnService("Breakfast", 200);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);
        AddOnService spa = new AddOnService("Spa Access", 1500);

        while (true) {
            System.out.println("\nChoose Add-On Service:");
            System.out.println("1. Breakfast (₹200)");
            System.out.println("2. Airport Pickup (₹800)");
            System.out.println("3. Spa Access (₹1500)");
            System.out.println("4. Finish Selection");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, breakfast);
                    break;
                case 2:
                    manager.addService(reservationId, airportPickup);
                    break;
                case 3:
                    manager.addService(reservationId, spa);
                    break;
                case 4:
                    manager.displayServices(reservationId);
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}