package test.java.coffee;

import main.java.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class OrderTest {
    private Order validOrder;
    private Customer validCustomer;
    private OrderItems validItem;

    @BeforeEach
    void setUp() {
        try {
            validCustomer = new Customer("CUST001", "Ithi", "ithi@google.com");
            validItem = new OrderItems (new MenuItems("ITEM123", "Coffee",5.0),2); // 2 Coffee items
            validOrder = new Order ("ORD123", validCustomer );
            validOrder. addItem( validItem);
            } catch (InvalidDataException e) {
                fail("Exception should not be thrown for valid input");
            }
    }

    @Test
    void testValidOrderCreation() {
        // valid order 
        assertNotNull(validOrder , "Order object should not be null  ");
        assertEquals("ORD123 ", validOrder.getOrderID(), "Order ID should match the input value");
        assertEquals(validCustomer, validOrder.getCustomer(), "Should match the input value");
    }

    @Test
    void testAddItemToOrder() {        // Teston adding an item increases the size of the items list or not
        validOrder.addItem(new OrderItems(new MenuItems("ITEM456", "Tea",3.0), 1)); // Add AN tea item
        assertEquals(2, validOrder.getItems().size(), "Two items after adding a second item");
    }

    @Test
    void testCalculateAmount() {
        double totalAmount = validOrder.calculateAmount();
        assertEquals(10.0, totalAmount, "Should be 10.0 (2 items of Coffee with 5.0 each)");
    }

    @Test
    void testApplyDiscount() {
        Discount discount = new Discount(0.1); // 10% discount applied for Test
        validOrder.applyDiscount(discount );
        // After applying a 10% discount, the final amount should be reduced
        double discountedAmount=validOrder.calculateAmount() * (1 - discount.getDiscountRate());
        validOrder.setFinalAmount(discountedAmount);
        assertEquals(discountedAmount, validOrder.getFinalAmount(), "Final amount should match the discounted value");
    }

    @Test
    void testSetNegativeFinalAmount() {
        //Twst for Negative now
        assertThrows(IllegalArgumentException.class, () -> validOrder.setFinalAmount(-5.0), "Cannot be negative");  //Should not work
    }

    @Test
    void testToFileString() {
        // Get the expected string for file storage
        String expectedString = String.format("ORD123,CUST123,ITEM123,2,%s", validOrder.getTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // Compare the actual output of the toFileString method with the expected string
        assertEquals(expectedString, validOrder.toFileString(), "File string format should match expected format");
    }

    @Test
    void testInvalidInput() {               // Test  should throw InvalidDataException
        assertThrows(InvalidDataException.class,()-> new Order("", validCustomer),"Cannot be empty");
        assertThrows(InvalidDataException.class,()-> new Order("ORD124", null),"Cannot be null");
    }
}
