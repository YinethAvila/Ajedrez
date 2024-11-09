package org.example.Models;

import lombok.Getter;

@Getter
public enum Pieces {
    KING(1, "king"),
    ROOK(2, "rook"),
    QUEEN(3, "queen"),
    BISHOP(4, "bishop"),
    KNIGHT(5, "knight"),
    PAWN(6, "pawn"),;

    private final int value;
    private final String name;

    Pieces(int i, String name) {
        this.value = i;
        this.name = name;
    }
}
