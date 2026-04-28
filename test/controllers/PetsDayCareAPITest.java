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

    PetsDayCareAPI apiWithData;
    Owner owner;
    Dog dog;
    Cat cat;
    Parrot parrot;

    @BeforeEach
    void setUpData() {
        owner = new Owner(100, "John", "0871234567");

        dog = new Dog("Rex", 5, owner, 1000,
                'M', true, 10, false, "Lab", false);

        cat = new Cat("Kitty", 3, owner, 1001,
                'F', true, 4.5, false, true, "BALL OF YARN");

        parrot = new Parrot("Polly", 2, owner, 1002,
                'F', true, 2.0, false,
                25.0, true, 50);

        apiWithData = new PetsDayCareAPI("Data", 10, file);
        apiWithData.addPet(dog);
        apiWithData.addPet(cat);
        apiWithData.addPet(parrot);
    }


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

    @Test
    void testSetNameNull() {
        validAPI.setName(null);
        assertEquals("HappyPets", validAPI.getName()); // unchanged
    }

    @Test
    void testSetNameExactBoundary() {
        validAPI.setName("1234567890"); // exactly 10 chars
        assertEquals("1234567890", validAPI.getName());
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

    //Lower Bound
    @Test
    void testSetMaxBelowRange() {
        validAPI.setMaxNumberOfPets(5);
        assertEquals(20, validAPI.getMaxNumberOfPets()); // unchanged
    }

    //Upper Bound
    @Test
    void testSetMaxUpperBoundaryExact() {
        validAPI.setMaxNumberOfPets(100);
        assertEquals(100, validAPI.getMaxNumberOfPets());
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

    @Test
    void testSetPetsArrayNull() {
        validAPI.setPetsArray(null);
        assertNotNull(validAPI.getPetsArray());
    }

    // ----------------------------
    // CRUD Tests
    // ----------------------------
    @Test
    void testAddPet() {
        Dog newDog = new Dog("Max", 2, owner, 2000,
                'M', true, 8, false, "Bulldog", false);

        assertTrue(apiWithData.addPet(newDog));
    }

    @Test
    void testAddPetLimit() {
        PetsDayCareAPI small = new PetsDayCareAPI("Small", 1, file);
        small.addPet(dog);

        assertFalse(small.addPet(cat)); // exceeds limit
    }

    @Test
    void testDeletePetByIndex() {
        assertEquals(dog, apiWithData.deletePetByIndex(0));
        assertNull(apiWithData.deletePetByIndex(99));
    }

    @Test
    void testDeletePetById() {
        assertEquals(cat, apiWithData.deletePetById(1001));
        assertNull(apiWithData.deletePetById(9999));
    }

    @Test
    void testDeletePetByIdEmptyAfterRemoval() {
        apiWithData.deletePetByIndex(0);
        apiWithData.deletePetByIndex(0);
        apiWithData.deletePetByIndex(0);

        assertNull(apiWithData.deletePetById(1000));
    }

    @Test
    void testDeletePetByIdMiddleElement() {
        Pet removed = apiWithData.deletePetById(1001); // cat in middle
        assertEquals(cat, removed);
        assertEquals(2, apiWithData.numberOfPets());
    }

    @Test
    void testDeletePetByIdLastElement() {
        Pet removed = apiWithData.deletePetById(1002); // parrot (last)
        assertEquals(parrot, removed);
    }

    @Test
    void testUpdatePet() {
        Dog updated = new Dog("NewRex", 6, owner, 1000,
                'M', true, 12, true, "Lab", true);

        assertTrue(apiWithData.updatePet(0, updated));
        assertEquals("NewRex", apiWithData.getPet(0).getName());
    }

    @Test
    void testUpdateInvalid() {
        assertFalse(apiWithData.updatePet(99, dog));
        assertFalse(apiWithData.updatePet(0, null));
    }

    @Test
    void testUpdatePetWrongType() {
        Cat wrong = new Cat("Wrong", 2, owner, 999,
                'F', true, 3.0, false, true, "BALL OF YARN");

        assertTrue(apiWithData.updatePet(0, wrong)); // still returns true

        // BUT dog-specific fields should NOT change incorrectly
        assertEquals("Wrong", apiWithData.getPet(0).getName()); // only common fields
    }

    @Test
    void testUpdatePetSameTypeAllFields() {
        Dog updated = new Dog("Rex2", 7, owner, 1000,
                'M', false, 15, true, "Lab", true);

        apiWithData.updatePet(0, updated);

        Dog result = (Dog) apiWithData.getPet(0);

        assertEquals(7, result.getAge());
        assertTrue(result.isNeutered());
        assertEquals("Labrador", result.getBreed());
    }

    // ----------------------------
    // Search Tests
    // ----------------------------
    @Test
    void testGetPetByIndex() {
        assertEquals(dog, apiWithData.getPet(0));
        assertNull(apiWithData.getPet(-1));
    }

    @Test
    void testGetPetByName() {
        assertEquals(cat, apiWithData.getPet("Kitty"));
        assertNull(apiWithData.getPet("Unknown"));
    }

    @Test
    void testGetPetCaseInsensitive() {
        assertEquals(cat, apiWithData.getPet("kitty"));
    }


    @Test
    void testGetPetById() {
        assertEquals(parrot, apiWithData.getPetById(1002));
    }

    @Test
    void testFindDogByOwnerBreedAge() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("John", "Lab", 5);
        assertEquals(dog, result);
    }

    @Test
    void testGetPetsByOwnerName() {
        String result = apiWithData.getPetsByOwnersName("John");
        assertTrue(result.contains("Rex"));
    }

    // ----------------------------
    // Report Tests
    // ----------------------------
    @Test
    void testListAllPets() {
        assertTrue(apiWithData.listAllPets().contains("Rex"));
    }

    @Test
    void testListAllPetsFormatting() {
        String result = apiWithData.listAllPets();
        assertTrue(result.contains("0:"));
        assertTrue(result.contains("1:"));
    }

    @Test
    void testListDogsNoneButPetsExist() {
        PetsDayCareAPI api = new PetsDayCareAPI("Test", 10, file);
        api.addPet(cat);

        assertEquals("No Dogs", api.listAllDogs());
    }

    @Test
    void testListCats() {
        assertTrue(apiWithData.listAllCats().contains("Kitty"));
    }

    @Test
    void testListDogs() {
        assertTrue(apiWithData.listAllDogs().contains("Rex"));
    }

    @Test
    void testListParrots() {
        assertTrue(apiWithData.listAllParrots().contains("Polly"));
    }

    @Test
    void testListParrotsNoneButMultiplePetsExist() {
        PetsDayCareAPI api = new PetsDayCareAPI("Test", 10, file);

        api.addPet(dog);
        api.addPet(cat);

        assertEquals("No Parrots", api.listAllParrots());
    }

    @Test
    void testListIndoorCats() {
        assertTrue(apiWithData.listAllIndoorCats().contains("Kitty"));
    }

    @Test
    void testListIndoorCatsWhenNoPets() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);
        assertEquals("No Pets", empty.listAllIndoorCats());
    }

    @Test
    void testListDogsOlderThanAge() {
        assertTrue(apiWithData.listDogsOlderThanAge(3).contains("Rex"));
    }

    @Test
    void testListNeuteredAnimals() {
        dog.setNeutered(true);
        assertTrue(apiWithData.listAllNeuteredAnimals().contains("Rex"));
    }

    @Test
    void testListCatsByToy() {
        assertTrue(apiWithData.listAllCatsByFavouriteToy("BALL OF YARN").contains("Kitty"));
    }

    @Test
    void testListCatsByToyNullInput() {
        String result = apiWithData.listAllCatsByFavouriteToy(null);
        assertTrue(result.contains("Please enter"));
    }



    @Test
    void testListPetsByOwnerNoMatch() {
        String result = apiWithData.listAllPetsByOwner("Nobody");

        assertTrue(result.contains("No Pet"));
    }

    @Test
    void testListStayMoreThanDaysNone() {
        String result = apiWithData.listAllPetsThatStayMoreThanDays(10);

        assertTrue(result.contains("No Pet"));
    }

    @Test
    void testListStayMoreThanDaysMatch() {
        boolean[] days = {true,true,true,false,false,false,false};
        dog.setDaysAttending(days);

        String result = apiWithData.listAllPetsThatStayMoreThanDays(2);

        assertTrue(result.contains("Rex"));
    }

    @Test
    void testWeeklyIncomeEmpty() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);
        assertEquals(0.0, empty.getWeeklyIncome());
    }

    @Test
    void testWeeklyIncomeWithData() {
        boolean[] days = {true,true,false,false,false,false,false};
        dog.setDaysAttending(days);

        double income = apiWithData.getWeeklyIncome();

        assertTrue(income > 0);
    }

    @Test
    void testWeeklyIncomeMultiplePets() {
        boolean[] days = {true,true,false,false,false,false,false};

        dog.setDaysAttending(days);
        cat.setDaysAttending(days);

        double income = apiWithData.getWeeklyIncome();

        assertTrue(income > dog.calculateWeeklyFee());
    }

    @Test
    void testListStayDaysEqualBoundary() {
        boolean[] days = {true, true, false, false, false, false, false}; // 2 days
        dog.setDaysAttending(days);

        String result = apiWithData.listAllPetsThatStayMoreThanDays(2);

        // Should NOT include dog because it's equal, not greater
        assertTrue(result.contains("No Pet"));
    }

    // ----------------------------
    // Counting Tests
    // ----------------------------
    @Test
    void testCounting() {
        assertEquals(3, apiWithData.numberOfPets());
        assertEquals(1, apiWithData.numberOfDogs());
        assertEquals(1, apiWithData.numberOfCats());
        assertEquals(1, apiWithData.numberOfParrots());
    }

    @Test
    void testDangerousDogsCount() {
        dog.setDangerousBreed(true);
        assertEquals(1, apiWithData.numberOfDangerousDogs());
    }

    @Test
    void testDangerousDogsMixed() {
        Dog safeDog = new Dog("Safe", 2, owner, 2000,
                'M', true, 5, false, "Lab", false);

        apiWithData.addPet(safeDog);

        dog.setDangerousBreed(true);

        assertEquals(1, apiWithData.numberOfDangerousDogs());
    }

    @Test
    void testIndoorCatsCount() {
        assertEquals(1, apiWithData.numberOfIndoorCats());
    }

    @Test
    void testParrotVocabularyCount() {
        int count = apiWithData.numberOfParrotsByVocabularySize(
                parrot.getVocabularySize()
        );

        assertEquals(1, count);
    }

    @Test
    void testParrotVocabNoMatch() {
        assertEquals(0,
                apiWithData.numberOfParrotsByVocabularySize("Nonexistent"));
    }

    @Test
    void testParrotVocabNullInput() {
        assertEquals(0, apiWithData.numberOfParrotsByVocabularySize(null));
    }

    // ----------------------------
    // Sorting Tests
    // ----------------------------
    @Test
    void testSortByName() {
        apiWithData.sortPetsByName();
        assertEquals("Kitty", apiWithData.getPet(0).getName());
    }

    //Pets with Same Names
    @Test
    void testSortSameNames() {
        apiWithData.addPet(new Dog("Rex", 1, owner, 999,
                'M', true, 5, false, "Lab", false));

        apiWithData.sortPetsByName();

        assertEquals("Kitty", apiWithData.getPet(0).getName());
    }

    @Test
    void testSortById() {
        apiWithData.sortPetsById();
        assertTrue(apiWithData.getPet(0).getId() >= apiWithData.getPet(1).getId());
    }

    @Test
    void testSortOrderCorrect() {
        apiWithData.sortPetsById();

        int first = apiWithData.getPet(0).getId();
        int second = apiWithData.getPet(1).getId();

        assertTrue(first >= second);
    }

    @Test
    void testSortByIdReverseOrder() {
        PetsDayCareAPI api = new PetsDayCareAPI("Test", 10, file);

        api.addPet(new Dog("A", 1, owner, 3000,'M',true,5,false,"Lab",false));
        api.addPet(new Dog("B", 1, owner, 2000,'M',true,5,false,"Lab",false));
        api.addPet(new Dog("C", 1, owner, 1000,'M',true,5,false,"Lab",false));

        api.sortPetsById();

        assertEquals(3000, api.getPet(0).getId());
    }
    // ----------------------------
    // Stay Test
    // ----------------------------
    @Test
    void testBuildStayDaysValid() {
        String[] input = {"y", "n", "y", "n", "y", "n", "y"};
        boolean[] result = PetsDayCareAPI.buildStayDays(input);

        assertTrue(result[0]);
        assertFalse(result[1]);
    }

    @Test
    void testBuildStayDaysMixedInvalid() {
        String[] input = {"y", null, "x", "Y", "", "n", "random"};

        boolean[] result = PetsDayCareAPI.buildStayDays(input);

        assertTrue(result[0]);
        assertFalse(result[1]);
        assertFalse(result[2]);
    }

    @Test
    void testBuildStayDaysInvalid() {
        boolean[] result = PetsDayCareAPI.buildStayDays(null);
        assertEquals(7, result.length);
    }

    @Test
    void testBuildStayDaysAllNulls() {
        String[] input = {null, null, null, null, null, null, null};
        boolean[] result = PetsDayCareAPI.buildStayDays(input);

        for (boolean b : result) {
            assertFalse(b);
        }
    }

    // ----------------------------
    // Empty Case
    // ----------------------------
    @Test
    void testListWhenEmpty() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);
        assertEquals("No Pets", empty.listAllPets());
    }

    @Test
    void testAverageWhenEmpty() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);
        assertEquals(0, empty.getAverageNumDaysPerWeek());
    }

    @Test
    void testAverageDaysNonEmpty() {
        boolean[] days = {true,true,false,false,false,false,false};
        dog.setDaysAttending(days);

        int avg = apiWithData.getAverageNumDaysPerWeek();

        assertTrue(avg >= 0);
    }

    @Test
    void testAverageDivisionRounding() {
        boolean[] d1 = {true,true,false,false,false,false,false}; // 2
        boolean[] d2 = {true,false,false,false,false,false,false}; // 1

        dog.setDaysAttending(d1);
        cat.setDaysAttending(d2);

        int avg = apiWithData.getAverageNumDaysPerWeek();

        assertEquals(1, avg); // 3/2 = 1 (int division)
    }

    @Test
    void testSearchOnEmptyAPI() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);

        assertNull(empty.getPet(0));
        assertNull(empty.getPet("Anything"));
        assertNull(empty.getPetById(1));
    }

    @Test
    void testListAllDogsEmpty() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);
        assertEquals("No Dogs", empty.listAllDogs());
    }

    @Test
    void testListDangerousDogsEmpty() {
        assertEquals("No Dangerous Dogs in the Kennels",
                apiWithData.listAllDangerousDogs());
    }

    @Test
    void testListIndoorCatsNone() {
        cat.setIndoorCat(false);

        assertEquals("No Indoor Cats",
                apiWithData.listAllIndoorCats().trim());
    }

    // ----------------------------
    // Null Cases
    // ----------------------------
    @Test
    void testAddNullPet() {
        assertFalse(apiWithData.addPet(null));
    }

    @Test
    void testUpdateWithNullPet() {
        assertFalse(apiWithData.updatePet(0, null));
    }

    @Test
    void testGetPetNullName() {
        assertNull(apiWithData.getPet(null));
    }

    // ----------------------------
    // Empty String Cases
    // ----------------------------
    @Test
    void testFindDogWithEmptyInputs() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("", "", 0);
        assertNull(result);
    }


    @Test
    void testSearchCaseInsensitive() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("john", "lab", 5);
        assertEquals(dog, result);
    }


    @Test
    void testFindDogMultipleMatchesReturnsFirst() {
        Dog dog2 = new Dog("Rex2", 5, owner, 2000,
                'M', true, 9, false, "Labrador", false);

        apiWithData.addPet(dog2);

        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("John", "Lab", 5);

        assertEquals(dog, result); // first match
    }

    @Test
    void testFindDogPartialBreedMatch() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("John", "La", 5);
        assertEquals(dog, result);
    }

    @Test
    void testFindDogNullInputs() {
        assertNull(apiWithData.findDogByOwnerAndBreedAndAge(null, "Lab", 5));
        assertNull(apiWithData.findDogByOwnerAndBreedAndAge("John", null, 5));
    }

    @Test
    void testFindDogNoMatchDifferentOwner() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("Mike", "Lab", 5);
        assertNull(result);
    }

    @Test
    void testFindDogNoMatchDifferentAge() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("John", "Lab", 99);
        assertNull(result);
    }

    @Test
    void testFindDogNoMatchDifferentBreed() {
        Pet result = apiWithData.findDogByOwnerAndBreedAndAge("John", "Poodle", 5);
        assertNull(result);
    }

    // ----------------------------
    // Empty Delete
    // ----------------------------
    @Test
    void testDeleteFromEmpty() {
        PetsDayCareAPI empty = new PetsDayCareAPI("Empty", 10, file);

        assertNull(empty.deletePetByIndex(0));
        assertNull(empty.deletePetById(1000));
    }

    // ----------------------------
    // Delete Reduce Array Size
    // ----------------------------
    @Test
    void testDeleteReducesSize() {
        int before = apiWithData.numberOfPets();
        apiWithData.deletePetByIndex(0);
        assertEquals(before - 1, apiWithData.numberOfPets());
    }

    // ----------------------------
    // Stays Edge Case
    // ----------------------------
    @Test
    void testBuildStayDaysAllNo() {
        String[] input = {"n", "n", "n", "n", "n", "n", "n"};
        boolean[] result = PetsDayCareAPI.buildStayDays(input);

        for (boolean day : result) {
            assertFalse(day);
        }
    }

    @Test
    void testBuildStayDaysWrongLength() {
        String[] input = {"y", "n"}; // invalid length

        boolean[] result = PetsDayCareAPI.buildStayDays(input);

        assertEquals(7, result.length);
    }

    // ----------------------------
    // Sorting Edge Case
    // ----------------------------
    @Test
    void testSortSingleElement() {
        PetsDayCareAPI single = new PetsDayCareAPI("One", 10, file);
        single.addPet(dog);

        single.sortPetsByName();
        assertEquals("Rex", single.getPet(0).getName());
    }

    @Test
    void testSortAlreadySorted() {
        apiWithData.sortPetsByName();
        apiWithData.sortPetsByName(); // again

        assertEquals("Kitty", apiWithData.getPet(0).getName());
    }

    // ----------------------------
    // Capacity Edge Case
    // ----------------------------
    @Test
    void testMaxCapacityExactLimit() {
        PetsDayCareAPI small = new PetsDayCareAPI("Cap", 10, file);

        for (int i = 0; i < 10; i++) {
            small.addPet(new Dog("Dog" + i, 2, owner, 1000 + i,
                    'M', true, 5, false, "Lab", false));
        }

        assertFalse(small.addPet(dog));
    }

    // ----------------------------
    // No Match Case
    // ----------------------------
    @Test
    void testListCatsByToyNoMatch() {
        assertTrue(apiWithData.listAllCatsByFavouriteToy("Invalid").contains("No Cats"));
    }

    @Test
    void testListDogsOlderThanAgeNone() {
        assertEquals("No Dogs older than 10",
                apiWithData.listDogsOlderThanAge(10));
    }

    // ----------------------------
    // Persistence Test
    // ----------------------------
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testSaveAndLoadPersistence() throws Exception {

        File tempFile = new File("test-persistence.xml");

        // --- create API + data ---
        PetsDayCareAPI api = new PetsDayCareAPI("Test", 10, tempFile);

        Owner owner = new Owner(1, "John", "0870000000");

        Dog dog = new Dog("Rex", 5, owner, 1000,
                'M', true, 10, false, "Lab", false);

        api.addPet(dog);

        // --- save ---
        api.save();

        // --- load into new instance ---
        PetsDayCareAPI loadedAPI = new PetsDayCareAPI("Test", 10, tempFile);
        loadedAPI.load();

        // DEBUG (leave temporarily if needed)
        assertEquals(1, loadedAPI.numberOfPets());

        Pet loaded = loadedAPI.getPet(0); // safer than ID lookup

        assertNotNull(loaded);
        assertEquals("Rex", loaded.getName());
        assertEquals(1000, loaded.getId());

        tempFile.delete();
    }


}