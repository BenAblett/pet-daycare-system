package main;

import controllers.OwnerAPI;
import controllers.PetsDayCareAPI;
import models.*;
import utils.CatToyUtility;
import utils.ScannerInput;

import java.io.File;

public class Driver {
    private final PetsDayCareAPI api;
    private final OwnerAPI ownerAPI;

    public Driver() {
        api = new PetsDayCareAPI("PetCare", 50, new File("pets.xml"));
        ownerAPI = new OwnerAPI(new File("owners.xml"));
    }

    public static void main(String[] args) {
        new Driver().runMenu();
    }

    //----------------------------------------------------------------------------
    // Private methods for displaying the menu and processing the selected options
    //----------------------------------------------------------------------------
    public void runMenu() {
        int option;

        do {
            System.out.println("""
                    ----------Pet DayCare---------
                    | 1. Manage Pets             |
                    | 2. Reports                 |
                    |----------------------------|
                    | 3. Search Pets             |
                    | 4. Sort Pets               |
                    | 5. Count pets              |
                    |----------------------------|
                    | 6. Save                    |
                    | 7. Load                    |
                    |----------------------------|
                    | 0. Exit                    |
                    ------------------------------
                    """);

            option = ScannerInput.readNextInt("Select option: ");

            switch (option) {
                case 1 -> petMenu();
                case 2 -> reportsMenu();
                case 3 -> searchMenu();
                case 4 -> sortingMenu();
                case 5 -> countingMenu();
                case 6 -> save();
                case 7 -> load();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option");
            }

        } while (option != 0);
    }

