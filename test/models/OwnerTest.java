package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    Owner validOwner;
    Owner longNameOwner;
    Owner boundaryIdOwner;

    @BeforeEach
    void setUp() {
        validOwner = new Owner(100, "John Doe", "0871234567");
        longNameOwner = new Owner(101, "12345678901234567890123456789012345", "0871234567");
        boundaryIdOwner = new Owner(999, "Max", "0879999999");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(100, validOwner.getId());
        assertEquals("John Doe", validOwner.getName());
        assertEquals("0871234567", validOwner.getPhoneNumber());
    }

    @Test
    void testNameBoundary() {
        assertEquals(30, longNameOwner.getName().length());
    }

    @Test
    void testIdBoundary() {
        assertEquals(999, boundaryIdOwner.getId());

        boundaryIdOwner.setId(99); // invalid
        assertEquals(999, boundaryIdOwner.getId());
    }

    @Test
    void testSetters() {
        validOwner.setName("Jane");
        assertEquals("Jane", validOwner.getName());
        validOwner.setName("1234567890123456789012345678901");
        assertEquals("Jane", validOwner.getName());

        validOwner.setPhoneNumber("0831234567");
        assertEquals("0831234567", validOwner.getPhoneNumber());

        validOwner.setPhoneNumber("ABC"); // invalid
        assertEquals("0831234567", validOwner.getPhoneNumber());
    }

    @Test
    void testEquals() {
        Owner o1 = new Owner(100, "John", "0871111111");
        Owner o2 = new Owner(100, "John", "0899999999");

        assertEquals(o1, o2);

        Owner o3 = new Owner(101, "John", "0871111111");
        assertNotEquals(o1, o3);

        assertNotEquals(o1, null);
        assertNotEquals(o1, new Object());
    }

    @Test
    void testEqualsSameReference() {
        // covers the `this == o` → true branch (line 1 of equals)
        assertTrue(validOwner.equals(validOwner));
    }

    @Test
    void testEqualsSameIdDifferentName() {
        // covers the `&&` second operand: id matches but name differs → false
        Owner sameId = new Owner(100, "Different Name", "0871234567");
        assertNotEquals(validOwner, sameId);
    }

    @Test
    void testToString() {
        String result = validOwner.toString();
        assertTrue(result.contains("Id: 100"));
        assertTrue(result.contains("John Doe"));
    }
}
