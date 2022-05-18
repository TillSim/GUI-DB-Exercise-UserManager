package views;

import database.UserDatabase;
import helpers.ConsoleLogger;
import views.components.LoginPanel;
import views.components.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    LoginPanel loginPanel;
    MainPanel mainPanel;

    public MainFrame() {
        add(loginPanel = new LoginPanel(this));

        setTitle("Login");
        ImageIcon logo = new ImageIcon("./img/logo.png");
        setIconImage(logo.getImage());

        setSize(600,500);
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        ConsoleLogger.printLog("MainFrame created");
    }

    @Override
    public void setTitle(String text) {
        super.setTitle("User Manager  |  " + text);
    }

    /**
     * removes all Panels and adds new LoginPanel
     * updates Frame title
     */
    public void switchToLoginPanel() {
        UserDatabase.addDefaultUser();
        getContentPane().removeAll();
        add(loginPanel = new LoginPanel(this));
        revalidate();
        setTitle("Login");
    }

    /**
     * removes all Panels and adds new MainPanel
     * updates Frame title
     */
    public void switchToMainPanel() {
        getContentPane().removeAll();
        add(mainPanel = new MainPanel(this));
        revalidate();
        setTitle("Administration");
    }

}
