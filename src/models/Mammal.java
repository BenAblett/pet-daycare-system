package models;

public abstract class Mammal extends Pet{

    private char sex = 'U';          // default Unknown
    private boolean vaccinated = false;
    private double weight = 2;       // default minimum
    private boolean neutered = false;


    //Constructor
    public Mammal(String name, int age, Owner owner, int id,
                  char sex, boolean vaccinated, double weight, boolean neutered) {

        super(name, age, owner, id);

        setSex(sex);
        setNeutered(neutered);
        setVaccinated(vaccinated);
        setWeight(weight);
    }

    //Getters
    public char getSex() {return sex;}

    public boolean isVaccinated() {return vaccinated;}

    public double getWeight() {return weight;}

    public boolean isNeutered() {return neutered;}

    //Setters
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

    //toString
    @Override
    public String toString() {
        return super.toString() +
                ", sex: " + sex +
                ", vaccinated: " + (vaccinated ? "Yes" : "No") +
                ", weight: " + weight +
                ", neutered: " + (neutered ? "Yes" : "No");
    }


}
