package models;

import utils.BirdUtility;

public class Parrot extends Bird{

    private String vocabularySize = "Amazing";
    private int vocabularyLevel;

    // -------------------------
    // Constructor
    // -------------------------
    public Parrot(String name, int age, Owner owner, int id,
                  double wingSpan, boolean canFly, int vocabularySize) {

        super(name, age, owner, id, wingSpan, canFly);

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
        this.vocabularySize = BirdUtility.getVocabularyLevel(vocabularySize);
        this.vocabularyLevel = vocabularySize; //Stores int value as well as transforming it
    }

    // -------------------------
    // Business Logic
    // -------------------------
    @Override
    public double calculateWeeklyFee() {
        return 10 * numOfDaysAttending();
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return super.toString() +
                ", vocabularySize=" + vocabularySize;
    }
}
