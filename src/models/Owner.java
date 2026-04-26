package models;


import java.util.Objects;
// this class is fully functional
public class Owner {
    private  int id = 100;// 3 digits - default 100
    private String name; // <= 30  //number should be stored with no spaces, start with 0 and have 10 digits in ir
    private String phoneNumber = "087302000";

    public Owner(int id, String name, String phoneNumber) {
        setId(id);
        initName(name);
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        if (id >=100 && id <= 999)
            this.id = id;
    }
    public void initName(String name) {

        this.name = (name.length()<= 30?
                name :
                name.substring(0,30));
    }


    public void setName(String name) {
        if (name.length() <=30)  this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (onlyContainsNumbers(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return getId() == owner.getId() && Objects.equals(getName(), owner.getName());
    }


    private boolean onlyContainsNumbers(String text) {
        return (text.matches("[0-9]+"));
    }
    @Override
    public String toString() {
        return
                "Id: " + id + ", name: " + name + ", phone: " + phoneNumber;

    }}

