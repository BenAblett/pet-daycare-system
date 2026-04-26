package models;

import utils.DogBreedUtility;

public class Dog extends Mammal {

    private String breed = "";
    private boolean dangerousBreed;

    public static final float DANGEROUS_DAILY_RATE = 40;
    public static final float NONDANGEROUS_DAILY_RATE = 30;

    // -------------------------
    // Constructor
    // -------------------------
    public Dog(String name, int age, Owner owner, int id,
               char sex, boolean vaccinated, double weight, boolean neutered,
               String breed, boolean dangerousBreed) {

        super(name, age, owner, id, sex, vaccinated, weight, neutered);

        // Constructor → validation applied
        if (DogBreedUtility.checkBreed(breed)) {
            this.breed = breed;
        }

        this.dangerousBreed = dangerousBreed;
    }

    // -------------------------
    // Getters
    // -------------------------
    public String getBreed() {
        return breed;
    }

    public boolean isDangerousBreed() {
        return dangerousBreed;
    }

    // -------------------------
    // Setters (validate only)
    // -------------------------
    public void setBreed(String breed) {
        if (DogBreedUtility.checkBreed(breed)) {
            this.breed = breed;
        }
    }

    public void setDangerousBreed(boolean dangerousBreed) {
        this.dangerousBreed = dangerousBreed;
    }

    // -------------------------
    // Business Logic
    // -------------------------
    @Override
    public double calculateWeeklyFee() {
        float rate = dangerousBreed ? DANGEROUS_DAILY_RATE : NONDANGEROUS_DAILY_RATE;
        return rate * numOfDaysAttending();
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return super.toString() +
                ", breed=" + breed +
                ", dangerousBreed=" + dangerousBreed;
    }
}
