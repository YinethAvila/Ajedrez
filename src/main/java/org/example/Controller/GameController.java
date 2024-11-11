package org.example.Controller;

import org.example.Services.FileServices;
import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.Implementations.TableServices;

import java.nio.file.Paths;

public class GameController {

    private final TableServices _tableServices;
    private final FileServices _fileServicesImpl = FileServicesImpl.getInstance();

    public GameController(String url){
        this._tableServices = new TableServices();
        _tableServices.createAllPieces();
    }

    public TableServices getTable(){
        return _tableServices;
    }

}
