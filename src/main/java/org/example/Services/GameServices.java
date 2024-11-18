package org.example.Services;

import org.example.Models.Turn;

import java.util.ArrayList;

public interface GameServices {
    void getMetadataFromLine(String line);
    void getGameFromFile(String line);
    void getTurnFromLine(String line);
    ArrayList<Turn> getTurns();
}
