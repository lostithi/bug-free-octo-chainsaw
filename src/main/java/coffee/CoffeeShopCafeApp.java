import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

// Enum for item categories
enum Category {
    BEVERAGE, FOOD, OTHER;
    
    // Convert a string from the file into a Category.
    public static Category fromString(String s) {
        s = s.trim().toUpperCase();
        if (s.startsWith("BEV"))
            return BEVERAGE;
        else if (s.startsWith("FOOD"))
            return FOOD;
        else
            return OTHER;
    }
}

// Class representing a menu item
class MenuItem {
    private String id;         
    private String description;
    private double cost;
    private Category category;
    
    public MenuItem(String id, String description, double cost, Category category) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.category = category;
    }
    
    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }
    public Category getCategory() { return category; }
    
    @Override
    public String toString() {
        return String.format("%s: %s ($%.2f) - %s", id, description, cost, category);
    }
}

// Class representing an order record
class Order {
    private LocalDateTime timestamp;
    private String customerId;
    private String itemId;
    
    public Order(LocalDateTime timestamp, String customerId, String itemId) {
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.itemId = itemId;
    }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCustomerId() { return customerId; }
    public String getItemId() { return itemId; }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s", timestamp, customerId, itemId);
    }
}

// Interface for owner operations (menu management, order processing, analytics)
interface OwnerOperations {
    void loadMenu(String filename);
    void loadOrders(String filename);
    void addOrder(Order order);
    void generateReport();
    Collection<MenuItem> getMenuItems();
    MenuItem getMenuItemById(String id);
    double calculateDiscount(List<MenuItem> items);
    double getTotalCost(List<MenuItem> items);
}

// Core class managing the café – loading files, processing orders, and generating reports.
class CoffeeShop implements OwnerOperations {
    private Map<String, MenuItem> menu = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private Map<String, Integer> orderCounts = new HashMap<>();
    private double totalIncome = 0.0;
    
    // Read the menu text file (CSV: id,description,cost,category)
    @Override
    public void loadMenu(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Invalid menu line: " + line);
                    continue;
                }
                String id = parts[0].trim();
                String description = parts[1].trim();
                double cost = Double.parseDouble(parts[2].trim());
                String categoryStr = parts[3].trim();
                Category category = Category.fromString(categoryStr);
                
                // Validate the identifier based on category
                if (category == Category.BEVERAGE && !id.toUpperCase().startsWith("BEV")) {
                    System.out.println("Invalid ID for beverage: " + id);
                    continue;
                } else if (category == Category.FOOD && !id.toUpperCase().startsWith("FOOD")) {
                    System.out.println("Invalid ID for food item: " + id);
                    continue;
                } else if (category == Category.OTHER && 
                          !(id.toUpperCase().startsWith("OTHR") || id.toUpperCase().startsWith("OTHER"))) {
                    System.out.println("Invalid ID for other item: " + id);
                    continue;
                }
                MenuItem item = new MenuItem(id, description, cost, category);
                menu.put(id, item);
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
    }
    
    // Read the orders text file (CSV: timestamp,customerId,itemId)
    @Override
    public void loadOrders(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Invalid order line: " + line);
                    continue;
                }
                LocalDateTime timestamp = LocalDateTime.parse(parts[0].trim());
                String customerId = parts[1].trim();
                String itemId = parts[2].trim();
                if (!menu.containsKey(itemId)) {
                    System.out.println("Order item ID not found in menu: " + itemId);
                    continue;
                }
                Order order = new Order(timestamp, customerId, itemId);
                orders.add(order);
                orderCounts.put(itemId, orderCounts.getOrDefault(itemId, 0) + 1);
                totalIncome += menu.get(itemId).getCost();
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file: " + e.getMessage());
        }
    }
    
    @Override
    public Collection<MenuItem> getMenuItems() {
        return menu.values();
    }
    
    @Override
    public MenuItem getMenuItemById(String id) {
        return menu.get(id);
    }
    
    @Override
    public void addOrder(Order order) {
        orders.add(order);
        String itemId = order.getItemId();
        orderCounts.put(itemId, orderCounts.getOrDefault(itemId, 0) + 1);
        totalIncome += menu.get(itemId).getCost();
    }
    
    @Override
    public void generateReport() {
        System.out.println("\n====== Café Report ======");
        System.out.println("Menu Item | Orders Count");
        for (MenuItem item : menu.values()) {
            int count = orderCounts.getOrDefault(item.getId(), 0);
            System.out.printf("%s - %s: %d orders\n", item.getId(), item.getDescription(), count);
        }
        System.out.printf("Total Income: $%.2f\n", totalIncome);
    }
}

// Main application class (Console-based)
public class CoffeeShopCafeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeShop shop = new CoffeeShop();
        
        // Load the menu and existing orders from text files
        shop.loadMenu("menu.txt");
        shop.loadOrders("orders.txt");

        int choice;
        do {
            System.out.println("\n====== Coffee Shop Console Menu ======");
            System.out.println("1. View Menu");
            System.out.println("2. Place an Order");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1 -> shop.getMenuItems().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.next();
                    System.out.print("Enter Item ID: ");
                    String itemId = scanner.next();
                    if (shop.getMenuItemById(itemId) != null) {
                        shop.addOrder(new Order(LocalDateTime.now(), customerId, itemId));
                        System.out.println("✅ Order placed successfully!");
                    } else {
                        System.out.println("❌ Invalid item ID.");
                    }
                }
                case 3 -> shop.generateReport();
                case 4 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
