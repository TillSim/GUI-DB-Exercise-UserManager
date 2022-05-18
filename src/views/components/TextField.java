package views.components;

import javax.swing.*;

public class TextField extends JTextField {

    public TextField(int x, int y, int width) {setBounds(x,y,width,25);}

    public TextField(int x, int y, int width, String toolTipText) {
        setBounds(x,y,width,25);
        setToolTipText(toolTipText);
    }

}
