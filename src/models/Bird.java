package models;

import utils.Utilities;

public abstract class Bird extends Pet {

    private double wingSpan = 3;
    private boolean canFly = false;

    // -------------------------
    // Constructor
    // -------------------------
    public Bird(String name, int age, Owner owner, int id,
                double wingSpan, boolean canFly) {

        super(name, age, owner, id);

        if (Utilities.validRange(wingSpan, 3, 400)) {
            this.wingSpan = wingSpan;
        }

        this.canFly = canFly;
    }

    // -------------------------
    // Getters
    // -------------------------
    public double getWingSpan() {
        return wingSpan;
    }

    public boolean isCanFly() {
        return canFly;
    }

    // -------------------------
    // Setters (validate only)
    // -------------------------
    public void setWingSpan(double wingSpan) {
        if (Utilities.validRange(wingSpan, 3, 400)) {
            this.wingSpan = wingSpan;
        }
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return super.toString() +
                ", wingSpan=" + wingSpan +
                ", canFly=" + canFly;
    }
}
