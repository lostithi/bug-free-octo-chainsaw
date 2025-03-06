package test.java.coffee;

import main.java.*;
import coffee.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class PaymentTest {
    private Payment validPayment;

@BeforeEach
    void setUp() {
        // Valid Payment 
        try {
        validPayment = new Payment("PAY123","ORD001",50.0,"cash");
        } catch (InvalidDataException e) {
        fail("Exception should not be thrown for valid");
        }
    }
    // Test for valid payment,
    @Test
    void testValidPaymentCreation() {
        // Valid 
        assertNotNull(validPayment, "Payment should not be null after creating");
        assertEquals("PAY123", validPayment.getPaymentID(), "Payment ID should match input value");
        assertEquals("ORD001", validPayment.getOrderID(), "Should match the input value");
        assertEquals(50.0, validPayment.getAmount(), "Should match the input");
        assertEquals ("cash", validPayment.getPaymentMethod(), "Payment method should match");
    }
    // Tests 
    @Test
    void testInvalidPaymentID() {
        assertThrows(InvalidDataException.class, () -> new Payment("", "ORD001", 50.0, "cash"), "Payment ID cannot be empty");
    }
    @Test
    void testInvalidOrderID() {
        assertThrows(InvalidDataException.class,()-> new Payment("PAY123", "", 50.0, "cash"), "Cannot be empty");
        }
    @Test
    void testInvalidAmount() {
        assertThrows(InvalidDataException.class,() -> new Payment("PAY123", "ORD001", 0, "cash"), "Payment amount must be positive");
        }
    @Test
    void testInvalidPaymentMethod() {
        assertThrows(InvalidDataException.class,() -> new Payment("PAY123", "ORD001", 50.0, ""), "Cannot be empty");
        }
    @Test
    void testProcessPayment() {
        assertDoesNotThrow(() -> validPayment.processPayment(), "Payment should not thorw an exception");
        }
    @Test
    void testToFileString() {
        // Tet for the expected string format 
        String expectedString = String.format("PAY123,ORD001,50.00,cash,%s", validPayment.getTimestamp().format(java.time.format.DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss")));
        //  actual to toFileString mcomparison
        assertEquals(expectedString, validPayment.toFileString(), "Expected format should be matching");
    }
    // Payment ID
@Test
    void testSetPaymentID() {
        assertDoesNotThrow(() -> validPayment.setPaymentID("PAY199"), "Setting valid payment ID should not throw an exception");
        assertThrows(InvalidDataException.class, () -> validPayment.setPaymentID(""), "Payment ID cannot be empty");
    }
@Test
    void testSetAmount() {
        assertDoesNotThrow(() -> validPayment.setAmount(100.0), "Should not throw an exception");
        assertThrows(InvalidDataException.class, () -> validPayment.setAmount(0), "Payment amount must be positive");
    }
@Test
    void testSetPaymentMethod() {
        assertDoesNotThrow(() -> validPayment.setPaymentMethod("card"), "Should not throw an exception");
        assertThrows(InvalidDataException.class, () -> validPayment.setPaymentMethod(""), "Payment method cannot be empty");
    }
//Time Test

@Test
    void testSetTimestamp() {
        assertDoesNotThrow(() -> validPayment.setTimestamp(LocalDateTime.now()), "Should not throw an exception");
        assertThrows(IllegalArgumentException.class, () -> validPayment.setTimestamp(null), "Timestamp cannot be null");
    }
}
