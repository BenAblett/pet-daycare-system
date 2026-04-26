 //TODO - Uncomment when you are ready to go

package controllers;

 import models.Pet;
 import utils.ISerializer;
 import utils.Utilities;

 import java.io.File;
 import java.util.ArrayList;


 public class PetsDayCareAPI implements ISerializer {
     @Override
     public void save() throws Exception {

     }

     @Override
     public void load() throws Exception {

     }

     @Override
     public String fileName() {
         return "";
     }

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

         // name → truncate to 20 chars
         this.name = Utilities.truncateString(name, 20);

         // max pets → 10–100, default 10
         if (Utilities.validRange(maxNumberOfPets, 10, 100)) {
             this.maxNumberOfPets = maxNumberOfPets;
         } else {
             this.maxNumberOfPets = 10;
         }

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
 }


    //TODO Add a getter and setter for each field, that adheres to the  validation rules as per spec


    //-------------------------------------
    //  Pet ARRAYLIST CRUD
    //-------------------------------------
    //TODO add the methods as per spec.

    //-------------------------------------
    //  Pet ARRAYLIST - Utility methods
    //-------------------------------------
    // e.g validation methods
    //TODO Add a method isValidIndex(int) which returns an boolean -
    //      - returns true if the index is valid for the pets arrayList (in range)
    //      - returns false otherwise
    //      As this method is used inside this class, it should be private


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


    //------------------------------
    //  FINDING METHODS
    //-------------------------------
    // TOD Add all the find methods

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





