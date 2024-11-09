package org.example.Views;

import org.example.Services.FileServices;
import org.example.Services.ViewServices;
import javax.swing.*;

public class MainView implements View {

    private final JPanel _panel = new JPanel();

    @Override
    public void changeView() {
        ViewServices.mainPanel.removeAll();
        showView();
        ViewServices.mainPanel.add(_panel);
        ViewServices.mainPanel.revalidate();
        ViewServices.mainPanel.repaint();
    }

    @Override
    public void showView() {
        _panel.setBounds(0, 0, ViewServices.frame.getWidth(), ViewServices.frame.getHeight());
        _panel.setLayout(null);
        JButton readFile = ViewServices.createButton(
                "Read File",
                ViewServices.frame.getWidth()/3,
                (ViewServices.frame.getHeight()/2)-20,
                100,
                40,
                FileServices.getFilePath(ViewServices.frame, path->ViewServices.gamePath=path)
        );
        JButton startGame = ViewServices.createButton(
                "Start Game",
                (ViewServices.frame.getWidth()/3)+150,
                (ViewServices.frame.getHeight()/2)-20,
                100,
                40,
                e -> {
                    if (ViewServices.gamePath == null){
                        JOptionPane.showMessageDialog(ViewServices.frame, "Please select a game", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        new GameView().changeView();
                    }
                }
        );
        _panel.add(readFile);
        _panel.add(startGame);
    }
}