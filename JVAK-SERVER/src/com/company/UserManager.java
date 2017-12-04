package com.company;

import com.company.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {

    //public static final String USERS_FILE_NAME = "users.csv";

    private static UserManager instance = new UserManager();

    private HashMap<String, User> users = new HashMap<>();


    private UserManager() {
        //loadUsers();
        loadUsersFromDB();
    }

    public static UserManager getInstance() {
        return instance;
    }

    public void createUser(String username, String password, String email) {
        //Create a new user instance.
        User user = new User();
        //Set the username, password and email properties of the new user instance.
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        users.put(username, user);

        System.out.println();
        System.out.println(username + " was created successfully.");

        //saveUsers();
        insertUserInDB();
    }

    private void loadUsersFromDB() {
        //TODO
    }

    private void insertUserInDB() {
        //TODO
    }

    public void deleteUser(int position) {
        //users.remove(position);
        //TODO

        System.out.println();
        System.out.println("User " + position + " deleted successfully!");
    }

    public void listAllUsers() {
        System.out.println();
        System.out.println("Listing Registered Users:");

        int i = 0;
        for (Map.Entry<String, User> userEntry: users.entrySet()) {
            User user = userEntry.getValue();
            System.out.println("");
            System.out.println((i + 1) + ". " + user.getUsername());
            System.out.println(" - E-mail: " + user.getEmail());
            i++;
        }
        System.out.println();
        System.out.println("Total Users: " + i);
        waitForEnter();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    private void waitForEnter() {
        System.out.println("Press Enter to return to menu.");
        try {
            System.in.read();
        } catch(Exception e) {}
    }

    public boolean areThereRegisteredUsers() {
        return users.size() > 0;
    }

//    private void saveUsers() {
//        Path filePath = Paths.get(USERS_FILE_NAME);
//        ArrayList<String> usersData = new ArrayList<>();
//
//        for (Map.Entry<String, User> userEntry: users.entrySet()) {
//            User user = userEntry.getValue();
//            usersData.add(user.getUsername() + "," + user.getPassword() + "," + user.getEmail());
//        }
//
//        try {
//            Files.deleteIfExists(filePath);
//            Files.write(filePath, usersData, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            //e.printStackTrace();
//        }
//    }
//
//    private void loadUsers() {
//        Path filePath = Paths.get(USERS_FILE_NAME);
//        List<String> usersData = null;
//
//        try {
//            usersData = Files.readAllLines(filePath, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            //e.printStackTrace();
//        }
//
//        if (usersData != null) {
//            for (String userData: usersData) {
//                String[] userFields = userData.split(",");
//
//                User user = new User();
//                user.setUsername(userFields[0]);
//                user.setPassword(userFields[1]);
//                user.setEmail(userFields[2]);
//
//                users.put(user.getUsername(), user);
//            }
//        }
//    }
}