package org.example.Views;

import org.example.Controller.GameController;
import org.example.Models.Table;
import org.example.Services.Implementations.AudioServicesImpl;
import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.ViewServices;

import javax.swing.*;
import java.awt.*;

public class GameView implements View {

    private final JPanel _panel = new JPanel(new BorderLayout());
    private final JPanel _gamePanel = new JPanel();
    private final GameController _controller = new GameController(ViewServices.gamePath);
    private final Table _table = _controller.getTable().getTable();

    @Override
    public void changeView() {
        ViewServices.mainPanel.removeAll(); // Limpia el panel principal
        showView(); // Vuelve a construir los componentes de la vista
        ViewServices.mainPanel.add(_panel); // Agrega el panel reconstruido
        ViewServices.mainPanel.revalidate();
        ViewServices.mainPanel.repaint();
    }

    private void leftPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth()/10*2, ViewServices.frame.getHeight()));
        panel.setBackground(new Color(248, 250, 252));
    }

    private void bottomPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth()/10*8, ViewServices.frame.getHeight()/10*2));
        panel.setBackground(Color.WHITE);
        JButton previousButton = ViewServices.createButton(
                "<",
                10,
                10,
                60,
                20,
                e->{
                    _controller.PrevMovement();
                    changeView();
                }
        );
        JButton next = ViewServices.createButton(
                ">",
                10,
                10,
                50,
                20,
                e->{
                    _controller.NextMovement();
                    changeView();
                }
        );
        panel.add(previousButton, BorderLayout.WEST);
        panel.add(next, BorderLayout.EAST);
    }

    private void mainPanel() {
        _gamePanel.removeAll(); // Limpia cualquier componente previo del panel

        _gamePanel.setPreferredSize(new Dimension(
                ViewServices.frame.getWidth() / 10 * 8,
                ViewServices.frame.getHeight() / 10 * 8
        ));
        _gamePanel.setLayout(new GridLayout(10, 10)); // 10x10 para bordes de letras y números
        Color darkColor = new Color(125, 135, 150); // Gris oscuro
        Color lightColor = new Color(232, 235, 239); // Gris claro

        String[] columns = {"", "A", "B", "C", "D", "E", "F", "G", "H", ""};
        String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8"};

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JPanel square = new JPanel();
                square.setLayout(new BorderLayout());

                if (row == 0 || row == 9) {
                    JLabel label = new JLabel(columns[col], SwingConstants.CENTER);
                    square.add(label);
                    square.setBackground(Color.WHITE);
                } else if (col == 0 || col == 9) {
                    JLabel label = new JLabel(rows[row - 1], SwingConstants.CENTER);
                    square.add(label);
                    square.setBackground(Color.WHITE);
                } else {
                    if ((row + col) % 2 == 0) {
                        square.setBackground(lightColor);
                    } else {
                        square.setBackground(darkColor);
                    }
                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);

                    try {
                        if (_table.table()[row - 1][col - 1] != null) {
                            String color = _table.table()[row - 1][col - 1].color() ? "white" : "black";
                            String piece = _table.table()[row - 1][col - 1].piece().getName();
                            label.setIcon(FileServicesImpl.getInstance().getImageIcon(color.concat(piece)));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    square.add(label);
                }
                _gamePanel.add(square);
            }
        }

        _gamePanel.revalidate();
        _gamePanel.repaint();
    }


    private void rightPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth() / 10 * 8, ViewServices.frame.getHeight()));

        JPanel bottomPanel = new JPanel();

        this.bottomPanel(bottomPanel);

        this.mainPanel();
        panel.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(_gamePanel, BorderLayout.CENTER);
    }

    @Override
    public void showView() {
        _panel.removeAll();
        _panel.setBounds(0, 0, ViewServices.frame.getWidth(), ViewServices.frame.getHeight());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel(new BorderLayout());
        this.leftPanel(leftPanel);
        this.rightPanel(rightPanel);
        _panel.add(leftPanel, BorderLayout.WEST);
        _panel.add(rightPanel, BorderLayout.EAST);
        _panel.revalidate();
        _panel.repaint();
    }

}