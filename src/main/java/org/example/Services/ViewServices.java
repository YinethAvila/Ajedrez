package org.example.Services;

import org.example.Views.View;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionListener;

public class ViewServices {
    public static int xSize;
    public static int ySize;
    public static String gamePath;
    public static JFrame frame;
    public static JPanel mainPanel;
    public static Clip clip;


    public static void changeView(View view) {
        view.changeView();
    }



    public static JButton createButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        return button;
    }

}