    // =========================
    // PET MENU (CRUD)
    // =========================
    private void petMenu() {
        int option;
        do {
            System.out.println("""
                    ---------Pet Menu---------
                    | 1. Add Pet             |
                    | 2. Update Pet          |
                    | 3. List All Pets       |
                    | 4. Delete Pet by ID    |
                    | 5. Delete Pet by Index |
                    | 0. Back                |
                    --------------------------
                    """);

            option = ScannerInput.readNextInt("Select: ");

            //noinspection SwitchStatementWithoutDefaultBranch
            switch (option) {
                case 1 -> addPet();
                case 2 -> updatePet();
                case 3 -> System.out.println(api.listAllPets());
                case 4 -> {
                    int id = ScannerInput.readNextInt("Enter ID: ");
                    Pet deleted = api.deletePetById(id);

                    if (deleted != null) {
                        System.out.println("Pet Deleted from System Successfully");
                    } else {
                        System.out.println("Pet not Found");
                    }
                }
                case 5 -> {
                    int index = ScannerInput.readNextInt("Enter Index: ");
                    Pet deleted = api.deletePetByIndex(index);

                    if (deleted != null) {
                        System.out.println("Pet Deleted from System Successfully");
                    } else {
                        System.out.println("Pet not Found");
                    }
                }

            }
        }while (option != 0) ;
    }
    //------------------------------------
    // Private methods for Adding Pets
    //------------------------------------
    private void addPet() {

        int type = ScannerInput.readNextInt("""
    1) Dog
    2) Cat
    3) Parrot
    Choose type:""");

        String name = ScannerInput.readNextLine("Name: ");
        int age = ScannerInput.readNextInt("Age: ");
        int id = ScannerInput.readNextInt("ID: ");
        // -------------------------
        // OWNER HANDLING
        // -------------------------
        String ownerName = ScannerInput.readNextLine("Owner name: ");


        Owner owner = ownerAPI.getOwnerByName(ownerName);

        if (owner == null) {
            String phone = ScannerInput.readNextLine("Owner phone: ");
            owner = new Owner(0, ownerName, phone);
            ownerAPI.addOwner(owner);
            System.out.println("New owner created.");
        } else {
            System.out.println("Existing owner found.");
        }

        // -------------------------
        // STAY INPUT
        // -------------------------
        String[] inputs = new String[7];
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

        for (int i = 0; i < 7; i++) {
            String input;

            do {
                input = ScannerInput.readNextLine(
                        "Stay on " + days[i] + "? (y/n): ").trim().toLowerCase();

                if (!input.equals("y") && !input.equals("n")) {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }

            } while (!input.equals("y") && !input.equals("n"));

            inputs[i] = input;
        }

        boolean[] stay = PetsDayCareAPI.buildStayDays(inputs);

        Pet pet;

        // -------------------------
        // TYPE-SPECIFIC CREATION
        // -------------------------
        switch (type) {

            case 1 -> { // DOG
                char sex = ScannerInput.readNextChar("Sex (M/F): ");
                boolean vaccinated = ScannerInput.readNextBoolean("Vaccinated (y/n): ");
                double weight = ScannerInput.readNextDouble("Weight (kg): ");
                boolean neutered = ScannerInput.readNextBoolean("Neutered (y/n): ");

                String breed = ScannerInput.readNextLine("Breed: ");
                boolean dangerous = ScannerInput.readNextBoolean("Dangerous (y/n): ");

                pet = new Dog(name, age, owner, id,
                        sex, vaccinated, weight, neutered,
                        breed, dangerous);
            }

            case 2 -> { // CAT
                System.out.println(CatToyUtility.getCatToys());

                char sex = ScannerInput.readNextChar("Sex (M/F): ");
                boolean vaccinated = ScannerInput.readNextBoolean("Vaccinated (y/n): ");
                double weight = ScannerInput.readNextDouble("Weight (kg): ");
                boolean neutered = ScannerInput.readNextBoolean("Neutered (y/n): ");

                String toy = ScannerInput.readNextLine("Favourite toy: ");
                boolean indoor = ScannerInput.readNextBoolean("Indoor (y/n): ");

                pet = new Cat(name, age, owner, id,
                        sex, vaccinated, weight, neutered,
                        indoor, toy);
            }

            case 3 -> { // PARROT
                char sex = ScannerInput.readNextChar("Sex (M/F): ");
                boolean vaccinated = ScannerInput.readNextBoolean("Vaccinated (y/n): ");
                double weight = ScannerInput.readNextDouble("Weight (kg): ");
                boolean neutered = ScannerInput.readNextBoolean("Neutered (y/n): ");

                double wingSpan = ScannerInput.readNextDouble("Wing span: ");
                boolean canFly = ScannerInput.readNextBoolean("Can fly (y/n): ");

                int vocab = ScannerInput.readNextInt("Vocabulary size: ");

                pet = new Parrot(name, age, owner, id, sex, vaccinated, weight, neutered,
                        wingSpan, canFly, vocab);
            }

            default -> {
                System.out.println("Invalid type.");
                return;
            }
        }

        // -------------------------
        // FINALISE
        // -------------------------
        pet.setDaysAttending(stay);

        boolean added = api.addPet(pet);

        if (added) {
            System.out.println("Pet added successfully.");
        } else {
            System.out.println("Failed to add pet.");
        }
    }

    //------------------------------------
    // Private methods for Updating Pets
    //------------------------------------
    private void updatePet() {

        int index = ScannerInput.readNextInt("Enter index of pet to update: ");
        Pet existing = api.getPet(index);

        if (existing == null) {
            System.out.println("Invalid index.");
            return;
        }

        System.out.println("Updating: " + existing);

        // -------------------------
        // COMMON INPUT
        // -------------------------
        String name = ScannerInput.readNextLine("New name: ");
        int age = ScannerInput.readNextInt("New age: ");

        String ownerName = ScannerInput.readNextLine("Owner name: ");
        String phone = ScannerInput.readNextLine("Owner phone: ");
        Owner owner = new Owner(0, ownerName, phone);

        // Stay input
        String[] inputs = new String[7];
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < 7; i++) {
            inputs[i] = ScannerInput.readNextLine(
                    "Stay on " + days[i] + "? (y/n): ");
        }

        boolean[] stay = PetsDayCareAPI.buildStayDays(inputs);

        Pet updated = null;

