package utils;

import java.util.HashSet;
import java.util.Set;

public class DogBreedUtility {

    private static  Set<String> dogBreeds = new HashSet<>();
    public static boolean checkBreed(String breed) {


        // Add breeds
        dogBreeds.add("LABRADOR RETRIEVER");
        dogBreeds.add("GERMAN SHEPHERD");
        dogBreeds.add("GOLDEN RETRIEVER");
        dogBreeds.add("BULLDOG");
        dogBreeds.add("BEAGLE");
        dogBreeds.add("ROTTWEILER");
        dogBreeds.add("PIT BULL");

        return dogBreeds.contains(breed.toUpperCase());
    }

}
