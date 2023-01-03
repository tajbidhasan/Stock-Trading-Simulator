package com.example.homework10;

import java.io.*;
import java.util.ArrayList;

public class DataCenter implements Serializable{
    private static DataCenter instance = null;
    private static String fileName = "data.dat";

    private ArrayList<User> userList;

    private DataCenter() {
        userList = new ArrayList<>();

        userList.add(new User("tajbid","keepGoing", "samee6510"));

    }

    public static DataCenter getInstance() {
        if(instance == null) {
            if(readFile()==null) {
                return instance = new DataCenter();
            }
            else {
                instance = readFile();
            }
        }
        return instance;
    }
    public User login(String username, String password){
        for(int i=0; i< userList.size();i++){
            if(userList.get(i).getUsername().equals(username)&& userList.get(i).getPassword().equals(password)){

                return userList.get(i);

            }

        }

        return null;
    }
    public String getNameData(User user){
        return user.getName();
    }
    public boolean findUser(String username, String password) {
        return findUser(new User(username, password));
    }

    public boolean addUser(User user){
        if(!isValid(user)){
            userList.add(user);
            return true;
        }
        return false;
    }
    public boolean findUser(User user) {
        return userList.contains(user);
    }
    public boolean isValid(User user){
        ArrayList<String> userNameList = new ArrayList<>();

        for(int i = 0; i< userList.size();i++){
            userNameList.add(userList.get(i).getUsername());

        }
        if(userNameList.contains(user.getUsername())){
            return true;
        }

        return false;
    }
         public static void savefile() {

        try(ObjectOutputStream oos = new ObjectOutputStream(new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(fileName))))){
            oos.writeObject(instance);

        }
        catch(IOException ioe){

        }
        catch(Exception e) {

        }
    }
    public static DataCenter readFile() {

        try(ObjectInputStream ois = new ObjectInputStream( new DataInputStream(new BufferedInputStream(
                new FileInputStream(fileName))))){
            return((DataCenter) ois.readObject());

        }
        catch(IOException ioe){

        }
        catch(Exception e) {

        }
        return null;
    }


}
