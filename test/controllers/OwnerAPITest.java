package controllers;

import models.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class OwnerAPITest {

    OwnerAPI api;

    Owner owner1;
    Owner owner2;
    Owner ownerDuplicate;

    @BeforeEach
    void setUp() {
        api = new OwnerAPI(new File("test.xml"));

        owner1 = new Owner(100, "John", "0871111111");
        owner2 = new Owner(101, "Mary", "0872222222");
        ownerDuplicate = new Owner(102, "John", "0873333333");

        api.addOwner(owner1);
        api.addOwner(owner2);
    }

    @Test
    void testAddOwner() {
        // duplicate name (boundary case)
        assertFalse(api.addOwner(ownerDuplicate));

        Owner newOwner = new Owner(103, "Luke", "0874444444");
        assertTrue(api.addOwner(newOwner));
    }

    @Test
    void testGetOwnerByIndex() {
        assertEquals(owner1, api.getOwnerByIndex(0));
        assertNull(api.getOwnerByIndex(-1)); // boundary
        assertNull(api.getOwnerByIndex(10)); // out of bounds
    }

    @Test
    void testGetOwnerByName() {
        assertEquals(owner2, api.getOwnerByName("Mary"));
        assertNull(api.getOwnerByName("Unknown"));
    }

    @Test
    void testUpdateOwner() {
        assertTrue(api.updateOwner(0, "Johnny", "0899999999"));

        Owner updated = api.getOwnerByIndex(0);
        assertEquals("Johnny", updated.getName());
        assertEquals("0899999999", updated.getPhoneNumber());

        assertFalse(api.updateOwner(10, "Fail", "000")); // invalid index
    }

    @Test
    void testRemoveOwner() {
        assertTrue(api.removeOwner(owner1));
        assertFalse(api.removeOwner(owner1)); // already removed
    }

    @Test
    void testRemoveOwnerByName() {
        Owner removed = api.removeOwnerByName("Mary");
        assertEquals(owner2, removed);

        assertNull(api.removeOwnerByName("Unknown"));
    }

    @Test
    void testListOwners() {
        String result = api.listOwners();
        assertTrue(result.contains("John"));
        assertTrue(result.contains("Mary"));
    }

    @Test
    void testListOwnersStartsWith() {
        String result = api.listOwnersStartsWith("J");
        assertTrue(result.contains("John"));

        String empty = api.listOwnersStartsWith("Z");
        assertEquals("No Owners Found", empty);
    }

    // -----------------------------------------------------------------------
    // listOwners – empty list branch
    // -----------------------------------------------------------------------

    @Test
    void testListOwnersWhenEmpty() {
        OwnerAPI emptyApi = new OwnerAPI(new File("empty.xml"));
        assertEquals("No Owners Found", emptyApi.listOwners());
    }

    // -----------------------------------------------------------------------
    // isValidOwner – direct tests including case-insensitive match
    // -----------------------------------------------------------------------

    @Test
    void testIsValidOwnerReturnsTrueForExactMatch() {
        assertTrue(api.isValidOwner("John"));
    }

    @Test
    void testIsValidOwnerReturnsTrueForCaseInsensitiveMatch() {
        assertTrue(api.isValidOwner("john"));
        assertTrue(api.isValidOwner("MARY"));
    }

    @Test
    void testIsValidOwnerReturnsFalseForUnknownName() {
        assertFalse(api.isValidOwner("Unknown"));
    }

    // -----------------------------------------------------------------------
    // retrieveOwnerIndex – direct tests
    // -----------------------------------------------------------------------

    @Test
    void testRetrieveOwnerIndexReturnsCorrectIndex() {
        assertEquals(0, api.retrieveOwnerIndex("John"));
        assertEquals(1, api.retrieveOwnerIndex("Mary"));
    }

    @Test
    void testRetrieveOwnerIndexReturnsCaseInsensitive() {
        assertEquals(0, api.retrieveOwnerIndex("john"));
    }

    @Test
    void testRetrieveOwnerIndexReturnsNegativeOneWhenNotFound() {
        assertEquals(-1, api.retrieveOwnerIndex("Unknown"));
    }

    // -----------------------------------------------------------------------
    // Getters – getOwnerList, getFile, fileName
    // -----------------------------------------------------------------------

    @Test
    void testGetOwnerList() {
        assertEquals(2, api.getOwnerList().size());
        assertTrue(api.getOwnerList().contains(owner1));
        assertTrue(api.getOwnerList().contains(owner2));
    }

    @Test
    void testGetFile() {
        assertEquals(new File("test.xml"), api.getFile());
    }

    @Test
    void testFileName() {
        assertEquals("test.xml", api.fileName());
    }
}