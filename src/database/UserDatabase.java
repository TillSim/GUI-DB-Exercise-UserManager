package database;

import helpers.ConsoleLogger;
import helpers.DatabaseHandler;
import helpers.EncryptionHandler;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDatabase {

    private static DatabaseHandler connection;
    private static String signedUser = "";
    private static final String ADMINEMAIL = "admin@usermanager.com";


    /**
     * creates and connects database and table with default user
     */
    public static void connect() {
        if(!Files.exists(Path.of("users.db"))) {
            connection = new DatabaseHandler("users.db");
            connection.modifyDB("CREATE TABLE IF NOT EXISTS users "+
                                            "(id INTEGER PRIMARY KEY, " +
                                            "name TEXT NOT NULL, " +
                                            "age INTEGER NOT NULL, " +
                                            "email TEXT NOT NULL UNIQUE, " +
                                            "password TEXT NOT NULL)");
        } else {connection = new DatabaseHandler("users.db");}

        addDefaultUser();
    }

    /**
     * adds user to database, if it does not exist already
     * @param name String
     * @param age int
     * @param email String
     * @param password String
     */
    public static void addUser(String name, int age, String email, String password) {
        String signedUser = UserDatabase.getSignedUser();

        if(signedUser.equals("") || signedUser.equals(ADMINEMAIL)){
            try {
                if(connection.queryDB("SELECT * FROM users WHERE email= '" + email + "'").next()) {
                    JOptionPane.showMessageDialog(null, "User Already Exists");
                } else {
                    connection.modifyDB("INSERT INTO users (name, age, email, password) VALUES (" +
                                                     "'" + name + "', " + age + ", '" + email + "', '" + EncryptionHandler.bcryptHash(password) + "')");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {JOptionPane.showMessageDialog(null, "Log In As Admin To Add New Users");}
    }

    /**
     * adds default user, if table is empty
     */
    public static void addDefaultUser() {
        try {
            if(!connection.queryDB("SELECT * FROM users").next()) {
                addUser("Admin", 99, ADMINEMAIL, "admin");
                JOptionPane.showMessageDialog(null, "Added Default User (email:'" + ADMINEMAIL + "' | pw:'admin')");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates user with password
     * @param name String
     * @param age int
     * @param email String
     * @param password String
     */
    public static void updateUser(String name, int age, String email, String password) {
        String signedUser = UserDatabase.getSignedUser();

        if(signedUser.equals(ADMINEMAIL) || signedUser.equals(email)){
            try {
                if(connection.queryDB("SELECT * FROM users WHERE email= '" + email + "'").next()) {
                    connection.modifyDB("UPDATE users SET name = '" + name + "'," + " age = '" + age + "'," + " password = '" + EncryptionHandler.bcryptHash(password) + "' WHERE email = '" + email + "'");
                } else {JOptionPane.showMessageDialog(null, "User Not In Database");}
            } catch (SQLException e) {JOptionPane.showMessageDialog(null, "User Not In Database (EXCEPTION)");}
        } else {JOptionPane.showMessageDialog(null, "Log In As Admin To Update Other Users");}
    }

    /**
     * updates user without password
     * @param name String
     * @param age int
     * @param email String
     */
    public static void updateUser(String name, int age, String email) {
        String signedUser = UserDatabase.getSignedUser();

        if(signedUser.equals(ADMINEMAIL) || signedUser.equals(email)){
            try {
                if(connection.queryDB("SELECT * FROM users WHERE email= '" + email + "'").next()) {
                    connection.modifyDB("UPDATE users SET name = '" + name + "'," + " age = '" + age + "' WHERE email = '" + email + "'");
                } else {JOptionPane.showMessageDialog(null, "User Not In Database");}
            } catch (SQLException e) {JOptionPane.showMessageDialog(null, "User Not In Database (EXCEPTION)");}
        } else {JOptionPane.showMessageDialog(null, "Log In As Admin To Update Other Users");}
    }

    /**
     * removes user from database
     * @param email String
     */
    public static void removeUser(String email) {
        String signedUser = UserDatabase.getSignedUser();

        if(signedUser.equals(ADMINEMAIL)) {
            if(!email.equals(ADMINEMAIL)) {
                connection.modifyDB("DELETE FROM users WHERE email='" + email + "'");
            } else {JOptionPane.showMessageDialog(null, "You Can Not Remove The Admin User");}
        } else {JOptionPane.showMessageDialog(null, "Log In As Admin To Remove Users");}
    }

    /**
     * loads all records from users table
     * @return DefaultListModel String
     */
    public static DefaultListModel<String> getUsers() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ResultSet query;
        try {
            query = connection.queryDB("SELECT * FROM users");
            while(query.next()) {
                listModel.addElement(query.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {return listModel;}
    }

    /**
     * returns user name from database
     * @param email String
     * @return String
     */
    public static String getUserName(String email) {
        ResultSet query = connection.queryDB("SELECT name FROM users WHERE email='" + email + "'");
        try {
            return query.getString("name");
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     * returns user age from database
     * @param email String
     * @return int
     */
    public static int getUserAge(String email) {
        ResultSet query = connection.queryDB("SELECT age FROM users WHERE email='" + email + "'");
        try {
            return query.getInt("age");
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * returns user email from database
     * @param email String
     * @return String
     */
    public static String getUserEmail(String email) {
        ResultSet query = connection.queryDB("SELECT email FROM users WHERE email='" + email + "'");
        try {
            return query.getString("email");
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     * compares credentials with email and password from database
     * @param email String
     * @param password String
     * @return boolean
     */
    public static boolean isLoginValid(String email, String password) {
        ResultSet query = connection.queryDB("SELECT password FROM users WHERE email = '" + email + "'");

        try {
            if(query != null && EncryptionHandler.comparePassword(password, query.getString("password"))) {
                ConsoleLogger.printLog("Login Successful");
                return true;
            }
        } catch (SQLException e) {e.printStackTrace();}

        JOptionPane.showMessageDialog(null, "Login Failed");
        ConsoleLogger.printLog("Login Failed");
        return false;
    }


    public static String getSignedUser() {
        return signedUser;
    }

    public static void setSignedUser(String signedUser) {
        UserDatabase.signedUser = signedUser;
    }
}
