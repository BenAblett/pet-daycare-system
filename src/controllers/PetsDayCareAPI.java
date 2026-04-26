 //TODO - Uncomment when you are ready to go

package controllers;

 import com.thoughtworks.xstream.XStream;
 import com.thoughtworks.xstream.io.xml.DomDriver;
 import models.*;
 import utils.ISerializer;
 import utils.Utilities;

 import java.io.*;
 import java.util.ArrayList;



 public class PetsDayCareAPI implements ISerializer {

     private ArrayList<Pet> pets;
     private String name;
     private int maxNumberOfPets;
     private File file;

     //-------------------------------------
     //  Constructor
     //-------------------------------------
     //TODO array list of pets, should be empty at the start.
     public PetsDayCareAPI(String name, int maxNumberOfPets, File file) {
         this.pets = new ArrayList<>();

         setName(name);

         setMaxNumberOfPets(maxNumberOfPets);

         this.file = file;

         //TODO array list of pets, should be empty at the start.
         //     When creating the PetsDayCare, truncate the name to 20 characters.
         //     When updating an existing DayCare, only update the name if it is 10 characters or less.
         //     number of pets must be must be >= 10 <= 100 default to 10
         //     file  is the name of the file that you will load from / save to
     }

     //-------------------------------------
     //  Setters/Getters
     //-------------------------------------
     public String getName() {
         return name;
     }

     public int getMaxNumberOfPets() {
         return maxNumberOfPets;
     }

     public ArrayList<Pet> getPetsArray() {
         return pets;
     }

     public void setName(String name) {
         if (name != null && name.length() <= 10) {
             this.name = name;
         }
     }

     public void setMaxNumberOfPets(int maxNumberOfPets) {
         if (Utilities.validRange(maxNumberOfPets, 10, 100)) {
             this.maxNumberOfPets = maxNumberOfPets;
         }
     }

     public void setPetsArray(ArrayList<Pet> pets) {
         if (pets != null) {
             this.pets = pets;
         }
     }


     //TODO Add a getter and setter for each field, that adheres to the  validation rules as per spec


     //-------------------------------------
     //  Pet ARRAYLIST CRUD
     //-------------------------------------
     public boolean addPet(Pet pet) {
         return pets.add(pet);
     }

     public void deletePetByIndex(int index) {
         if (isValidPetIndex(index)) {
             pets.remove(index);
         }
     }

     public Pet deletePetById(int id) {
         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i).getId() == id) {
                 return pets.remove(i);
             }
         }
         return null;
     }

     public Pet getPet(int index) {
         if (isValidPetIndex(index)) {
             return pets.get(index);
         }
         return null;
     }

     public Pet getPet(String name) {
         if (name == null) return null;

         for (Pet pet : pets) {
             if (pet.getName().equalsIgnoreCase(name)) {
                 return pet;
             }
         }
         return null;
     }

     public Pet getPetById(int id) {
         for (Pet pet : pets) {
             if (pet.getId() == id) {
                 return pet;
             }
         }
         return null;
     }
     //-------------------------------------
     //  Pet ARRAYLIST - Utility methods
     //-------------------------------------
     // e.g validation methods
     //TODO Add a method isValidIndex(int) which returns an boolean -
     //      - returns true if the index is valid for the pets arrayList (in range)
     //      - returns false otherwise
     //      As this method is used inside this class, it should be private
     private boolean isValidPetIndex(int index) {
         return index >= 0 && index < pets.size();
     }

     public Pet updatePet(int index, Pet updatedPet) {
         if (isValidPetIndex(index) && updatedPet != null) {
             return pets.set(index, updatedPet);
         }
         return null;
     }

     public double getWeeklyIncome() {
         double total = 0;

         for (Pet p : pets) {
             total += p.calculateWeeklyFee();
         }

         return total;
     }

     public int getAverageNumDaysPerWeek() {
         if (pets.isEmpty()) return 0;

         int totalDays = 0;

         for (Pet p : pets) {
             totalDays += p.numOfDaysAttending();
         }

         return totalDays / pets.size();
     }

     // Convert y/n inputs into boolean[]
     public static boolean[] buildStayDays(String[] inputs) {
         if (inputs == null || inputs.length != 7) {
             return new boolean[7]; // default: all false
         }

         boolean[] days = new boolean[7];

         for (int i = 0; i < 7; i++) {
             days[i] = inputs[i] != null &&
                     inputs[i].equalsIgnoreCase("y");
         }

         return days;
     }

     // Optional: pretty print days (useful for toString/debugging)
     public static String formatDays(boolean[] days) {
         if (days == null) return "None";

         String[] names = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
         String result = "";

         for (int i = 0; i < days.length; i++) {
             if (days[i]) {
                 result += names[i] + " ";
             }
         }

         return result.isEmpty() ? "None" : result.trim();
     }


     // TODO  Add a method  getPet(int) which returns a Pet object:
     //       - if the supplied index is valid, the Pet object at that location is returned
     //       - if the supplied index is invalid, null is returned

     //TODO  Add a method  getPet(String) which returns a Pet object:
     //       - if the supplied name is found, the first Dog object with that name is returned
     //       - if the supplied name is not found, null is returned


     //TODO  Add a method  getPetById(int) which returns a Pet object:
     //       - if the supplied id is found, the Pet object with that id is returned
     //       - if the supplied id is not found, null is returned


     //------------------------------------
     // LISTING METHODS - Basic and Advanced
     //------------------------------------
