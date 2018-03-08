package com.company;

import java.sql.*;

public class DatabaseConnector {
    /*
    Steps:

    1. Get a connection to the database.

    2. Get a statement from the connection.

    3. Execute a SQL Query.

    4. Process the results.

    5. Close Results.

    6. Close Statement.

    7. Close Connection.

    */

    //Prerequisites
    private static final String JDB_DATABASE = "jvak_users";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + JDB_DATABASE;

    private static final String DB_TABLE = "users";

    private static final String DB_USER = "jvak_users";
    private static final String DB_PASS = "Wl<Ewv;0Ss/5";

    private static Connection dbConnection = null;
    private static Statement dbStatement = null;
    private static PreparedStatement dbPreparedStatement = null;
    private static ResultSet dbResultSet = null;

    private static UserManager userManager = UserManager.getInstance();

    private static Connection getDatabaseConnection() {

        try {

            System.out.println("Connected to SQL server.");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("ErrorCode: " + ex.getErrorCode());

        }

        return null;
    }

    public static void addUserToDatabase(String username, String password, String email) throws SQLException {
        try {

            String userInsertQuery = "INSERT INTO " + DB_TABLE + " (name, password, email)" +
                    " VALUES (?, ?, ?)";

            dbConnection = getDatabaseConnection();

            if (dbConnection != null) {

                dbPreparedStatement = dbConnection.prepareStatement(userInsertQuery);

                dbPreparedStatement.setString(1, username);
                dbPreparedStatement.setString(2, password);
                dbPreparedStatement.setString(3, email);

                dbPreparedStatement.executeUpdate();

                System.out.println("Insert Completed Successfully...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbPreparedStatement != null) {
                dbPreparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static void deleteUserFromDatabase(String username) throws SQLException {
        try {

            String userDeleteQuery = "DELETE FROM " + DB_TABLE + " WHERE name='" + username + "'";

            dbConnection = getDatabaseConnection();

            if (dbConnection != null) {
                dbStatement = dbConnection.createStatement();
            }

            int rAff = dbStatement.executeUpdate(userDeleteQuery);

            System.out.println("Rows Affected:" + rAff);
            System.out.println("Delete Completed");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbStatement != null) {
                dbStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static void getUsersFromDatabase() throws SQLException {

        String dbUserName, dbUserPass, dbUserEmail;

        int userCounter = 0;

        try {

            String userGetAllQuery = "SELECT * FROM " + DB_TABLE;

            dbConnection = getDatabaseConnection();

            if (dbConnection != null) {

                dbStatement = dbConnection.createStatement();

                dbResultSet = dbStatement.executeQuery(userGetAllQuery);

                System.out.println();
                System.out.println("Syncing users from database...");
                System.out.println();

                while (dbResultSet.next()) {
                    dbUserName = dbResultSet.getString("name");
                    dbUserPass = dbResultSet.getString("password");
                    dbUserEmail = dbResultSet.getString("email");

                    userManager.createUser(dbUserName, dbUserPass, dbUserEmail, false);

                    userCounter++;

                    System.out.print(".." + userCounter + " ");

                }

                System.out.println();
                System.out.println("Users syncrhonized: " + userCounter);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbResultSet != null) {
                dbResultSet.close();
            }
            if (dbStatement != null) {
                dbStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}