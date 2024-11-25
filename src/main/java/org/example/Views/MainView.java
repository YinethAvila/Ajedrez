package org.example.Views;

import org.example.Services.Implementations.AudioServicesImpl;
import org.example.Services.Implementations.GameServicesImpl;
import org.example.Services.ViewServices;
import javax.swing.*;
import java.io.File;
import java.util.function.Consumer;

/**
 * Clase que representa la vista principal de la aplicación de ajedrez.
 * Esta clase implementa la interfaz {@link View} y es responsable de cambiar y mostrar la vista inicial,
 * que incluye la opción de cargar un archivo de juego o iniciar una nueva partida.
 */
public class MainView implements View {

    /** El panel principal de la vista, que contiene los botones. */
    private final JPanel _panel = new JPanel();

    /**
     * Cambia la vista actual y la actualiza en el panel principal.
     * Este método remueve los componentes existentes en el panel principal,
     * luego muestra la vista actualizada y la vuelve a agregar al panel principal.
     * Se llama cuando se necesita cambiar entre diferentes vistas en la interfaz gráfica.
     *
     * @see ViewServices#mainPanel
     */
    @Override
    public void changeView() {
        // Limpiar la vista actual
        ViewServices.mainPanel.removeAll();
        // Mostrar la nueva vista
        showView();
        // Añadir el panel con los nuevos componentes
        ViewServices.mainPanel.add(_panel);
        // Revalidar y repintar el panel para reflejar los cambios
        ViewServices.mainPanel.revalidate();
        ViewServices.mainPanel.repaint();
    }

    /**
     * Configura los elementos de la vista, como los botones de "Leer Archivo" y "Iniciar Juego".
     * Los botones se posicionan en la ventana y se les asigna su funcionalidad respectiva.
     * Este método es invocado por {@link #changeView()} para actualizar la vista cuando se cambia.
     *
     * @see ViewServices#createButton(String, int, int, int, int, java.awt.event.ActionListener)
     */
    @Override
    public void showView() {
        // Configura el tamaño y el diseño del panel
        _panel.setBounds(0, 0, ViewServices.frame.getWidth(), ViewServices.frame.getHeight());
        _panel.setLayout(null);

        // Crear el botón "Read File" que permite al usuario seleccionar un archivo de juego
        JButton readFile = ViewServices.createButton(
                "Read File",
                ViewServices.frame.getWidth()/3,
                (ViewServices.frame.getHeight()/2)-20,
                100,
                40,
                e -> {
                    // Mostrar el selector de archivos
                    JFileChooser fileChooser = new JFileChooser("./src/main/resources/games");
                    int result = fileChooser.showOpenDialog(ViewServices.frame);

                    // Si se seleccionó un archivo, almacenar la ruta
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        // Consumir la ruta del archivo y almacenarla
                        Consumer<String> filePathConsumer = path -> ViewServices.gamePath = path;
                        filePathConsumer.accept(selectedFile.getAbsolutePath());
                    }
                }
        );

        // Crear el botón "Start Game" para iniciar el juego después de seleccionar un archivo
        JButton startGame = ViewServices.createButton(
                "Start Game",
                (ViewServices.frame.getWidth()/3) + 150,
                (ViewServices.frame.getHeight()/2) - 20,
                100,
                40,
                e -> {
                    // Verificar si se ha seleccionado un archivo de juego
                    if (ViewServices.gamePath == null) {
                        // Mostrar un mensaje de error si no se seleccionó un archivo
                        JOptionPane.showMessageDialog(ViewServices.frame, "Please select a game", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Cargar el juego desde el archivo seleccionado y empezar el juego
                        GameServicesImpl.getInstance().getGameFromFile(ViewServices.gamePath);
                        AudioServicesImpl.getInstance().playBackgroundMusic();
                        new GameView().changeView();
                    }
                }
        );

        // Añadir los botones al panel
        _panel.add(readFile);
        _panel.add(startGame);
    }
}
