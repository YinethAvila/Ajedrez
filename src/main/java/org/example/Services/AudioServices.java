package org.example.Services;

/**
 * Interfaz que define los servicios relacionados con la gestión de la música de fondo en la aplicación.
 * Las clases que implementen esta interfaz deberán proporcionar métodos para reproducir y detener la música de fondo,
 * así como para ajustar el volumen.
 */
public interface AudioServices {

    /**
     * Detiene la música de fondo si está en reproducción.
     * Este método verifica si la música está en ejecución y la detiene, liberando los recursos asociados.
     */
    void stopBackgroundMusic();

    /**
     * Reproduce la música de fondo de la aplicación.
     * Este método carga y reproduce la canción de fondo, y la mantiene en un ciclo continuo durante el uso de la aplicación.
     */
    void playBackgroundMusic();

    /**
     * Establece el volumen de la música de fondo.
     * Este método permite ajustar el volumen de la música de fondo en la aplicación.
     *
     * @param volume El valor del volumen a establecer. Un valor negativo reduce el volumen y los valores más altos aumentan el volumen.
     */
    void setVolume(float volume);
}

