package models;

import utils.Utilities;

public abstract class Pet {

    private static  int nextId=1000;

    private int id;
    private String name;
    private Owner owner;
    private boolean[] daysAttending;
    private int age;

    public Pet(String name, int age, Owner owner, int id) {
        setName(name);
        setAge(age);
        setOwner(owner);
        setId(id);
    }

    //Getters
    public int getId() {return id;}

    public String getName(){return name;}

    public Owner getOwner(){return owner;}

    public int getAge(){return age;}

    public boolean[] getDaysAttending() {return daysAttending;}



    //Setters
    public void setName(String name) {
        this.name = Utilities.truncateString(name,30);
    }

    public void setAge(int age){
        if (Utilities.validRange(age, 0, 20)){
            this.age = age;
        }
    }

    public void setOwner(Owner owner){
        if (owner != null) {
            this.owner = owner;
        }
    }

    public void setDaysAttending(boolean[] daysAttending){
        if (daysAttending != null && daysAttending.length == 7) {
            this.daysAttending = daysAttending;
        }
    }

    public void setId(int id){
        if (id >= 1000) {
            this.id = id;
        } else {
            this.id = nextId++;
        }
    }

    //Attendance
    public void checkIn(int dayIndex) {
        if (Utilities.validRange(dayIndex, 0, 6)) {
            daysAttending[dayIndex] = false;
        }
    }

    public void checkOut(int dayIndex) {
        if (Utilities.validRange(dayIndex, 0, 6)) {
            daysAttending[dayIndex] = false;
        }
    }

    public int numOfDaysAttending() {
        int count = 0;
        for (boolean day : daysAttending) {
            if (day) count++;
        }
        return count;
    }

    //Helper
    private String getDaysString() {
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        String result = "";

        for (int i = 0; i < daysAttending.length; i++) {
            if (daysAttending[i]) {
                result += dayNames[i] + " ";
            }
        }

        if (result.isEmpty()) {
            return "None";
        }

        return result.trim();
    }

    //Abstract
    public abstract double calculateWeeklyFee();

    //toString
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", owner=" + owner +
                ", daysAttending=" + numOfDaysAttending() +
                '}';
    }
}