//TODO e.g. Add a method, listAllPets().  The return type is String.
//     This method returns a list of the pets stored in the array list.
//     Each pet should be on a new line and should be preceded by the index number e.g.
//        0: pet 1 Details
//        1: pet 2 Details
//    If there are no pets stored in the array list, return a string that contains "There are no pets registered at the moment".

//TODO +  and the remaining listing methods as per spec

     public String listAllPets() {
         if (pets.isEmpty()) return "No Pets";

         String result = "";
         for (int i = 0; i < pets.size(); i++) {
             result += i + ": " + pets.get(i) + "\n";
         }
         return result.trim();
     }

     public String listAllCats() {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i) instanceof Cat) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty() ? "No cats" : result.trim();
     }

     public String listAllDogs() {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i) instanceof Dog) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty() ? "No Dogs" : result.trim();
     }

     public String listAllParrots() {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i) instanceof Parrot) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty() ? "No Parrots" : result.trim();
     }

     public String listAllDangerousDogs() {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i) instanceof Dog) {
                 Dog d = (Dog) pets.get(i);
                 if (d.isDangerousBreed()) {
                     result += i + ": " + d + "\n";
                 }
             }
         }

         if (result.isEmpty()) return "No Dangerous Dogs in the Kennels";
         return result.trim();
     }

     public String listAllIndoorCats() {
         if (pets.isEmpty()) return "No Pets";

         String result = "";
         int index = 0;

         for (Pet pet : pets) {
             if (pet instanceof Cat cat) {
                 if (cat.isIndoorCat()) {
                     result += index + ": " + cat + "\n";
                 }
             }
             index++;
         }

         if (result.isEmpty()) return "No Indoor Cats";
         return result;
     }

     public String listAllCatsByFavouriteToy(String toy) {
         if (pets.isEmpty()) return "No Pets";

         String result = "";
         int index = 0;

         for (Pet pet : pets) {
             if (pet instanceof Cat cat) {
                 if (cat.getFavouriteToy() != null &&
                         cat.getFavouriteToy().equalsIgnoreCase(toy)) {

                     result += index + ": " + cat + "\n";
                 }
             }
             index++;
         }

         if (result.isEmpty()) {
             return "No Cats with favourite toy " + toy;
         }

         return result;
     }

     public String listDogsOlderThanAge(int age) {
         if (pets.isEmpty()) return "No Pets";

         String result = "";
         int index = 0;

         for (Pet pet : pets) {
             if (pet instanceof Dog dog) {
                 if (dog.getAge() > age) {
                     result += index + ": " + dog + "\n";
                 }
             }
             index++;
         }

         if (result.isEmpty()) return "No Dogs older than " + age;
         return result;
     }

     public String listAllNeuteredAnimals() {
         if (pets.isEmpty()) return "No Pets";

         String result = "";
         int index = 0;

         for (Pet pet : pets) {
             if (pet instanceof Mammal mammal) {
                 if (mammal.isNeutered()) {
                     result += index + ": " + mammal + "\n";
                 }
             }
             index++;
         }

         if (result.isEmpty()) return "No Neutered Animals";
         return result;
     }


     public String listAllPetsByOwner(String ownerName) {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i).getOwner().getName().equalsIgnoreCase(ownerName)) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty()
                 ? "No Pet with owner " + ownerName
                 : result.trim();
     }

     public String listAllPetsThatStayMoreThanDays(int numDays) {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i).numOfDaysAttending() > numDays) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty()
                 ? "No Pet stays longer than " + numDays
                 : result.trim();
     }
     //-------------------------------------
     //  Counting Methods
     //-------------------------------------
     // TODO all the counting methods e.g.
     //TODO Add a method, numberOfPets().  The return type is int.
     //     This method returns the number of pets objects currently stored in the array list.

     //TODO Add a method, numberOfDogs().  The return type is int.
     //     This method returns the number of  dogs objects currently stored in the array list.


     //TODO Add a method, getWeeklyIncome().  The return type is double.
     //     This method returns the amount received from all the pets per week.

     //TODO Add a method, getAverageNumDaysPerWeek().  The return type is int.
     //     This method returns the average number of days pets stay in the Day Care.

     public int numberOfPets() {
         return pets.size();
     }

     public int numberOfCats() {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Cat) count++;
         }

         return count;
     }

     public int numberOfDogs() {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Dog) count++;
         }

         return count;
     }

     public int numberOfParrots() {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Parrot) count++;
         }

         return count;
     }

     public int numberOfDangerousDogs() {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Dog && ((Dog) p).isDangerousBreed()) {
                 count++;
             }
         }

         return count;
     }

     public int numberOfIndoorCats() {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Cat && ((Cat) p).isIndoorCat()) {
                 count++;
             }
         }

         return count;
     }

     public int numberOfParrotsByVocabularySize(String vocabSize) {
         int count = 0;

         for (Pet p : pets) {
             if (p instanceof Parrot) {
                 Parrot parrot = (Parrot) p;
                 if (parrot.getVocabularySize().equalsIgnoreCase(vocabSize)) {
                     count++;
                 }
             }
         }

         return count;
     }

     //------------------------------
     //  FINDING METHODS
     //-------------------------------
     // TOD Add all the find methods

     public Pet findDogByOwnerAndBreedAndAge(String name, String breed, int age) {
         for (Pet p : pets) {
             if (p instanceof Dog) {
                 Dog d = (Dog) p;

                 if (d.getOwner().getName().equalsIgnoreCase(name) &&
                         d.getBreed().equalsIgnoreCase(breed) &&
                         d.getAge() == age) {

                     return d;
                 }
             }
         }
         return null;
     }

     public String getPetsByOwnersName(String name) {
         String result = "";

         for (int i = 0; i < pets.size(); i++) {
             if (pets.get(i).getOwner().getName().equalsIgnoreCase(name)) {
                 result += i + ": " + pets.get(i) + "\n";
             }
         }

         return result.isEmpty()
                 ? "No Pets for " + name
                 : result.trim();
     }

     //------------------------------
     //  SEARCHING METHODS
     //-------------------------------

     // TODO Searching methods as per spec.

     //--------------------------------
     // Sorting methods
     //
     //--------------------------------
     //  Sorting methods as per spec .
     // NOTE : THESE MUST BE WRITTEN WITHOUT THE USE OF THE SORT LIBRARY. If in doubt, just ask.

     private void swapPets(int i, int j) {
         Pet temp = pets.get(i);
         pets.set(i, pets.get(j));
         pets.set(j, temp);
     }

     private void swapPets(Pet p1, Pet p2) {
         int i = pets.indexOf(p1);
         int j = pets.indexOf(p2);

         if (i != -1 && j != -1) {
             swapPets(i, j);
         }
     }

     public void sortPetsById() {
         for (int i = 0; i < pets.size(); i++) {
             int maxIndex = i;

             for (int j = i + 1; j < pets.size(); j++) {
                 if (pets.get(j).getId() > pets.get(maxIndex).getId()) {
                     maxIndex = j;
                 }
             }

             swapPets(i, maxIndex);
         }
     }

     public void sortPetsByName() {
         for (int i = 0; i < pets.size(); i++) {
             int minIndex = i;

             for (int j = i + 1; j < pets.size(); j++) {
                 if (pets.get(j).getName()
                         .compareToIgnoreCase(pets.get(minIndex).getName()) < 0) {

                     minIndex = j;
                 }
             }

             swapPets(i, minIndex);
         }
     }

     //  Methods for Persistence
     // --------------------------------
     //---------------------------------
     //  Methods for Persistence
     // --------------------------------

     //TODO Add a method, load().  The return type is void.
     //    This method uses the XStream component to deserialise the Pets array list  from
     //    an XML file into the Pets array list.


     //TODO Add a method, save().  The return type is void.
     //    This method uses the XStream component to serialise the Pets array list object  to
     //    an XML file.

     public void load() throws Exception {
         //list of classes that you wish to include in the serialisation, separated by a comma
         Class<?>[] classes = new Class[] {
                 models.Dog.class,
                 models.Cat.class,
                 models.Mammal.class,
                 models.Bird.class,
                 models.Parrot.class,
                 models.Owner.class };

         //setting up the xstream object with default security and the above classes
         XStream xstream = new XStream(new DomDriver());
         XStream.setupDefaultSecurity(xstream);
         xstream.allowTypes(classes);

         //doing the actual serialisation to an XML file
         ObjectInputStream is = xstream.createObjectInputStream(new FileReader(file));
         pets = (ArrayList<Pet>) is.readObject();
         is.close();
     }

     @Override
     public String fileName() {
         return file.getName();
     }

     @Override
     public void save() throws Exception {

         Class<?>[] classes = new Class[] {
                 models.Dog.class,
                 models.Cat.class,
                 models.Mammal.class,
                 models.Bird.class,
                 models.Parrot.class,
                 models.Owner.class
         };

         XStream xstream = new XStream(new DomDriver());
         XStream.setupDefaultSecurity(xstream);
         xstream.allowTypes(classes);

         ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(file));
         out.writeObject(pets);
         out.close();
     }

 }



