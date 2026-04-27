package models;

import utils.BirdUtility;

public class Parrot extends Bird{

    private String vocabularySize = "Amazing";
    private int vocabularyLevel;

    // -------------------------
    // Constructor
    // -------------------------
    public Parrot(String name, int age, Owner owner, int id,
                  char sex, boolean vaccinated, double weight, boolean neutered,
                  double wingSpan, boolean canFly, int vocabularySize) {

        super(name, age, owner, id, sex, vaccinated, weight, neutered, wingSpan, canFly);

        // Convert int → String using utility
        setVocabularySize(vocabularySize);
    }

    // -------------------------
    // Getter
    // -------------------------
    public String getVocabularySize() {
        return vocabularySize;
    }
    public int getVocabularyLevel() {
        return vocabularyLevel;
    }


    // -------------------------
    // Setter (convert int → String)
    // -------------------------
    public void setVocabularySize(int vocabularySize) {
        if (vocabularySize >=0) {
            this.vocabularySize = BirdUtility.getVocabularyLevel(vocabularySize);
            this.vocabularyLevel = vocabularySize; //Stores int value as well as transforming it
        }
    }

    // -------------------------
    // Business Logic
    // -------------------------
    @Override
    public double calculateWeeklyFee() {
        return 10 * numOfDaysAttending();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        if (!super.equals(obj)) return false;

        Parrot other = (Parrot) obj;

        return this.vocabularyLevel == other.vocabularyLevel &&
                this.vocabularySize.equalsIgnoreCase(other.vocabularySize);
    }
    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "[Parrot] " + super.toString() +
                ", vocabularySize=" + vocabularySize +
                ", Weekly Fee: " + calculateWeeklyFee();
    }
}
