//DATA storage and management here
package coffee;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CoffeeShop {
    private List<Menu> menu;
    private TreeSet<Order> orders;  //Treeset for sorted order o/p
    private TreeSet<Customer> customers;
//CONSRUCTOR for ddata Managemnet
    public CoffeeShop() {
        this.menu = new ArrayList<>();
        this.orders = new TreeSet<>((o1, o2) -> o1.getOrderID().compareTo(o2.getOrderID()));
        this.customers = new TreeSet<>((c1, c2) -> c1.getCustomerID().compareTo(c2.getCustomerID()));
    }

    public void openMenu() {        // Method to open and load menu data from file
        FileManager fileManager = new FileManager();
        fileManager.openMenu();
    }
    public void openOrders() {
        FileManager fileManager = new FileManager();
        fileManager.openOrder();
    }
    public void report() {
        System.out.println("Generating report...");
    
      int totalOrders = this.getOrders().size();
      System.out.println("Total Number of Orders: " + totalOrders);

      double totalAmount = 0.0;
      Iterator<Order> iterator = this.getOrders().iterator();
      while (iterator.hasNext()) {
         Order order = iterator.next();
         totalAmount += order.calculateAmount(); // Total amount
      }
      System.out.println("Total Amount of All Orders: $" + String.format("%.2f", totalAmount));
      System.out.println("\nOrder Details:");
     
      // Each order
      iterator = this.getOrders().iterator();
      while (iterator.hasNext()) {
         Order order = iterator.next();
         System.out.println("Order ID: " + order.getOrderID());
         System.out.println("Customer: " + order.getCustomer().getDetails());
         System.out.println("Total Amount: $" + String.format("%.2f", order.calculateAmount()));
         System.out.println("Timestamp: " + order.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
         System.out.println("--------------------------------");
      }
}




    // Gets for access
    public List<Menu> getMenu() { 
        return menu; 
    }
    public TreeSet<Order> getOrders() { 
        return orders; 
    }
    public TreeSet<Customer> getCustomers() { 
        return customers; 
    }
}
