package org.example;

import org.example.Services.ViewServices;
import org.example.Views.MainView;
import org.example.Views.View;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        ViewServices.xSize = 800;
        ViewServices.ySize = 600;
        ViewServices.frame = new JFrame("Ajedrez");
        ViewServices.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ViewServices.frame.setResizable(false);
        ViewServices.frame.setLocationRelativeTo(null);
        ViewServices.frame.setBounds(0, 0, ViewServices.xSize, ViewServices.ySize);
        ViewServices.mainPanel = new JPanel();
        ViewServices.mainPanel.setLayout(null);
        ViewServices.frame.add(ViewServices.mainPanel);
        View mainView = new MainView();
        mainView.changeView();
        ViewServices.frame.setVisible(true);
    }

}