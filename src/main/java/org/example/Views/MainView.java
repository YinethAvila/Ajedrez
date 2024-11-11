package org.example.Views;

import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.ViewServices;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

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
                e -> {
                    JFileChooser fileChooser = new JFileChooser("./src/main/resources/games");

                    int result = fileChooser.showOpenDialog(ViewServices.frame);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        Consumer<String> filePathConsumer = path -> ViewServices.gamePath=path;
                        filePathConsumer.accept(selectedFile.getAbsolutePath());
                    }
                }
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