package org.example.Controller;

import org.example.Models.Piece;
import org.example.Models.Pieces;
import org.example.Services.FileServices;
import org.example.Services.TableServices;

import java.nio.file.Paths;

public class GameController {

    private final TableServices _tableServices;
    private final FileServices _fileServices;

    public GameController(String url){
        this._tableServices = new TableServices();
        this._fileServices = new FileServices(Paths.get(url));
    }

    public void startGame(){
        _tableServices.createAllPieces();
        System.out.println(_fileServices.get_metadata().toString());
    }

}
