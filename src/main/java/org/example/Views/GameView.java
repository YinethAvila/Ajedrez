package org.example.Views;

import org.example.Controller.GameController;
import org.example.Models.Table;
import org.example.Services.Implementations.AudioServicesImpl;
import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.ViewServices;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la vista del juego de ajedrez.
 * Esta clase implementa la interfaz {@link View} y es responsable de mostrar el tablero de ajedrez,
 * los movimientos previos y siguientes, así como gestionar la interfaz gráfica durante el transcurso de una partida.
 */
public class GameView implements View {

    /**
     * Panel principal de la vista del juego, contiene todo el layout.
     */
    private final JPanel _panel = new JPanel(new BorderLayout());

    /**
     * Panel donde se muestra el tablero de ajedrez.
     */
    private final JPanel _gamePanel = new JPanel();

    /**
     * Controlador que gestiona la lógica del juego y las interacciones.
     */
    private final GameController _controller = new GameController(ViewServices.gamePath);

    /**
     * Tabla que representa el estado actual del tablero de ajedrez.
     */
    private final Table _table = _controller.getTable().getTable();

    /**
     * Cambia la vista actual y actualiza los componentes en el panel principal.
     * Este método limpia los componentes existentes en el panel principal,
     * reconstruye la vista y vuelve a agregarla al panel principal.
     */
    @Override
    public void changeView() {
        ViewServices.mainPanel.removeAll(); // Limpia el panel principal
        showView(); // Vuelve a construir los componentes de la vista
        ViewServices.mainPanel.add(_panel); // Agrega el panel reconstruido
        ViewServices.mainPanel.revalidate();
        ViewServices.mainPanel.repaint();
    }

    /**
     * Configura las propiedades del panel izquierdo.
     * Define su tamaño y color de fondo.
     *
     * @param panel El panel izquierdo que se va a configurar.
     */
    private void leftPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth() / 10 * 2, ViewServices.frame.getHeight()));
        panel.setBackground(new Color(248, 250, 252));
    }

    /**
     * Configura el panel inferior, donde se encuentran los botones de navegación de los movimientos.
     *
     * @param panel El panel inferior donde se agregarán los botones.
     */
    private void bottomPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth() / 10 * 8, ViewServices.frame.getHeight() / 10 * 2));
        panel.setBackground(Color.WHITE);

        // Botón para retroceder en los movimientos
        JButton previousButton = ViewServices.createButton(
                "<",
                10,
                10,
                60,
                20,
                e -> {
                    _controller.PrevMovement();
                    changeView();
                }
        );

        // Botón para avanzar en los movimientos
        JButton next = ViewServices.createButton(
                ">",
                10,
                10,
                50,
                20,
                e -> {
                    _controller.NextMovement();
                    changeView();
                }
        );

        panel.add(previousButton, BorderLayout.WEST);
        panel.add(next, BorderLayout.EAST);
    }

    /**
     * Configura el panel principal donde se dibuja el tablero de ajedrez.
     * El tablero se dibuja en una cuadrícula de 10x10, con celdas para las piezas y las coordenadas.
     */
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

        // Dibuja las celdas del tablero
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JPanel square = new JPanel();
                square.setLayout(new BorderLayout());

                // Agrega etiquetas para las filas y columnas
                if (row == 0 || row == 9) {
                    JLabel label = new JLabel(columns[col], SwingConstants.CENTER);
                    square.add(label);
                    square.setBackground(Color.WHITE);
                } else if (col == 0 || col == 9) {
                    JLabel label = new JLabel(rows[row - 1], SwingConstants.CENTER);
                    square.add(label);
                    square.setBackground(Color.WHITE);
                } else {
                    // Alterna los colores del tablero
                    if ((row + col) % 2 == 0) {
                        square.setBackground(lightColor);
                    } else {
                        square.setBackground(darkColor);
                    }
                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);

                    // Muestra las piezas si existen en la posición correspondiente
                    try {
                        if (_table.table()[row - 1][col - 1] != null) {
                            String color = _table.table()[row - 1][col - 1].color() ? "white" : "black";
                            String piece = _table.table()[row - 1][col - 1].piece().getName();
                            label.setIcon(FileServicesImpl.getInstance().getImageIcon(color.concat(piece)));
                        }
                    } catch (Exception e) {
                    }
                    square.add(label);
                }
                _gamePanel.add(square);
            }
        }

        _gamePanel.revalidate();
        _gamePanel.repaint();
    }

    /**
     * Configura el panel derecho que contiene el tablero de ajedrez y el panel inferior con los botones de navegación.
     *
     * @param panel El panel derecho donde se agregan los subcomponentes.
     */
    private void rightPanel(JPanel panel) {
        panel.setPreferredSize(new Dimension(ViewServices.frame.getWidth() / 10 * 8, ViewServices.frame.getHeight()));

        JPanel bottomPanel = new JPanel();

        // Configura el panel inferior
        this.bottomPanel(bottomPanel);

        // Configura el panel principal con el tablero
        this.mainPanel();
        panel.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(_gamePanel, BorderLayout.CENTER);
    }

    /**
     * Muestra la vista del juego, configurando y agregando los paneles izquierdo y derecho al panel principal.
     * Este método es invocado al cambiar de vista.
     */
    @Override
    public void showView() {
        _panel.removeAll();
        _panel.setBounds(0, 0, ViewServices.frame.getWidth(), ViewServices.frame.getHeight());

        // Crea y configura los paneles izquierdo y derecho
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel(new BorderLayout());
        this.leftPanel(leftPanel);
        this.rightPanel(rightPanel);

        // Agrega los paneles al panel principal
        _panel.add(leftPanel, BorderLayout.WEST);
        _panel.add(rightPanel, BorderLayout.EAST);

        _panel.revalidate();
        _panel.repaint();
    }
}