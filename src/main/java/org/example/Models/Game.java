package org.example.Models;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private final List<Turn> _gameTurns = new ArrayList<Turn>();
    private final Player _winner;

    public Game(Player winner) {
        this._winner = winner;
    }

}
