package views.components;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    public Label(int x, int y, int width, String text) {
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(x,y,width,25);
        setForeground(Color.white);
    }

    public Label(int x, int y, int width, int height, String imgFile) {
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(x,y,width,height);
        ImageIcon icon = new ImageIcon(imgFile);
        Image img = icon.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(img));
    }

}
