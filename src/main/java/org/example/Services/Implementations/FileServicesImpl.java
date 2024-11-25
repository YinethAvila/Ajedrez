package org.example.Services.Implementations;

import org.example.Services.FileServices;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementación de la interfaz {@link FileServices} que proporciona métodos para leer archivos,
 * obtener recursos dentro del proyecto y cargar imágenes.
 * Además, esta clase ofrece un método adicional para dividir los movimientos de un juego de ajedrez
 * en turnos individuales.
 */
public class FileServicesImpl implements FileServices {

    /** Instancia global de la clase {@link FileServicesImpl} para el patrón Singleton. */
    private static FileServices _globalInstance;

    /**
     * Constructor privado para evitar la creación de instancias externas.
     */
    private FileServicesImpl(){}

    /**
     * Método que divide una cadena de movimientos de ajedrez en turnos individuales.
     * Utiliza expresiones regulares para identificar los turnos en la cadena de texto de movimientos.
     *
     * @param moves La cadena que contiene todos los movimientos del juego en formato texto.
     * @return Una lista de cadenas, donde cada cadena representa un turno individual del juego.
     */
    public ArrayList<String> splitMovesByTurn(String moves) {
        ArrayList<String> turns = new ArrayList<>();

        // Patrón de expresión regular para identificar los turnos.
        Pattern pattern = Pattern.compile("(\\d+\\.\\s*\\S+\\s+\\S+)");
        Matcher matcher = pattern.matcher(moves);

        // Encontrar todos los turnos en la cadena de movimientos.
        while (matcher.find()) {
            turns.add(matcher.group(1));
        }

        return turns;
    }

    /**
     * Obtiene la instancia global de {@link FileServicesImpl}, implementando el patrón Singleton.
     *
     * @return La instancia única de {@link FileServicesImpl}.
     */
    public static FileServices getInstance() {
        if(_globalInstance == null){
            _globalInstance = new FileServicesImpl();
        }
        return _globalInstance;
    }

    /**
     * Lee un archivo desde la ruta proporcionada y devuelve su contenido como una lista de cadenas,
     * donde cada elemento corresponde a una línea del archivo.
     *
     * @param path La ruta completa del archivo que se desea leer.
     * @return Una lista de cadenas, donde cada cadena es una línea del archivo leído.
     * @throws RuntimeException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<String> readFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene la URL de un recurso dentro del proyecto.
     * Los recursos se encuentran en el directorio "src/main/resources".
     *
     * @param folder El nombre de la carpeta donde se encuentra el recurso.
     * @param fileName El nombre del archivo de recurso.
     * @return La URL del recurso como una cadena.
     * @throws RuntimeException Si ocurre un error al obtener la URL.
     */
    @Override
    public String getResourceURL(String folder, String fileName) {
        try {
            return Paths.get("src", "main", "resources", folder, fileName).toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga una imagen desde el directorio de recursos y la devuelve como un {@link ImageIcon}.
     * La imagen se escala a un tamaño de 40x40 píxeles.
     *
     * @param fileName El nombre del archivo de imagen (sin extensión) que se desea cargar.
     * @return Un {@link ImageIcon} que representa la imagen cargada y escalada.
     */
    @Override
    public ImageIcon getImageIcon(String fileName) {
        ImageIcon icon = new ImageIcon(getResourceURL("pieces", fileName.concat(".png")));
        return new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    }
}

