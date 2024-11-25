package org.example.Services;

import org.example.Views.View;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionListener;
import javax.sound.sampled.Clip;

/**
 * Clase que proporciona servicios estáticos para gestionar la vista y la interfaz gráfica
 * de la aplicación de ajedrez, como la creación de botones y la manipulación de la vista actual.
 */
public class ViewServices {

    /** Ancho de la ventana de la aplicación. */
    public static int xSize;

    /** Alto de la ventana de la aplicación. */
    public static int ySize;

    /** Ruta del archivo de juego (por ejemplo, para cargar una partida guardada). */
    public static String gamePath;

    /** El frame principal de la aplicación. */
    public static JFrame frame;

    /** El panel principal de la interfaz de usuario. */
    public static JPanel mainPanel;

    /** Reproductor de audio para efectos sonoros o música. */
    public static Clip clip;

    /**
     * Cambia la vista actual de la aplicación.
     *
     * @param view La nueva vista que se mostrará.
     */
    public static void changeView(View view) {
        view.changeView();
    }

    /**
     * Crea un botón con texto, tamaño y ubicación específicos, y le asigna un ActionListener.
     *
     * @param text El texto que aparecerá en el botón.
     * @param x La posición X del botón.
     * @param y La posición Y del botón.
     * @param width El ancho del botón.
     * @param height El alto del botón.
     * @param actionListener El ActionListener que se ejecutará cuando el botón sea presionado.
     * @return El botón creado con las especificaciones dadas.
     */
    public static JButton createButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        return button;
    }
}
