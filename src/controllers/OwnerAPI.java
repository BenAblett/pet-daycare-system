package controllers;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Owner;
import utils.ISerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilities.isValidIndex;

//todo - class is fully functional
public class OwnerAPI implements ISerializer {

    private List<Owner> ownerList = new ArrayList<>();

    private File file;

    public OwnerAPI(File file)  {
        this.file = file;
    }

    //---------------------
    // Create methods
    //---------------------
    public boolean addOwner(Owner owner) {
        if (isValidOwner(owner.getName())){
            return false;
        }
        return ownerList.add(owner);
    }

    //---------------------
    // Read methods
    //---------------------
    public Owner getOwnerByIndex(int index){
        if (isValidIndex(ownerList, index)){
            return ownerList.get(index);
        }
        else{
            return null;
        }
    }

    public Owner getOwnerByName(String oName){
        int index = retrieveOwnerIndex(oName);
        if (index != -1){
            return ownerList.get(index);
        }
        return null;
    }


    public String listOwners(){
        String listOwners = "";
        for (Owner ow : ownerList){
            listOwners += ownerList.indexOf(ow) + ": " + ow + "\n";
        }
        if (listOwners.equals("")){
            return "No Owners Found";
        }
        else {
            return listOwners;
        }
    }
    public String listOwnersStartsWith(String prefix){
        String listOwners = "";
        for (Owner ow : ownerList){
            if (ow.getName().startsWith(prefix)){
                listOwners += ownerList.indexOf(ow) + ": " + ow + "\n";
            }}

        if (listOwners.equals("")){
            return "No Owners Found";
        }
        else {
            return listOwners;
        }
    }

    //---------------------
    // Update methods
    //---------------------
    public boolean updateOwner(int index, String name, String phone){
        if (getOwnerByIndex(index)!=null){
            Owner ow = getOwnerByIndex(index);
            ow.setName(name);
            ow.setPhoneNumber(phone);
            return true;
        }
        return false;
    }

    //---------------------
    // Delete methods
    //---------------------
    public boolean removeOwner(Owner ownerObject){
        if (ownerList.contains(ownerObject)) {
            return ownerList.remove(ownerObject);
        }
        return false;
    }

    public Owner removeOwnerByName(String ownerName){
        int index = retrieveOwnerIndex(ownerName);
        if (index != -1) {
            return ownerList.remove(index);
        }
        return null;
    }

    //---------------------
    // Validation Methods
    //---------------------
    public boolean isValidOwner(String ownerName){
        for (Owner ow : ownerList){
            if (ow.getName().equalsIgnoreCase(ownerName)){
                return true;
            }
        }
        return false;
    }

    public int retrieveOwnerIndex(String ownerName){
        for (Owner ow : ownerList){
            if (ow.getName().equalsIgnoreCase(ownerName)){
                return ownerList.indexOf(ow);
            }
        }
        return -1;
    }

    //---------------------
    // Getters/Setters
    //---------------------

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public File getFile() {
        return file;
    }


    //---------------------
    // Persistence Methods
    //---------------------

    @Override
    public String fileName() {
        return String.valueOf(file);
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(ownerList);
        os.close();
    }


    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ Owner.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        ownerList = (List<Owner>) in.readObject();
        in.close();
    }



}
