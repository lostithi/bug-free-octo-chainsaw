import java.util.*;
import java.time.LocalDateTime;

enum Category {
    BEVERAGE, FOOD, DESERT;
    }

public class Menu {
    private String itemID;
    private String name;
    private String description;
    private String category;
    private double price;

    // Constructor with validation
    public Menu(String itemID, String name, String description, String category, double price) {
        if (itemID == null || itemID.isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    // Getters
    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    // Display menu item details
    public String getInfo() {
        return "Item: " + name + " | Category: " + category + " | Price: $" + price;
    }
}

public class Order {
    private String orderID;
    private Customer customer;
    private List<OrderItems> items;
    private double finalAmount;

    public Order(String orderID, Customer customer, List<OrderItems> items) {
        if (orderID == null || orderID.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty.");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        this.orderID = orderID;
        this.customer = customer;
        this.items = items;
        this.finalAmount = calculateAmount();
    }

    public String getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItems> getItems() {
        return items;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public double calculateAmount() {
        double total = 0;
        for (OrderItems item : items) {
            total += item.getMenuItem().getPrice() * item.getNoOfOrders();
        }
        return total;
    }

    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }
        this.finalAmount -= (finalAmount * percentage / 100);
    }
}


class customerList{
    private String customerID;
    private String name;
    private String phoneNumber;
  
    public Customer(String customerID, String name, String phoneNumber) {
        if (customerID == null || customerID.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (!phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        this.customerID = customerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDetails() {
        return "Customer ID: " + customerID + ", Name: " + name + ", Phone: " + phoneNumber;
    }
}

interface operations {
    void loadMenu(String Menulist);

    void loadOrder(String Orderlist);

    void newOrder();

    void report();
}

class coffeeShop {
    private List<orderList> menu = new ArrayList<>();
    private Map<String, menuItem> orders = new HashMap<>();
    private List<orderList> customers = new ArrayList<>();

 public void addMenuItem(Menu item) {
        menu.add(item);
    }

   
    public void openMenu() {
        System.out.println("----- Coffee Shop Menu -----");
        for (Menu item : menu) {
            System.out.println(item.getInfo());
        }
    }

   
    public void addOrder(Order order) {
        orders.add(order);
    }

    
    public void openOrders() {
        System.out.println("----- Open Orders -----");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID() + ", Customer: " + order.getCustomer().getName() + ", Total: $" + order.getFinalAmount());
        }
    }

    
    public void report() {
        System.out.println("Generating Coffee Shop Report...");
        System.out.println("Total Orders: " + orders.size());
        System.out.println("Total Customers: " + customers.size());
    }

}

public class coffeedemo {
    public static void main(String[] args) {
        coffeeShop shop = new coffeeShop();
        int choice = 0;
        
    do{
        System.out.println("\nCOFFEE SHOP SIMULATION");
        System.out.println("\n1.MENU list");
        System.out.println("\n2.Order now");
        System.out.println("\n3.Report");
        System.out.println("\n4.Exit");
        System.out.println("\nEnter your choice: ");

    switch(choice){
        case 1->{}
        case 2->{}
        case 3->{}
        case 4->{System.out.println("EXITING.TY!!");}
        default->{System.out.println("INVALID OPTION!!");}
        
    }
    }while(choice!=4);
    }

}