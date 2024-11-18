package org.example.Controller;

import org.example.Services.FileServices;
import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.Implementations.TableServicesImpl;
import org.example.Services.TableServices;

public class GameController {

    private final TableServicesImpl _tableServicesImpl;
    private final FileServices _fileServicesImpl = FileServicesImpl.getInstance();

    public GameController(String url){
        this._tableServicesImpl = new TableServicesImpl();
        _tableServicesImpl.createAllPieces();
    }

    public TableServices getTable(){
        return _tableServicesImpl;
    }

    public void NextMovement(){
        _tableServicesImpl.makeNextMovement();
    }

    public void PrevMovement(){
        _tableServicesImpl.makePrevMovement();
    }

    public void seeTable(){
        _tableServicesImpl.seeTable();
    }

}
