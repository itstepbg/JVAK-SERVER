package com.company;

import com.company.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static UserManager instance = new UserManager();

    private HashMap<String, User> users = new HashMap<>();

    public static UserManager getInstance() {
        return instance;
    }

    private UserManager() {}

    public void createUser(String username, String password, String email, boolean systemMessage) {
        //Create a new user instance.
        User user = new User();

        //Set the username, password and email properties of the new user instance.
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        users.put(username, user);

        if (systemMessage == true) {
            System.out.println();
            System.out.println(username + " was created successfully.");
        }
    }

    public void addUserToDb(String username, String password, String email) {
        try {

            //First add the user to the database.
            DatabaseConnector.addUserToDatabase(username, password, email);

            //Then create one user in UserManager.
            this.createUser(username, password, email, true);

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void loadUsersFromDB() {
        try {
            DatabaseConnector.getUsersFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int position) {

        int j = 0;
        String userToDelete;

        for (Map.Entry<String, User> userEntry : users.entrySet()) {

            User user = userEntry.getValue();

            if (j == position) {
                userToDelete = user.getUsername();

                try {

                    DatabaseConnector.deleteUserFromDatabase(userToDelete);

                    users.remove(userToDelete);

                    System.out.println();
                    System.out.println("User " + (position + 1) + " deleted successfully!");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            }

            j++;
        }


    }

    public void listAllUsers() {
        System.out.println();
        System.out.println("Listing Registered Users:");

        int i = 0;
        for (Map.Entry<String, User> userEntry : users.entrySet()) {
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

    public void findUser(String userToFind) {

        String userToCompare;

        int u = 0;

        for (Map.Entry<String, User> userEntry : users.entrySet()) {

            User user = userEntry.getValue();

            userToCompare = user.getUsername();

            if (userToFind.equals(userToCompare)) {
                System.out.println("");
                System.out.println((u + 1) + ". " + user.getUsername());
                System.out.println(" - E-mail: " + user.getEmail());
            }
            u++;
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public int getTotalUsers() {
        return users.size();
    }

    private void waitForEnter() {
        System.out.println("Press Enter to return to menu.");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    public boolean areThereRegisteredUsers() {
        return users.size() > 0;
    }
}