 //TODO - Uncomment when you are ready to go

package controllers;

 import com.thoughtworks.xstream.XStream;
 import com.thoughtworks.xstream.io.xml.DomDriver;
 import models.*;
 import utils.ISerializer;
 import utils.Utilities;

 import java.io.*;
 import java.util.ArrayList;



 @SuppressWarnings({"StringConcatenationInLoop", "deprecation", "unchecked"})
 public class PetsDayCareAPI implements ISerializer {

     private ArrayList<Pet> pets;
     private String name;
     private int maxNumberOfPets;
     private final File file;

     //-------------------------------------
     //  Constructor
     //-------------------------------------
     public PetsDayCareAPI(String name, int maxNumberOfPets, File file) {
         this.pets = new ArrayList<>();

         setName(name);

         setMaxNumberOfPets(maxNumberOfPets);

         this.file = file;
     }

     // -------------------------
     // ID GENERATION
     // -------------------------


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
         if (name != null) {
             this.name = Utilities.truncateString(name, 20);
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
         if (pets.size() >= maxNumberOfPets) return false;
         return pets.add(pet);
     }

     public Pet deletePetByIndex(int index) {
         if (isValidPetIndex(index)) {
            return pets.remove(index);
         }
         return null;
     }

     public Pet deletePetById(int id) {
         for (Pet pet : pets) {
             if (pet.getId() == id) {
                 pets.remove(pet);
                 return pet;
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

     public boolean updatePet(int index, Pet updated) {

         Pet existing = getPet(index);

         if (existing != null && updated != null) {

             // -------- COMMON FIELDS --------
             existing.setName(updated.getName());
             existing.setAge(updated.getAge());
             existing.setOwner(updated.getOwner());
             existing.setDaysAttending(updated.getDaysAttending());

             // -------- TYPE-SPECIFIC --------
             if (existing instanceof Dog e && updated instanceof Dog u) {

                 e.setSex(u.getSex());
                 e.setVaccinated(u.isVaccinated());
                 e.setWeight(u.getWeight());
                 e.setNeutered(u.isNeutered());
                 e.setBreed(u.getBreed());
                 e.setDangerousBreed(u.isDangerousBreed());
             }

             else if (existing instanceof Cat e && updated instanceof Cat u) {

                 e.setSex(u.getSex());
                 e.setVaccinated(u.isVaccinated());
                 e.setWeight(u.getWeight());
                 e.setNeutered(u.isNeutered());
                 e.setFavouriteToy(u.getFavouriteToy());
                 e.setIndoorCat(u.isIndoorCat());
             }

             else if (existing instanceof Parrot e && updated instanceof Parrot u) {

                 e.setWingSpan(u.getWingSpan());
                 e.setCanFly(u.isCanFly());
                 e.setVocabularySize(u.getVocabularyLevel());
             }

             return true;
         }

         return false;
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

     //------------------------------------
     // LISTING METHODS - Basic and Advanced
     //------------------------------------

     @SuppressWarnings("StringConcatenationInLoop")
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
             if (pets.get(i) instanceof Dog d) {
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

     public double getWeeklyIncome() {
         double total = 0;

         for (Pet p : pets) {
             total += p.calculateWeeklyFee();
         }

         return Utilities.toTwoDecimalPlaces(total);
     }

     public int getAverageNumDaysPerWeek() {
         if (pets.isEmpty()) return 0;

         int totalDays = 0;

         for (Pet p : pets) {
             totalDays += p.numOfDaysAttending();
         }

         return totalDays / pets.size();
     }

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
             if (p instanceof Parrot parrot) {
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
             if (p instanceof Dog d) {

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

     public void load() throws Exception {
         //list of classes that you wish to include in the serialization, separated by a comma
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

         //doing the actual serialization to an XML file
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



