package com.company;

import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    //Init running and assign true so that we can loop with (while)
    private static boolean running = true;

    private static UserManager userManager = UserManager.getInstance();

    private static ServerThread networkingThread;

    private static Connection dbConnection;

    public static void main(String[] args) {
        for (String argument : args) {
            System.out.println(argument);
        }

        networkingThread = new ServerThread("networkingThread");
        networkingThread.start();

        //TODO Move to a separate MySQLManager class.
        try {
            dbConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/chat_server?user=root&password=test");
            System.out.println("Connected to SQL server.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("ErrorCode: " + ex.getErrorCode());
        }

        //Looping while running == true
        while(running) {
            //Display the main menu in the console in the form of text
            showMenu();

            //Wait for the user input, and read it once the user has entered something.
            //Pass the UserManager instance to the method.
            readUserInput();
        }
    }

    private static void showMenu() {
        //Print the menu information on the screen.
        System.out.println();
        System.out.println("1. Create User");
        System.out.println("2. Delete User");
        System.out.println("3. List All Users");
        System.out.println("4. Find Users");
        System.out.println("5. Exit");
    }

    private static void readUserInput() {
        //Print out a line and text that get the user aware of the menu options.
        System.out.println();
        System.out.print("Select Menu Item: ");

        //Create a new instance scanner of the object Scanner that "scans" the System.in
        Scanner scanner = new Scanner(System.in);

        //Non-static method next is assigned to a string named command.
        String command = scanner.next();

        int commandCode = 0;

        // Using try catch to try to "convert" a string into an integer.
        // If the string is a number, commandCode will be equal to that number in integer.
        // If the string is a character, we will displayError();
        try {
            commandCode = Integer.parseInt(command);
        } catch(Exception e) {
            displayError();
        }

        //Check if the user has entered a valid option
        if (commandCode > 0) {
            menuSelection(commandCode);
        } else {
            displayError();
        }
    }

    private static void displayError() {
        System.out.println();
        System.out.println("Invalid selection!");
    }

    private static void menuSelection(int commandCheck) {
        switch(commandCheck) {
            case 1:
                menuUserRegistration();
                break;
            case 2:
                menuDeleteUser();
                break;
            case 3:
                menuListUsers();
                break;
            case 4:
                //menuSearchUser();
                break;
            case 5:
                System.out.println();
                System.out.println("Quitting...");
                networkingThread.stopServer();
                running = false;
                break;
            default:
                displayError();
                break;
        }
    }

    private static void menuUserRegistration() {
        System.out.println();
        System.out.println("---= User Registration =---");
        System.out.println();
        System.out.print("Please enter a user name: ");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        System.out.println();
        System.out.print("Please enter a password: ");
        String password = scanner.next();

        System.out.println();
        System.out.print("Please enter an E-mail Address: ");
        String email = scanner.next();

        userManager.createUser(name, password, email);
    }

    private static void menuDeleteUser() {
        if (userManager.areThereRegisteredUsers()) {
            System.out.println();
            System.out.println("---= Delete a User =---");
            System.out.println();
            System.out.print("Please enter the user name you want to delete: ");
            Scanner scanner = new Scanner(System.in);
            int userIndex = scanner.nextInt();

            boolean waitConfirmation = true;
            while (waitConfirmation) {
                System.out.println();
                System.out.print("Are you sure you want to delete the user with index: " + userIndex + " - (yY/nN) ?");
                String answer = scanner.next();
                if ((answer.equals("y")) || (answer.equals("Y"))) {
                    userManager.deleteUser(userIndex - 1);
                    break;
                } else {
                    System.out.println();
                    System.out.println("Returning to Menu..");
                    System.out.println();
                    break;
                }
            }
        } else {
            displayNoUsersError();
        }
    }

    private static void menuSearchUsers() {
        if (userManager.areThereRegisteredUsers()) {
            System.out.println();
            System.out.println("---= Search Usernames =---");
            System.out.println();
            System.out.print("Please enter the user name you want to find: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.next();
            //TODO Implement a user serach functionality in a more appropriate way.
            //userManager.findUsers(name);
        } else {
            displayNoUsersError();
        }
    }

    private static void menuListUsers() {
        if (userManager.areThereRegisteredUsers()) {
            userManager.listAllUsers();
        } else {
            displayNoUsersError();
        }
    }

    private static void displayNoUsersError() {
        System.out.println();
        System.out.println("Please register a user to use this option!");
    }
}