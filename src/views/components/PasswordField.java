package views.components;

import javax.swing.*;

public class PasswordField extends JPasswordField {

    public PasswordField(int x, int y, int width) {
        setBounds(x,y,width,25);
    }

    public PasswordField(int x, int y, int width, String toolTipText) {
        setBounds(x,y,width,25);
        setToolTipText(toolTipText);
    }



}
