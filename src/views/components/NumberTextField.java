package views.components;

import javax.swing.*;
import java.text.NumberFormat;

public class NumberTextField extends JFormattedTextField {

    /**
     * creates a JFormattedTextField with NumberFormat
     * @param x position
     * @param y position
     * @param width dimension
     */
    public NumberTextField(int x, int y, int width) {
        super(NumberFormat.getNumberInstance());
        setBounds(x, y, width, 25);
    }

}
