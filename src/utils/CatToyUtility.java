package utils;

import java.util.HashMap;
import java.util.Map;

public class CatToyUtility {

    // Create a HashMap where the key is the toy name (uppercase)
    // and the value is a description or recommended age

    private static final Map<String, String> catToys = new HashMap<>(){{
        put("FEATHER WAND", "Interactive toy for all ages");
        put("LASER POINTER", "Supervised play only");
        put("BALL OF YARN", "Suitable for kittens");
        put("MICE TOY", "Safe for indoor cats");
        put("CATNIP MOUSE", "Encourages active play");
        put("SCRATCHING POST", "Essential for claw maintenance");
        put("TUNNEL", "Fun for hiding and running");  }};


    @SuppressWarnings("StringConcatenationInLoop")
    static public String getCatToys() {
        // Display all toys with info
        String info = "Cat Toys and Info:";
        for (Map.Entry<String, String> entry : catToys.entrySet()) {
            info += "- " + entry.getKey() + " | Info: " + entry.getValue() + "\n";
        }
        return info;
    }

    // Check for a valid toy
    static public boolean isCatToy(String catToy) {
        return catToys.containsKey(catToy);
    }


}
