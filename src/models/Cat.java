package models;


import utils.CatToyUtility;

public class Cat extends Mammal {

    private boolean indoorCat;
    private String favouriteToy = "not known";

    // -------------------------
    // Constructor
    // -------------------------
    public Cat(String name, int age, Owner owner, int id,
               char sex, boolean vaccinated, double weight, boolean neutered,
               boolean indoorCat, String favouriteToy) {

        super(name, age, owner, id, sex, vaccinated, weight, neutered);

        this.indoorCat = indoorCat;

        // Validation (case-insensitive fix)
        if (favouriteToy != null &&
                CatToyUtility.isCatToy(favouriteToy.toUpperCase())) {
            this.favouriteToy = favouriteToy;
        }
    }

    // -------------------------
    // Getters
    // -------------------------
    public boolean isIndoorCat() {
        return indoorCat;
    }

    public String getFavouriteToy() {
        return favouriteToy;
    }

    // -------------------------
    // Setters (validate only)
    // -------------------------
    public void setIndoorCat(boolean indoorCat) {
        this.indoorCat = indoorCat;
    }

    public void setFavouriteToy(String favouriteToy) {
        if (favouriteToy != null &&
                CatToyUtility.isCatToy(favouriteToy.toUpperCase())) {
            this.favouriteToy = favouriteToy;
        }
    }

    // -------------------------
    // Business Logic
    // -------------------------
    @Override
    public double calculateWeeklyFee() {
        double rate = 20;

        if (indoorCat) {
            rate += 5;
        }

        return rate * numOfDaysAttending();
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return super.toString() +
                ", indoorCat=" + indoorCat +
                ", favouriteToy=" + favouriteToy;
    }
}
