package models;

import utils.Utilities;

public abstract class Bird extends Pet {

    private double wingSpan = 0;
    private boolean canFly = false;
    private char sex = 'U';          // default Unknown
    private boolean vaccinated = false;
    private double weight = 2;       // default minimum
    private boolean neutered = false;

    // -------------------------
    // Constructor
    // -------------------------
    public Bird(String name, int age, Owner owner, int id, char sex, boolean vaccinated,
                double weight, boolean neutered,
                double wingSpan, boolean canFly) {

        super(name, age, owner, id);

        setSex(sex);
        setNeutered(neutered);
        setVaccinated(vaccinated);
        setWeight(weight);
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

    public char getSex() {return sex;}

    public boolean isVaccinated() {return vaccinated;}

    public double getWeight() {return weight;}

    public boolean isNeutered() {return neutered;}

    // -------------------------
    // Setters (validate only)
    // -------------------------
    public void setSex(char sex) {
        sex = Character.toUpperCase(sex);

        if (isValidSex(sex)) {
            this.sex = sex;
        }
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    //Helper
    private boolean isValidSex(char sex) {
        return sex == 'M' || sex == 'F' || sex == 'U';
    }


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
                ", sex: " + sex +
                ", vaccinated: " + (vaccinated ? "Yes" : "No") +
                ", weight: " + weight +
                ", neutered: " + (neutered ? "Yes" : "No") +
                ", WingSpan: " + wingSpan +
                ", canFly: " + (canFly ? "Yes" : "No");
    }
}
