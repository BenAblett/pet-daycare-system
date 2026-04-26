package controllers;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PetsDayCareAPITest {

    PetsDayCareAPI validAPI;
    PetsDayCareAPI longNameAPI;
    PetsDayCareAPI boundaryMaxAPI;

    File file;

    @BeforeEach
    void setUp() {
        file = new File("test.xml");

        validAPI = new PetsDayCareAPI("HappyPets", 20, file);
        longNameAPI = new PetsDayCareAPI("VeryLongDayCareName", 20, file); // >10 chars
        boundaryMaxAPI = new PetsDayCareAPI("Test", 100, file); // upper boundary
    }

    // ----------------------------
    // Constructor + Getters
    // ----------------------------
    @Test
    void testConstructorAndGetters() {
        assertEquals("HappyPets", validAPI.getName());
        assertEquals(20, validAPI.getMaxNumberOfPets());
        assertNotNull(validAPI.getPetsArray());
        assertEquals(0, validAPI.getPetsArray().size());
    }

    // ----------------------------
    // Name Boundary (max 10 chars)
    // ----------------------------
    @Test
    void testNameBoundary() {
        assertEquals(10, longNameAPI.getName().length());
    }

    // ----------------------------
    // setName()
    // ----------------------------
    @Test
    void testSetName() {
        validAPI.setName("NewName");
        assertEquals("NewName", validAPI.getName());

        validAPI.setName("ThisNameIsTooLong"); // invalid
        assertEquals("NewName", validAPI.getName()); // unchanged
    }

    // ----------------------------
    // Max Pets Boundary
    // ----------------------------
    @Test
    void testMaxNumberBoundary() {
        assertEquals(100, boundaryMaxAPI.getMaxNumberOfPets());

        validAPI.setMaxNumberOfPets(10); // lower boundary
        assertEquals(10, validAPI.getMaxNumberOfPets());

        validAPI.setMaxNumberOfPets(101); // invalid
        assertEquals(10, validAPI.getMaxNumberOfPets());
    }

    // ----------------------------
    // setPetsArray / getPetsArray
    // ----------------------------
    @Test
    void testPetsArraySetterGetter() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");
        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, "Lab", false);

        pets.add(dog);

        validAPI.setPetsArray(pets);

        assertEquals(1, validAPI.getPetsArray().size());
        assertEquals(dog, validAPI.getPetsArray().get(0));
    }
}