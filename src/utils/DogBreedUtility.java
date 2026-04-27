package utils;

import java.util.HashSet;
import java.util.Set;

public class DogBreedUtility {

    private static final Set<String> dogBreeds = new HashSet<>();
     static {
         // Add breeds
         dogBreeds.add("LABRADOR RETRIEVER");
         dogBreeds.add("GERMAN SHEPHERD");
         dogBreeds.add("GOLDEN RETRIEVER");
         dogBreeds.add("BULLDOG");
         dogBreeds.add("BEAGLE");
         dogBreeds.add("ROTTWEILER");
         dogBreeds.add("PIT BULL");
     }
        public static boolean checkBreed(String breed) {
        return dogBreeds.contains(breed.toUpperCase());
    }


    public static String mapBreed(String breed) {
        String b = breed.toUpperCase();

        if (b.contains("LAB")) return "LABRADOR RETRIEVER";
        if (b.contains("GERMAN")) return "GERMAN SHEPHERD";
        if (b.contains("GOLDEN")) return "GOLDEN RETRIEVER";
        if (b.contains("BULLDOG")) return "BULLDOG";
        if (b.contains("BEAGLE")) return "BEAGLE";
        if (b.contains("ROTTWEILER")) return "ROTTWEILER";
        if (b.contains("PIT")) return "PIT BULL";

        return b;
    }
    @SuppressWarnings("EnhancedSwitchMigration")
    public static String formatBreed(String breed) {
        switch (breed) {
            case "LABRADOR RETRIEVER":
                return "Labrador";
            case "GERMAN SHEPHERD":
                return "German Shepherd";
            case "GOLDEN RETRIEVER":
                return "Golden Retriever";
            case "BULLDOG":
                return "Bulldog";
            case "BEAGLE":
                return "Beagle";
            case "ROTTWEILER":
                return "Rottweiler";
            case "PIT BULL":
                return "Pit Bull";
            default:
                return breed;
        }
    }
}
