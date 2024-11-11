package org.example.Models;

import lombok.Getter;

@Getter
public enum Pieces {
    KING(1, "King"),
    ROOK(2, "Rook"),
    QUEEN(3, "Queen"),
    BISHOP(4, "Bishop"),
    KNIGHT(5, "Knight"),
    PAWN(6, "Pawn"),;

    private final int value;
    private final String name;

    Pieces(int i, String name) {
        this.value = i;
        this.name = name;
    }
}
