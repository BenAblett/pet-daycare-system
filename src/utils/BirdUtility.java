package utils;

import java.util.TreeMap;

/**
 * Utility class that provides methods for determining a bird's
 * vocabulary level based on the number of words it knows.
 * The vocabulary levels are stored in a {@link TreeMap} where the key
 * represents the minimum number of words required for that level.
 */
public class BirdUtility {

    /**
     * A map that stores vocabulary thresholds and their corresponding labels.
     * The key represents the minimum number of words for that level.
     */
    private static final TreeMap<Integer, String> vocabularyLevels = new TreeMap<>();

    static {
        vocabularyLevels.put(0, "Minimal");
        vocabularyLevels.put(5, "Basic");
        vocabularyLevels.put(20, "Impressive");
        vocabularyLevels.put(50, "Amazing");
    }

    /**
     * Determines the vocabulary level of a bird based on the number of words it knows.
     * This method uses a {@link TreeMap} to find the closest vocabulary level
     * that is less than or equal to the provided number of words.
     * @param words the number of words the bird knows
     * @return the vocabulary level description
     */
    public static String getVocabularyLevel(int words) {
        return vocabularyLevels.floorEntry(words).getValue();
    }

    /**
     * Determines the vocabulary level of a bird based on the number of words it knows.
     * This method uses conditional statements instead of a {@link TreeMap}.
     * It produces the same result as {@link #getVocabularyLevel(int)}.
     *
     * @param numberOfWords the number of words the bird knows
     * @return the vocabulary level description
     */
    public static String getVocabularyLevel2(int numberOfWords) {

        if (numberOfWords <= 5) {
            return "Minimal";
        }
        else if (numberOfWords <= 20) {
            return "Basic";
        }
        else if (numberOfWords <= 50) {
            return "Impressive";
        }
        else {
            return "Amazing";
        }
    }
}
