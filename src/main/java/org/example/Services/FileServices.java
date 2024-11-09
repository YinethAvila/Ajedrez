package org.example.Services;

import lombok.Getter;
import lombok.Setter;
import org.example.Main;
import org.example.Models.Game;
import org.example.Models.GameMetadata;
import org.example.Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class FileServices {

    private GameMetadata _metadata = new GameMetadata();
    private Game _game;
    private Player[] _players;
    private BufferedReader _reader;

    private void readMetadata(String data){
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(data);
        if(!matcher.find()) return;
        if(data.contains("Event")) _metadata.setEvent(matcher.group(1));
        else if (data.contains("Site")) _metadata.setSite(matcher.group(1));
        else if (data.contains("Date")) _metadata.setEventDate(matcher.group(1));
        else if (data.contains("White")) _metadata.setWhite(new Player(matcher.group(1), true));
        else if (data.contains("Black")) _metadata.setBlack(new Player(matcher.group(1), false));
        else if (data.contains("Result")) _metadata.setResult(matcher.group(1));
    }

    public static ImageIcon getIcon(String name){
        String imageUrl = FileServices.getSourcesUrl("pieces/"+name);
        ImageIcon image = new ImageIcon(imageUrl);
        Image scaled = image.getImage().getScaledInstance(40, 45, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static String getSourcesUrl(String resource){
        String path = "";
        try {
            path = Main.class.getClassLoader().getResource(resource).getPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        return path;
    }

    public static ActionListener getFilePath(JFrame frame, Consumer<String> filePathConsumer){
        return e -> {
            JFileChooser fileChooser = new JFileChooser("./src/main/resources/games");

            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathConsumer.accept(selectedFile.getAbsolutePath());
            }
        };
    }

    public FileServices(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if(line.startsWith("[")){
                    readMetadata(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
