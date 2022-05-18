package views.components;

import database.UserDatabase;
import helpers.ConsoleLogger;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    MainFrame parent;

    JList<String> userList;
    JLabel nameLBL = new Label(25, 140, 200, "Name");
    JTextField nameTF = new TextField(25,165,200, "First name with capital letter");
    JLabel ageLBL = new Label(250, 140, 50, "Age");
    JFormattedTextField ageTF = new NumberTextField(250, 165, 50);
    JLabel emailLBL = new Label(25, 215, 275, "Email");
    JTextField emailTF = new TextField(25, 240, 275);
    JLabel passwordLBL = new Label(25,290, 275, "Password");
    JPasswordField passwordPF = new PasswordField(25, 315, 275, "5 characters minimum");
    JButton addBTN = new Button(25, 390,275,"Add User", "./img/set.png");
    JButton removeBTN = new Button(350, 25, 200, "Remove User", "./img/delete.png");
    JButton logoutBTN = new Button(25, 25, 275, "Logout", "./img/logout.png");


    public MainPanel(MainFrame parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(Color.darkGray);

        userList = new JList<>(UserDatabase.getUsers());
        userList.setBounds(350,100,200, 340);
        add(userList);

        add(nameLBL);
        add(nameTF);
        add(ageLBL);
        add(ageTF);
        add(emailLBL);
        add(emailTF);
        add(passwordLBL);
        add(passwordPF);
        add(addBTN);
        add(removeBTN);
        add(logoutBTN);

        logoutBTN.addActionListener(e -> parent.switchToLoginPanel());

        removeBTN.addActionListener(e -> {
            UserDatabase.removeUser(userList.getSelectedValue());
            userList.setModel(UserDatabase.getUsers());
        });

        addBTN.addActionListener(e -> {
            if(checkFields()) {
                UserDatabase.addUser(nameTF.getText(), Integer.parseInt(ageTF.getValue().toString()), emailTF.getText(), String.valueOf(passwordPF.getPassword()));
            }
            userList.setModel(UserDatabase.getUsers());
        });

        userList.addListSelectionListener(e -> fillFields());

        setVisible(true);
        ConsoleLogger.printLog("MainPanel created");
    }


    /**
     * checks all input fields for correct input format
     * @return boolean
     */
    private boolean checkFields() {
        if(nameTF.getText().isEmpty() || !nameTF.getText().matches("^[A-Z][a-z]*")) {
            JOptionPane.showMessageDialog(null, "Invalid Name");
            return false;
        }
        if(Integer.parseInt(ageTF.getValue().toString()) <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid Age");
            return false;
        }
        if(emailTF.getText().isEmpty() || !emailTF.getText().matches("[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})")) {
            JOptionPane.showMessageDialog(null, "Invalid Email");
            return false;
        }
        if(String.valueOf(passwordPF.getPassword()).length() < 5) {
            JOptionPane.showMessageDialog(null, "Invalid Password");
            return false;
        }
        return true;
    }

    /**
     * fills name and age fields with corresponding records
     */
    private void fillFields() {
        nameTF.setText(UserDatabase.getUserName(userList.getSelectedValue()));
        ageTF.setValue(UserDatabase.getUserAge(userList.getSelectedValue()));
        emailTF.setText(UserDatabase.getUserEmail(userList.getSelectedValue()));
        passwordPF.setText("");
    }

}