        // -------------------------
        // TYPE-SPECIFIC CREATION
        // -------------------------
        if (existing instanceof Dog) {

            char sex = ScannerInput.readNextChar("Sex (M/F): ");
            boolean vaccinated = ScannerInput.readNextLine("Vaccinated (y/n): ").equalsIgnoreCase("y");
            double weight = ScannerInput.readNextDouble("Weight: ");
            boolean neutered = ScannerInput.readNextLine("Neutered (y/n): ").equalsIgnoreCase("y");

            String breed = ScannerInput.readNextLine("Breed: ");
            boolean dangerous = ScannerInput.readNextLine("Dangerous (y/n): ").equalsIgnoreCase("y");

            updated = new Dog(name, age, owner,
                    existing.getId(),   // KEEP SAME ID
                    sex, vaccinated, weight, neutered,
                    breed, dangerous);
        } else if (existing instanceof Cat) {

            System.out.println(CatToyUtility.getCatToys());

            char sex = ScannerInput.readNextChar("Sex (M/F): ");
            boolean vaccinated = ScannerInput.readNextLine("Vaccinated (y/n): ").equalsIgnoreCase("y");
            double weight = ScannerInput.readNextDouble("Weight: ");
            boolean neutered = ScannerInput.readNextLine("Neutered (y/n): ").equalsIgnoreCase("y");

            String toy = ScannerInput.readNextLine("Favourite toy: ");
            boolean indoor = ScannerInput.readNextLine("Indoor (y/n): ").equalsIgnoreCase("y");

            updated = new Cat(name, age, owner,
                    existing.getId(),
                    sex, vaccinated, weight, neutered,
                    indoor, toy);
        } else if (existing instanceof Parrot) {

            char sex = ScannerInput.readNextChar("Sex (M/F): ");
            boolean vaccinated = ScannerInput.readNextLine("Vaccinated (y/n): ").equalsIgnoreCase("y");
            double weight = ScannerInput.readNextDouble("Weight: ");
            boolean neutered = ScannerInput.readNextLine("Neutered (y/n): ").equalsIgnoreCase("y");

            double wingSpan = ScannerInput.readNextDouble("Wing span: ");
            boolean canFly = ScannerInput.readNextLine("Can fly (y/n): ").equalsIgnoreCase("y");

            int vocab = ScannerInput.readNextInt("Vocabulary size: ");

            updated = new Parrot(name, age, owner,
                    existing.getId(), sex, vaccinated, weight, neutered,
                    wingSpan, canFly, vocab);
        }

