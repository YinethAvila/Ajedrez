package org.example.Services.Implementations;

import org.example.Models.Metadata;
import org.example.Models.Player;
import org.example.Models.Turn;
import org.example.Services.FileServices;
import org.example.Services.GameServices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameServicesImpl implements GameServices {

    private static GameServices _globalInstance;
    private final FileServices _fileServices = FileServicesImpl.getInstance();
    private final Metadata _metadata = new Metadata();
    private final ArrayList<Turn> _turns = new ArrayList<>();

    private GameServicesImpl(){}

    public static GameServices getInstance(){
        if(_globalInstance == null) _globalInstance = new GameServicesImpl();
        return _globalInstance;
    }

    @Override
    public void getMetadataFromLine(String line) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);
        if(!matcher.find()) return;
        if(line.contains("Event")) _metadata.setEvent(matcher.group(1));
        else if (line.contains("Site")) _metadata.setSite(matcher.group(1));
        else if (line.contains("Date")) _metadata.setEventDate(matcher.group(1));
        else if (line.contains("White")) _metadata.setWhite(new Player(matcher.group(1), true));
        else if (line.contains("Black")) _metadata.setBlack(new Player(matcher.group(1), false));
        else if (line.contains("Result")) _metadata.setResult(matcher.group(1));
    }

    @Override
    public void getGameFromFile(String path) {
        List<String> file = _fileServices.readFile(path);
        file.forEach(line -> {
            if (line.matches("^[0-9].*")) getTurnFromLine(line);
            else if (line.startsWith("[")) getMetadataFromLine(line);
        });
    }

    @Override
    public void getTurnFromLine(String line) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\s*\\S+\\s+\\S+)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

    }
}
