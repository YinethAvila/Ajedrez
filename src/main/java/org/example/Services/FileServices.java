package org.example.Services;

import javax.swing.*;
import java.util.List;
/**
 * Interfaz que define los servicios relacionados con la lectura de archivos y la obtención de recursos dentro de la aplicación.
 * Las clases que implementen esta interfaz serán responsables de leer archivos, obtener rutas de recursos y cargar imágenes.
 */
public interface FileServices {

    /**
     * Lee el contenido de un archivo de texto en una lista de cadenas.
     * Cada línea del archivo será representada como un elemento en la lista.
     *
     * @param path La ruta del archivo que se desea leer.
     * @return Una lista de cadenas donde cada cadena representa una línea del archivo.
     */
    List<String> readFile(String path);

    /**
     * Obtiene la URL de un recurso almacenado en una carpeta dentro del proyecto.
     * Este método puede ser útil para localizar recursos como archivos de audio, imágenes o configuraciones.
     *
     * @param folder El nombre de la carpeta donde se encuentra el recurso.
     * @param fileName El nombre del archivo de recurso.
     * @return La URL del recurso especificado.
     */
    String getResourceURL(String folder, String fileName);

    /**
     * Carga una imagen a partir de un archivo y devuelve un {@link ImageIcon} que puede ser utilizado en la interfaz gráfica.
     *
     * @param fileName El nombre del archivo de imagen que se desea cargar.
     * @return Un {@link ImageIcon} representando la imagen cargada.
     */
    ImageIcon getImageIcon(String fileName);
}
