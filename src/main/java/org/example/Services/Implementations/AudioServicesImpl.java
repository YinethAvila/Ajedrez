package org.example.Services.Implementations;

import org.example.Services.AudioServices;
import org.example.Services.FileServices;
import org.example.Services.ViewServices;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Implementación de la interfaz {@link AudioServices} que gestiona la reproducción de música de fondo.
 * Esta clase permite la reproducción, detención y control de volumen de la música de fondo,
 * utilizando la API de {@link AudioSystem} para cargar y manejar los archivos de audio.
 */
public class AudioServicesImpl implements AudioServices {

    /** Instancia global de {@link AudioServicesImpl}. */
    private static AudioServices _globalInstance;

    /** Servicio encargado de manejar los archivos. */
    private final FileServices _fileServices;

    /** Reproductor de audio para manejar la música de fondo. */
    private Clip _clip;

    /**
     * Constructor privado para asegurar que {@link AudioServicesImpl} es un singleton.
     * Inicializa el servicio de archivos.
     */
    private AudioServicesImpl(){
        _fileServices = FileServicesImpl.getInstance();
    }

    /**
     * Detiene la música de fondo si está en reproducción.
     * Este método detiene la ejecución del {@link Clip} y lo cierra.
     */
    @Override
    public void stopBackgroundMusic() {
        if(_clip != null && _clip.isRunning()) {
            _clip.stop(); // Detiene la música
            _clip.close(); // Cierra el clip de audio
        }
    }

    /**
     * Reproduce la música de fondo de manera continua.
     * Carga el archivo de audio, lo inicializa en un {@link Clip} y ajusta su volumen.
     * Si ocurre algún error (por ejemplo, si el archivo no existe), muestra un mensaje de error.
     */
    @Override
    public void playBackgroundMusic() {
        try {
            // Obtiene la ruta del archivo de audio
            File audioPath = new File(_fileServices.getResourceURL("audio", "sound.wav"));
            if(!audioPath.exists()) throw new FileNotFoundException();

            // Carga el archivo de audio en un stream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
            _clip = AudioSystem.getClip();
            _clip.open(audioStream);

            // Reproduce el clip de forma continua
            _clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Ajusta el volumen
            setVolume(-12f);

            // Inicia la reproducción del audio
            _clip.start();

        }catch (Exception e) {
            // Muestra un mensaje de error si el archivo de audio no existe
            JOptionPane.showMessageDialog(ViewServices.frame, "Song file doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ajusta el volumen de la música de fondo.
     *
     * @param volume El nivel de volumen en decibelios. Un valor negativo reduce el volumen.
     */
    @Override
    public void setVolume(float volume) {
        // Control de volumen del clip
        FloatControl volumeControl = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume); // Establece el volumen
    }

    /**
     * Obtiene la instancia global de {@link AudioServicesImpl}.
     * Si la instancia aún no existe, la crea.
     *
     * @return La instancia global de {@link AudioServicesImpl}.
     */
    public static AudioServices getInstance() {
        if(_globalInstance == null) _globalInstance = new AudioServicesImpl();
        return _globalInstance;
    }
}