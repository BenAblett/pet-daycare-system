package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParrotTest {

    Owner owner;
    Parrot parrot;

    @BeforeEach
    void setUp() {
        owner = new Owner(1234, "John Doe", "12345678");
        parrot = new Parrot("Polly", 2, owner, 1000,
                'F', true, 2.0, false,
                25.0, true, 50);
    }

    // -------------------------
    // Constructor + Getters
    // -------------------------
    @Test
    void testConstructorAndGetters() {
        assertEquals(25.0, parrot.getWingSpan());
        assertTrue(parrot.isCanFly());
        assertEquals(50, parrot.getVocabularyLevel());
        assertNotNull(parrot.getVocabularySize());
    }

    // -------------------------
    // Setters
    // -------------------------
    @Test
    void testSetters() {
        parrot.setWingSpan(30);
        assertEquals(30, parrot.getWingSpan());

        parrot.setCanFly(false);
        assertFalse(parrot.isCanFly());

        parrot.setVocabularySize(100);
        assertEquals(100, parrot.getVocabularyLevel());
    }

    // ----------------------
    // Edge Case
    // ----------------------
    @Test
    void testInvalidVocabulary() {
        parrot.setVocabularySize(-10);

        // should NOT change from original value
        assertEquals(50, parrot.getVocabularyLevel());
    }

    // -------------------------
    // Weekly Fee
    // -------------------------
    @Test
    void testWeeklyFee() {
        boolean[] days = {true, true, false, false, false, false, false};
        parrot.setDaysAttending(days);

        assertEquals(2 * 10, parrot.calculateWeeklyFee());
    }

    // -------------------------
    // toString
    // -------------------------
    @Test
    void testToString() {
        String result = parrot.toString();

        assertTrue(result.contains("WingSpan: 25.0"));
        assertTrue(result.contains("canFly: Yes"));
        assertTrue(result.contains("vocabularySize"));
        assertTrue(result.contains("Weekly Fee"));
    }

    // -------------------------
    // equals
    // -------------------------
    @Test
    void testEquals() {
        Parrot same = new Parrot("Other", 5, owner, 2000,
                'F', true, 2.0, false,
                25.0, true, 50);
        assertEquals(parrot, same);
        Parrot different = new Parrot("Diff", 3, owner, 3000,
                'M', false, 3.0, true,
                30.0, false, 10);
        assertNotEquals(parrot, different);
    }

    @Test
    void testEqualsEdgeCases() {
        assertNotEquals(null, parrot);
        assertNotEquals(parrot, owner);
    }
}