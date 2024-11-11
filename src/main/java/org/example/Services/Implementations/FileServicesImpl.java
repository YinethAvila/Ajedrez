package org.example.Services.Implementations;

import lombok.Getter;
import lombok.Setter;
import org.example.Main;
import org.example.Models.Player;
import org.example.Services.FileServices;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServicesImpl implements FileServices {

    private static FileServices _globalInstance;

    private FileServicesImpl(){}

    public ArrayList<String> splitMovesByTurn(String moves) {
        ArrayList<String> turns = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+\\.\\s*\\S+\\s+\\S+)");
        Matcher matcher = pattern.matcher(moves);

        while (matcher.find()) {
            turns.add(matcher.group(1));
        }

        return turns;
    }

    public static FileServices getInstance() {
        if(_globalInstance == null){
            _globalInstance = new FileServicesImpl();
        }
        return _globalInstance;
    }

    @Override
    public List<String> readFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResourceURL(String folder, String fileName) {
        try {
            return Paths.get("src", "main", "resources", folder, fileName).toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageIcon getImageIcon(String fileName) {
        ImageIcon icon = new ImageIcon(getResourceURL("pieces", fileName.concat(".png")));
        return new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    }
}
