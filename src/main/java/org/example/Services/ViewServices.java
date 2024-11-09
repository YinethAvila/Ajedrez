package org.example.Services;

import org.example.Views.View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class ViewServices {
    public static int xSize;
    public static int ySize;
    public static String gamePath;
    public static JFrame frame;
    public static JPanel mainPanel;
    public static Clip clip;

    public static void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void playBackgroundMusic() {
        try{
            // Abrir el archivo de audio
            System.out.println(FileServices.getSourcesUrl("audio/sound.wav"));
            File musicPath = new File(FileServices.getSourcesUrl("audio/sound.wav"));
            if (musicPath.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Configurar la música para que se reproduzca en bucle
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                // Iniciar la reproducción
                clip.start();
            } else {
                JOptionPane.showMessageDialog(ViewServices.frame, "Song file don't exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeView(View view) {
        view.changeView();
    }



    public static JButton createButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(actionListener);
        return button;
    }

}
