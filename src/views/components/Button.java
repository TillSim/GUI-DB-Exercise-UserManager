package views.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(int x, int y, int width, String text, String imgFile) {
        setBounds(x,y,width,50);
        setIcon(scaleIcon(imgFile));
        setText(text);
        setFont(new Font("Arial", Font.BOLD, 20));
        setHorizontalTextPosition(JButton.RIGHT);
        setVerticalTextPosition(JButton.CENTER);
        setBackground(Color.lightGray);
        setForeground(Color.black);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(false);
    }


    /**
     * scales down Image
     * @param file path to image
     * @return ImageIcon
     */
    private static ImageIcon scaleIcon(String file) {
        ImageIcon icon = new ImageIcon(file);
        Image img = icon.getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

}
