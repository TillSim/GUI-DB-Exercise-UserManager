package views.components;

import database.UserDatabase;
import helpers.ConsoleLogger;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    MainFrame parent;

    JLabel imageLBL = new Label(172, 25, 256, 256, "./img/logo.png");
    JLabel emailLBL = new Label(50, 300, 100, "Email");
    JLabel passwordLBL = new Label(50, 350, 100, "Password");
    JTextField emailTF = new TextField(150, 300, 300);
    PasswordField passwordPF = new PasswordField(150,350,300);
    Button loginBTN = new Button(200, 400, 200, "Login", "./img/login.png");


    public LoginPanel(MainFrame parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(Color.darkGray);

        add(imageLBL);
        add(emailLBL);
        add(emailTF);
        add(passwordLBL);
        add(passwordPF);
        add(loginBTN);

        loginBTN.addActionListener(e -> {
            if(UserDatabase.isLoginValid(emailTF.getText(), String.valueOf(passwordPF.getPassword()))) {
                parent.switchToMainPanel();
            } else {passwordPF.setText("");}
        });

        setVisible(true);
        ConsoleLogger.printLog("LoginPanel created");
    }

}
