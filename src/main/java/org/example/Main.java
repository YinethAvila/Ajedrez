package org.example;

import org.example.Services.ViewServices;
import org.example.Views.MainView;
import org.example.Views.View;

import javax.swing.*;

/**
 * Clase principal para la aplicación de ajedrez. Configura y muestra la ventana principal de la aplicación.
 */
public class Main {

    /**
     * Método principal que configura la interfaz gráfica de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Configura el tamaño de la ventana usando las propiedades estáticas xSize e ySize de la clase ViewServices.
        ViewServices.xSize = 800;  // Ancho de la ventana en 800 píxeles.
        ViewServices.ySize = 600;  // Alto de la ventana en 600 píxeles.

        // Crea una nueva ventana (JFrame) con el título "Ajedrez".
        ViewServices.frame = new JFrame("Ajedrez");

        // Configura la operación de cierre de la ventana para que el programa termine cuando se cierre la ventana.
        ViewServices.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establece que la ventana no pueda cambiar de tamaño.
        ViewServices.frame.setResizable(false);

        // Centra la ventana en la pantalla.
        ViewServices.frame.setLocationRelativeTo(null);

        // Establece la posición y el tamaño de la ventana con las dimensiones definidas anteriormente.
        ViewServices.frame.setBounds(0, 0, ViewServices.xSize, ViewServices.ySize);

        // Crea un panel principal (JPanel) para contener los elementos de la interfaz de usuario.
        ViewServices.mainPanel = new JPanel();

        // Configura el layout del panel a 'null' para posicionar los elementos manualmente (layout absoluto).
        ViewServices.mainPanel.setLayout(null);

        // Añade el panel principal al frame.
        ViewServices.frame.add(ViewServices.mainPanel);

        // Crea una instancia de la vista principal (MainView) y cambia la vista actual a la vista principal.
        View mainView = new MainView();
        mainView.changeView();

        // Hace visible la ventana en la pantalla.
        ViewServices.frame.setVisible(true);
    }
}
