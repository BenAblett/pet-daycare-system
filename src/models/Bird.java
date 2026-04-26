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
        setWingSpan(wingSpan);
        setCanFly(canFly);
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
        if (Utilities.validRange(wingSpan, 0, 400)) {
            this.wingSpan = wingSpan;
        }
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Bird other = (Bird) obj;

        return Double.compare(this.wingSpan, other.wingSpan) == 0 &&
                this.canFly == other.canFly;
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "Bird: " + super.toString() +
                ", WingSpan: " + wingSpan +
                ", canFly: " + (canFly ? "Yes" : "No");
    }
}