        // -------------------------
        // FINAL UPDATE CALL
        // -------------------------
        if (updated != null) {
            updated.setDaysAttending(stay);
            boolean success = api.updatePet(index, updated);

            if (success) {
                System.out.println("Pet updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        }
    }



    //-----------------------------------------------------------------
    //  Private methods for Counting facility
    //-----------------------------------------------------------------
    private void countingMenu() {
        int option;

        do {
            System.out.println("""
                    -------------Counting------------
                    | 1. Number of Pets             |
                    | 2. Number of Dogs             |
                    | 3. Number of Dangerous Dogs   |
                    | 4. Number of Cats             |
                    | 5. Number of Indoor Cats      |
                    | 6. Number of Parrots          |
                    | 7. Number of Parrots by Vocab |
                    |-------------------------------|
                    | 0. Return to Main Menu        |
                    |-------------------------------|
                    """);

            option = ScannerInput.readNextInt("Select: ");

            switch (option) {
                case 1 -> System.out.println(api.numberOfPets());
                case 2 -> System.out.println(api.numberOfDogs());
                case 3 -> System.out.println(api.numberOfDangerousDogs());
                case 4 -> System.out.println(api.numberOfCats());
                case 5 -> System.out.println(api.numberOfIndoorCats());
                case 6 -> System.out.println(api.numberOfParrots());
                case 7 ->{
                    String vocab = ScannerInput.readNextLine("Enter vocabulary: ");
                    System.out.println(api.numberOfParrotsByVocabularySize(vocab));}
                }


        } while (option != 0);
    }


    //-----------------------------------------------------------------
    //  Private methods for Search facility
    //-----------------------------------------------------------------
    private void searchMenu() {
        int option;

        do {
            System.out.println("""
                    -----------------Search-----------------
                    | 1. Find Dog by owner, breed and, age |
                    | 2. Find Pet by owner name            |
                    | 3. Find Pet by name                  |
                    | 4. Find Pet by ID                    |
                    | 5. Find Pet by Index                 |
                    |--------------------------------------|
                    | 0. Return to Main Menu               |
                    |--------------------------------------|
                    """);

            option = ScannerInput.readNextInt("Select: ");

            switch (option) {

                case 1 ->{
                    String name = ScannerInput.readNextLine("Enter name: ");
                    String breed = ScannerInput.readNextLine("Enter breed: ");
                    int age = ScannerInput.readNextInt("Enter age: ");
                    System.out.println(api.findDogByOwnerAndBreedAndAge(name, breed, age));
                }
                case 2 -> {
                    String name = ScannerInput.readNextLine("Enter owners name: ");
                    System.out.println(api.getPetsByOwnersName(name));
                }
                case 3 -> {
                    String name = ScannerInput.readNextLine("Enter pets name: ");
                    System.out.println(api.getPet(name));
                }
                case 4 -> {
                    int id = ScannerInput.readNextInt("Enter pets ID: ");
                    System.out.println(api.getPetById(id));
                }
                case 5 -> {
                    int index = ScannerInput.readNextInt("Enter pets index: ");
                    System.out.println(api.getPet(index));
                }
            }
        } while (option != 0);
    }


    //-----------------------------
    //  Private methods for Reports
    // ----------------------------
    private void reportsMenu() {
        int option;

        do {
            System.out.println("""
                    --------------Reports---------------
                    | 1. List All Pets                 |
                    | 2. List Dogs                     |
                    | 3. List Cats                     |
                    | 4. List Parrots                  |
                    | 5. List Dangerous Dogs           |
                    | 6. List Indoor Cats              |
                    | 7. List dogs older than age      |
                    | 8. List cats by favourite toy    |
                    | 9. List Neutered Animals         |
                    | 10.List Pets by Owner            |
                    | 11.List Pets that Stay x Days    |
                    | 12.Show weekly income            |
                    | 13.Show Average length of stay   |
                    |----------------------------------|
                    | 0. Return to Main Menu           |
                    |----------------------------------|
                    """);

            option = ScannerInput.readNextInt("Select: ");

            switch (option) {
                case 1 -> System.out.println(api.listAllPets());
                case 2 -> System.out.println(api.listAllDogs());
                case 3 -> System.out.println(api.listAllCats());
                case 4 -> System.out.println(api.listAllParrots());
                case 5 -> System.out.println(api.listAllDangerousDogs());
                case 6 -> System.out.println(api.listAllIndoorCats());
                case 7 ->{
                        int age = ScannerInput.readNextInt("Enter age: ");
                        System.out.println(api.listDogsOlderThanAge(age));}
                case 8 ->{
                        System.out.println(CatToyUtility.getCatToys());
                        String toy = ScannerInput.readNextLine("Enter favourite toy: ");
                        System.out.println(api.listAllCatsByFavouriteToy(toy));}
                case 9 -> System.out.println(api.listAllNeuteredAnimals());
                case 10 -> {
                    String name = ScannerInput.readNextLine("Enter Owner name: ");
                    System.out.println(api.listAllPetsByOwner(name));}
                case 11 -> {
                        int days = ScannerInput.readNextInt("Enter Days Staying: ");
                        System.out.println(api.listAllPetsThatStayMoreThanDays(days));}
                case 12 -> System.out.println("Weekly Income: " + api.getWeeklyIncome());
                case 13 -> System.out.println("Average Days: " + api.getAverageNumDaysPerWeek());
            }

        } while (option != 0);
    }

    //-----------------------------
    //  Private methods for Sorting
    // ----------------------------

    private void sortingMenu() {
        int option;

        do {
            System.out.println("""
                    ---------Sorting----------
                    | 1. Sort by ID          |
                    | 2. Sort by Name        |
                    |------------------------|
                    | 0. Return to Main Menu |
                    |------------------------|
                    """);

            option = ScannerInput.readNextInt("Select: ");

            switch (option) {
                case 1 -> {
                    api.sortPetsById();
                    System.out.println("Sorted by ID");
                }
                case 2 -> {
                    api.sortPetsByName();
                    System.out.println("Sorted by Name");
                }
            }
        } while (option != 0);
    }


    //---------------------------------
    //  Private methods for Persistence
    // --------------------------------
    private void save() {
        try {
            api.save();
            ownerAPI.save();
            System.out.println("Saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving");
        }
    }

    private void load() {
        try {
            api.load();
            ownerAPI.load();
            System.out.println("Loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading");
        }
    }

}
