package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatTest {

    Owner owner;
    Cat cat;

    @BeforeEach
    void setUp() {
        owner = new Owner(1234, "John Doe", "12345678");

        //  DIFFERENCE FROM DOG:
        // Cat uses (indoor, favouriteToy) instead of (breed, dangerous)
        cat = new Cat("Whiskers", 3, owner, 1000,
                'F', true, 4.5, false,
                true, "Ball of Yarn");
    }

    @Test
    void testConstructorAndGetters() {
        //  DIFFERENCE:
        // Checking indoor + toy instead of breed/dangerous
        assertTrue(cat.isIndoorCat());
        assertEquals("Ball of Yarn", cat.getFavouriteToy());
    }

    @Test
    void testConstructorInvalidToy() {
        Cat c = new Cat("Test", 2, owner, 999,
                'F', true, 3.0, false,
                true, "INVALID");

        assertEquals("not known", c.getFavouriteToy());
    }

    @Test
    void testConstructorNullToy() {
        Cat c = new Cat("Test", 2, owner, 999,
                'F', true, 3.0, false,
                true, null);

        assertEquals("not known", c.getFavouriteToy());
    }

    @Test
    void testSetters() {
        // DIFFERENCE:
        // Cat-specific setters
        cat.setIndoorCat(false);
        assertFalse(cat.isIndoorCat());

        cat.setFavouriteToy("Laser Pointer");
        assertEquals("Laser Pointer", cat.getFavouriteToy());
    }

    @Test
    void testSetFavouriteToyNull() {
        cat.setFavouriteToy(null);

        // should remain unchanged
        assertEquals("Ball of Yarn", cat.getFavouriteToy());
    }

    @Test
    void testWeeklyFeeOutdoorCat() {
        cat.setIndoorCat(false);

        boolean[] days = {true, true, false, false, false, false, false};
        cat.setDaysAttending(days);

        // rate = 20 (no +5)
        assertEquals(2 * 20, cat.calculateWeeklyFee());
    }

    @Test
    void testInvalidToy() {
        cat.setFavouriteToy("Invalid Toy");

        // Should NOT change from original
        assertEquals("Ball of Yarn", cat.getFavouriteToy());
    }

    @Test
    void testToyCaseInsensitive() {
        cat.setFavouriteToy("laser pointer");
        assertEquals("laser pointer", cat.getFavouriteToy());
    }

    @Test
    void testWeeklyFeeNoDays() {
        boolean[] days = {false,false,false,false,false,false,false};
        cat.setDaysAttending(days);

        assertEquals(0, cat.calculateWeeklyFee());
    }

    @Test
    void testToString() {
        String result = cat.toString();

        // AME STRUCTURE as DogTest
        assertTrue(result.contains("[Cat]"));

        // DIFFERENCE:
        assertTrue(result.contains("favouriteToy"));
        assertTrue(result.contains("Weekly Fee"));
    }

    @Test
    void testToStringIndoorCat() {
        cat.setIndoorCat(true);
        String result = cat.toString();

        // DIFFERENCE:
        assertTrue(result.contains("indoor: Yes"));
    }

    @Test
    void testToStringOutdoorCat() {
        cat.setIndoorCat(false);

        String result = cat.toString();

        assertTrue(result.contains("indoor: No"));
    }

    @Test
    void testWeeklyFee() {
        boolean[] days = {true, true, false, false, false, false, false};
        cat.setDaysAttending(days);

        //  SAME CONCEPT as Dog (rate * days)
        //  Adjust value depending on your Cat rate
        assertEquals(2 * 25, cat.calculateWeeklyFee());
    }

    @Test
    void testEquals() {
        // DIFFERENCE:
        // Cat equality likely based on toy + indoor (depending on your implementation)
        Cat sameCat = new Cat("Kitty", 2, owner, 2000,
                'F', true, 5, true,
                true, "Ball of Yarn");

        assertEquals(cat, sameCat);

        Cat differentCat = new Cat("Tiger", 4, owner, 3000,
                'M', true, 6, false,
                false, "Laser Pointer");

        assertNotEquals(cat, differentCat);
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(cat.equals(cat));
    }

    @Test
    void testEqualsFirstLine() {
        // SAME AS DOG TEST

        // null check
        assertFalse(cat.equals(null));

        // wrong type check
        assertFalse(cat.equals(owner));
    }

    @Test
    void testEqualsSameToyDifferentIndoor() {
        // DIFFERENCE:
        // Same toy but different indoor status
        Cat differentIndoor = new Cat("Shadow", 3, owner, 4000,
                'F', true, 4.5, false,
                false, "Ball of Yarn");

        assertNotEquals(cat, differentIndoor);
    }

    @Test
    void testEqualsSameIndoorDifferentToy() {
        Cat other = new Cat("Other", 3, owner, 2000,
                'F', true, 4.5, false,
                true, "Laser Pointer");

        assertNotEquals(cat, other);
    }

    @Test
    void testEqualsCaseInsensitiveToy() {
        Cat other = new Cat("Other", 3, owner, 2000,
                'F', true, 4.5, false,
                true, "ball of yarn");

        assertEquals(cat, other);
    }
}
