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

public class AudioServicesImpl implements AudioServices {

    private static AudioServices _globalInstance;
    private final FileServices _fileServices;
    private Clip _clip;

    private AudioServicesImpl(){
        _fileServices = FileServicesImpl.getInstance();
    }

    @Override
    public void stopBackgroundMusic() {
        if(_clip != null && _clip.isRunning()) {
            _clip.stop();
            _clip.close();
        }
    }

    @Override
    public void playBackgroundMusic() {
        try {
            File audioPath = new File(_fileServices.getResourceURL("audio", "sound.wav"));
            if(!audioPath.exists()) throw new FileNotFoundException();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
            _clip = AudioSystem.getClip();
            _clip.open(audioStream);

            _clip.loop(Clip.LOOP_CONTINUOUSLY);

            setVolume(-12f);

            _clip.start();

        }catch (Exception e) {
            JOptionPane.showMessageDialog(ViewServices.frame, "Song file don't exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setVolume(float volume) {
        FloatControl volumeControl = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    public static AudioServices getInstance() {
        if(_globalInstance == null) _globalInstance = new AudioServicesImpl();
        return _globalInstance;
    }
}
