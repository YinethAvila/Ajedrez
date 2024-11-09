package org.example.Views;

import org.example.Services.FileServices;
import org.example.Services.ViewServices;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameView implements View {

    private final JPanel _panel = new JPanel(new BorderLayout());
    private final JPanel _gamePanel = new JPanel();

    @Override
    public void changeView() {
        ViewServices.mainPanel.removeAll();
        ViewServices.playBackgroundMusic();
        showView();
        ViewServices.mainPanel.add(_panel);
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
                e->{}
        );
        JButton next = ViewServices.createButton(
                ">",
                10,
                10,
                50,
                20,
                e->{}
        );
        panel.add(previousButton, BorderLayout.WEST);
        panel.add(next, BorderLayout.EAST);
    }

    private void mainPanel() {
        _gamePanel.setPreferredSize(new Dimension(
                ViewServices.frame.getWidth()/10*8,
                ViewServices.frame.getHeight()/10*8)
        );
        _gamePanel.setLayout(new GridLayout(10, 10)); // 10x10 para bordes de letras y números

        // Colores de las casillas del tablero
        Color darkColor = new Color(125, 135, 150); // Gris oscuro
        Color lightColor = new Color(232, 235, 239); // Gris claro

        // Letras para las columnas
        String[] columns = {"", "A", "B", "C", "D", "E", "F", "G", "H", ""};

        // Números para las filas
        String[] rows = {"8", "7", "6", "5", "4", "3", "2", "1"};

        // Crear la cuadrícula con bordes de letras y números
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JPanel square = new JPanel();
                square.setLayout(new BorderLayout());

                if (row == 0 || row == 9) {
                    // Colocar letras en la primera y última fila
                    JLabel label = new JLabel(columns[col], SwingConstants.CENTER);
                    square.add(label);
                    square.setBackground(Color.WHITE);
                } else if (col == 0 || col == 9) {
                    // Colocar números en la primera y última columna
                    if (col == 0) {
                        JLabel label = new JLabel(rows[row - 1], SwingConstants.CENTER);
                        square.add(label);
                    } else if (col == 9) {
                        JLabel label = new JLabel(rows[row - 1], SwingConstants.CENTER);
                        square.add(label);
                    }
                    square.setBackground(Color.WHITE);
                } else {
                    // Colocar las casillas del tablero de ajedrez
                    if ((row + col) % 2 == 0) {
                        square.setBackground(lightColor); // Casilla clara
                    } else {
                        square.setBackground(darkColor); // Casilla oscura
                    }
                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);
                    if((row == 1 && col == 1) || (row == 1 && col == 8)) {
                        label.setIcon(FileServices.getIcon("whiteTower.png"));
                    }else if((row == 1 && col == 2) || (row == 1 && col == 7)) {
                        label.setIcon(FileServices.getIcon("whiteKnight.png"));
                    }else if((row == 1 && col == 3) || (row == 1 && col == 6)) {
                        label.setIcon(FileServices.getIcon("whiteBishop.png"));
                    } else if (row == 1 && col == 4) {
                        label.setIcon(FileServices.getIcon("whiteQueen.png"));
                    } else if(row == 1 && col == 5) {
                        label.setIcon(FileServices.getIcon("whiteKing.png"));
                    } else if(row == 2){
                        label.setIcon(FileServices.getIcon("whitePawn.png"));
                    } else if (row == 7) {
                        label.setIcon(FileServices.getIcon("blackPawn.png"));
                    } else if ((row == 8 && col == 1) || (row == 8 && col == 8)) {
                        label.setIcon(FileServices.getIcon("blackTower.png"));
                    } else if ((row == 8 && col == 2) || (row == 8 && col == 7)) {
                        label.setIcon(FileServices.getIcon("blackKnight.png"));
                    } else if ((row == 8 && col == 3) || (row == 8 && col == 6)) {
                        label.setIcon(FileServices.getIcon("blackBishop.png"));
                    } else if (row == 8 && col == 4) {
                        label.setIcon(FileServices.getIcon("blackQueen.png"));
                    } else if(row == 8 && col == 5) {
                        label.setIcon(FileServices.getIcon("blackKing.png"));
                    }
                    square.add(label);
                }
                _gamePanel.add(square);
            }
        }
    }

    // Metodo para configurar el panel derecho, que contiene una sección inferior y el panel del juego
    private void rightPanel(JPanel panel) {
        // Ajusta el tamaño preferido del panel derecho a 80% del ancho de la ventana y el 100% de su altura
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth() / 10 * 8, ViewServices.frame.getHeight()));

        // Crea un panel inferior para colocar en la parte inferior del panel derecho
        JPanel bottomPanel = new JPanel();

        // Configura el panel inferior con su propio método (asumiendo que bottomPanel() personaliza este panel)
        this.bottomPanel(bottomPanel);

        // Configura el panel principal del juego (asumiendo que mainPanel() inicializa o configura _gamePanel)
        this.mainPanel();

        // Agrega el panel inferior en la posición SUR (abajo) del panel derecho
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Agrega el panel principal del juego en la posición CENTRO del panel derecho
        panel.add(_gamePanel, BorderLayout.CENTER);
    }

    // Metodo que muestra la vista principal configurando y agregando los paneles izquierdo y derecho al panel principal
    @Override
    public void showView() {
        // Configura los límites del panel principal para ocupar todo el área de la ventana
        _panel.setBounds(0, 0, ViewServices.frame.getWidth(), ViewServices.frame.getHeight());

        // Crea un nuevo panel para el lado izquierdo
        JPanel leftPanel = new JPanel();

        // Crea un nuevo panel derecho y le asigna un BorderLayout para organizar sus elementos internos
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Configura el panel izquierdo utilizando el método leftPanel(), que se encarga de personalizar este panel
        this.leftPanel(leftPanel);

        // Configura el panel derecho utilizando el método rightPanel(), que lo divide en diferentes secciones
        this.rightPanel(rightPanel);

        // Agrega el panel izquierdo al panel principal en la posición OESTE (izquierda)
        _panel.add(leftPanel, BorderLayout.WEST);

        // Agrega el panel derecho al panel principal en la posición ESTE (derecha)
        _panel.add(rightPanel, BorderLayout.EAST);
    }

}