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

        // Validation
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Cat other = (Cat) obj;

        return this.isIndoorCat() == other.isIndoorCat() &&
                this.getFavouriteToy().equalsIgnoreCase(other.getFavouriteToy());
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "[Cat] " + super.toString() +
                ", indoor: " + (indoorCat ? "Yes" : "No") +
                ", favouriteToy: " + favouriteToy +
                ", Weekly Fee: " + calculateWeeklyFee();
    }
}
