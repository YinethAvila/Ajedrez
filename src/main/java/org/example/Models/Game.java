package org.example.Models;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un juego de ajedrez, incluyendo los turnos y el ganador.
 */
@Getter
public class Game {

    /** Lista de turnos que han ocurrido durante el juego. */
    private final List<Turn> _gameTurns = new ArrayList<>();

    /** Jugador que ha ganado el juego. Puede ser null si el juego no tiene un ganador definido. */
    private final Player _winner;

    /**
     * Constructor de la clase Game.
     *
     * @param winner El jugador que ganó el juego. Puede ser null si no hay un ganador.
     */
    public Game(Player winner) {
        this._winner = winner;
    }
}
