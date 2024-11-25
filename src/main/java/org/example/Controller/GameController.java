package org.example.Controller;

import org.example.Services.FileServices;
import org.example.Services.Implementations.FileServicesImpl;
import org.example.Services.Implementations.TableServicesImpl;
import org.example.Services.TableServices;
/**
 * Clase que controla la lógica principal del juego de ajedrez.
 * Utiliza servicios para manejar las operaciones sobre el tablero
 * y la carga/guardado de partidas.
 */
public class GameController {

    /** Servicio para gestionar las operaciones sobre el tablero de ajedrez. */
    private final TableServicesImpl _tableServicesImpl;

    /** Servicio para manejar la carga y guardado de archivos del juego. */
    private final FileServices _fileServicesImpl = FileServicesImpl.getInstance();

    /**
     * Constructor de la clase GameController.
     * Inicializa los servicios de tablero y crea todas las piezas del juego.
     *
     * @param url La URL que podría utilizarse para cargar un archivo de juego (no utilizada actualmente).
     */
    public GameController(String url) {
        this._tableServicesImpl = new TableServicesImpl();
        _tableServicesImpl.createAllPieces();
    }

    /**
     * Obtiene el servicio que gestiona las operaciones sobre el tablero.
     *
     * @return El servicio de operaciones sobre el tablero.
     */
    public TableServices getTable() {
        return _tableServicesImpl;
    }

    /**
     * Realiza el siguiente movimiento en el juego.
     */
    public void NextMovement() {
        _tableServicesImpl.makeNextMovement();
    }

    /**
     * Revierte el movimiento anterior en el juego.
     */
    public void PrevMovement() {
        _tableServicesImpl.makePrevMovement();
    }

    /**
     * Muestra el estado actual del tablero.
     */
    public void seeTable() {
        _tableServicesImpl.seeTable();
    }
}
