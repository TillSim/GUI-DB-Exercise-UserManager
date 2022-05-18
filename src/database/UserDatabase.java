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
     * adds user to database if it does not exist already
     * @param name String
     * @param age int
     * @param email String
     * @param password String
     */
    public static void addUser(String name, int age, String email, String password) {
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
    }

    /**
     * adds default user, if table is empty
     */
    public static void addDefaultUser() {
        try {
            if(!connection.queryDB("SELECT * FROM users").next()) {
                addUser("Admin", 99, "admin@usermanager.com", "admin");
                JOptionPane.showMessageDialog(null, "Added Default User (email:'admin@usermanager.com' | pw:'admin')");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * removes user from database
     * @param id int
     */
    public static void removeUser(int id) {
        connection.modifyDB("DELETE FROM users WHERE id="+ (id+1));
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
     * @param id int
     * @return String
     */
    public static String getUserName(int id) {
        ResultSet query = connection.queryDB("SELECT name FROM users WHERE id=" + (id+1));
        try {
            return query.getString("name");
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     * returns user age from database
     * @param id int
     * @return int
     */
    public static int getUserAge(int id) {
        ResultSet query = connection.queryDB("SELECT age FROM users WHERE id=" + (id+1));
        try {
            return query.getInt("age");
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * returns user email from database
     * @param id int
     * @return String
     */
    public static String getUserEmail(int id) {
        ResultSet query = connection.queryDB("SELECT email FROM users WHERE id=" + (id+1));
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

}
