package test.java.coffee;

import main.java.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;    //Mocking package for File manager

import java.util.List;
import java.util.TreeSet;

public class CoffeeShopTest {
    private CoffeeShop coffeeShop;
    private FileManager fileManager;
    private Order order;
    private Customer customer;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        fileManager = mock(FileManager.class);  
        customer = new Customer("CUST001", "Ithi", "ithi@google.com");
        order = new Order("ORD001", customer);
    }

    @Test
    void testLoadData() {
        //check with predefines data
        when(fileManager.loadMenu()).thenReturn(new Menu("Coffee", 5.0));
        when(fileManager.loadOrders(any(CoffeeShop.class))).thenReturn(new TreeSet<>());  // Mock an empty list of orders
        // Menu and Orers loading
        coffeeShop.loadData(fileManager);   
        assertEquals(1, coffeeShop.getMenu().size(), "Menu should have 1 item");
        assertEquals("Coffee", coffeeShop.getMenu().get(0).getName(), "Menu item should match");
        assertTrue(coffeeShop.getOrders().isEmpty(), "Should be empty on loading");
    }

    @Test
    void testAddOrder() {
        coffeeShop.addOrder(order);
        assertEquals(1 , coffeeShop.getOrders().size(), "Orders SHOuld contain 1 order");
        assertTrue(coffeeShop.getCustomers().contains(customer), "Customer should be added to the set");
    }

    @Test
    void testOpenMenu() {
        when(fileManager.loadMenu()).thenReturn(new Menu("Tea", 3.0));  // Mock a Tea menu item
        coffeeShop.openMenu();
        assertEquals(1, coffeeShop.getMenu().size(), "Atleast an itemafter opening");
        assertEquals("Tea" , coffeeShop.getMenu().get(0).getName(), "Item should match");
    }

    @Test
    void testOpenOrders() {
        TreeSet<Order> orders = new TreeSet<>();
        orders.add(order);
        when(fileManager.loadOrders(any(CoffeeShop.class))).thenReturn(orders);
        coffeeShop.openOrders();
        assertEquals(1, coffeeShop.getOrders().size(), "Atleast 1 after opening");
    }

    @Test
    void testReport() {
        assertDoesNotThrow(() -> coffeeShop.report(), "Report generation should not throw an exception");
                }
}
