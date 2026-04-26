package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MammalTest {
  /*As Mammal is an abstract class, we cannot instantiate a Mammal object
   so instead we instantiate a Dog object and just test the Mammal methods from it.
   */
    Owner owner;
    Dog dog;

    @BeforeEach
    void setUp() {
        owner = new Owner(123, "John Doe", "098666736");
        dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, "Lab", false);
    }

    // -----------------------------------------------------------------------
    // Constructor & Getters
    // -----------------------------------------------------------------------

    @Test
    void testConstructorAndGetters() {
        assertEquals('M', dog.getSex());
        assertTrue(dog.isVaccinated());
        assertEquals(10, dog.getWeight());
        assertFalse(dog.isNeutered());
    }

    @Test
    void testConstructorWithFemaleSex() {
        Dog female = new Dog("Bella", 3, owner, 1001, 'F', false, 8.5, true, "Poodle", false);
        assertEquals('F', female.getSex());
        assertFalse(female.isVaccinated());
        assertEquals(8.5, female.getWeight());
        assertTrue(female.isNeutered());
    }

    // -----------------------------------------------------------------------
    // setSex – validation logic (normalises to uppercase, ignores invalid)
    // -----------------------------------------------------------------------

    @Test
    void testSetSexUppercaseMRetainsM() {
        dog.setSex('M');
        assertEquals('M', dog.getSex());
    }

    @Test
    void testSetSexLowercaseMNormalisedToUppercaseM() {
        dog.setSex('m');
        assertEquals('M', dog.getSex());
    }

    @Test
    void testSetSexUppercaseFSetsF() {
        dog.setSex('F');
        assertEquals('F', dog.getSex());
    }

    @Test
    void testSetSexLowercaseFNormalisedToUppercaseF() {
        dog.setSex('f');
        assertEquals('F', dog.getSex());
    }

    @Test
    void testSetSexInvalidCharDoesNotChangeSex() {
        // 'X' is not a valid sex value; the original value ('M') should be preserved
        dog.setSex('X');
        assertEquals('M', dog.getSex());
    }

    @Test
    void testSetSexInvalidDigitDoesNotChangeSex() {
        dog.setSex('1');
        assertEquals('M', dog.getSex());
    }

    // -----------------------------------------------------------------------
    // setVaccinated
    // -----------------------------------------------------------------------

    @Test
    void testSetVaccinatedToFalse() {
        dog.setVaccinated(false);
        assertFalse(dog.isVaccinated());
    }

    @Test
    void testSetVaccinatedToTrue() {
        Dog unvaccinated = new Dog("Spot", 2, owner, 1002, 'M', false, 5, false, "Beagle", false);
        unvaccinated.setVaccinated(true);
        assertTrue(unvaccinated.isVaccinated());
    }

    // -----------------------------------------------------------------------
    // setWeight – no lower-bound validation in source, so negatives are stored
    // -----------------------------------------------------------------------

    @Test
    void testSetWeightPositiveValue() {
        dog.setWeight(20.0);
        assertEquals(20.0, dog.getWeight());
    }

    @Test
    void testSetWeightZero() {
        dog.setWeight(0.0);
        assertEquals(0.0, dog.getWeight());
    }

    @Test
    void testSetWeightNegativeValue() {
        // Mammal.setWeight has no validation; negative value is accepted
        dog.setWeight(-5.0);
        assertEquals(-5.0, dog.getWeight());
    }

    @Test
    void testSetWeightDecimalPrecision() {
        dog.setWeight(12.75);
        assertEquals(12.75, dog.getWeight(), 0.001);
    }

    // -----------------------------------------------------------------------
    // setNeutered
    // -----------------------------------------------------------------------

    @Test
    void testSetNeuteredToTrue() {
        dog.setNeutered(true);
        assertTrue(dog.isNeutered());
    }

    @Test
    void testSetNeuteredToggleBackToFalse() {
        dog.setNeutered(true);
        dog.setNeutered(false);
        assertFalse(dog.isNeutered());
    }

    // -----------------------------------------------------------------------
    // Combined setters (original test preserved & extended)
    // -----------------------------------------------------------------------

    @Test
    void testSetters() {
        dog.setSex('F');
        assertEquals('F', dog.getSex());

        dog.setVaccinated(false);
        assertFalse(dog.isVaccinated());

        dog.setWeight(20);
        assertEquals(20, dog.getWeight());

        dog.setNeutered(true);
        assertTrue(dog.isNeutered());
    }

    // -----------------------------------------------------------------------
    // toString  –  format: " Sex=<char>, vaccinated: Yes|No, neutered=Yes|No"
    //              weight is NOT included; vaccinated/neutered use "Yes"/"No"
    // -----------------------------------------------------------------------

    @Test
    void testToStringContainsMammalFieldLabels() {
        String result = dog.toString();
        assertTrue(result.contains("sex: "));
        assertTrue(result.contains("vaccinated: "));
        assertTrue(result.contains("neutered: "));
        assertTrue(result.contains("weight: "));
    }


    @Test
    void testToStringVaccinatedAndNeuteredUsesYesNo() {
        // setUp: vaccinated=true, neutered=false
        String result = dog.toString();
        assertTrue(result.contains("vaccinated: Yes"));
        assertTrue(result.contains("neutered: No"));
    }

    @Test
    void testToStringReflectsCurrentFieldValues() {
        dog.setSex('F');
        dog.setVaccinated(false);
        dog.setNeutered(true);

        String result = dog.toString();
        assertTrue(result.contains("sex: F"));
        assertTrue(result.contains("vaccinated: No"));
        assertTrue(result.contains("neutered: Yes"));
    }


}
