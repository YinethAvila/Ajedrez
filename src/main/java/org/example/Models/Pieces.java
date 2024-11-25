package org.example.Models;

import lombok.Getter;

import lombok.Getter;

/**
 * Enumeración que representa los diferentes tipos de piezas en un juego de ajedrez.
 * Cada tipo de pieza tiene un valor numérico asociado y un nombre.
 */
@Getter
public enum Pieces {

    /** El Rey (King) es la pieza más importante en el ajedrez. */
    KING(1, "King"),

    /** La Torre (Rook) se mueve en línea recta, horizontal o verticalmente. */
    ROOK(2, "Rook"),

    /** La Dama (Queen) es la pieza más poderosa, moviéndose en cualquier dirección. */
    QUEEN(3, "Queen"),

    /** El Alfil (Bishop) se mueve diagonalmente. */
    BISHOP(4, "Bishop"),

    /** El Caballo (Knight) se mueve en forma de "L". */
    KNIGHT(5, "Knight"),

    /** El Peón (Pawn) avanza hacia adelante y captura en diagonal. */
    PAWN(6, "Pawn");

    /** Valor numérico asociado a la pieza de ajedrez. */
    private final int value;

    /** Nombre de la pieza de ajedrez en formato de texto. */
    private final String name;

    /**
     * Constructor para inicializar una pieza de ajedrez con un valor y un nombre.
     *
     * @param i El valor numérico que representa el tipo de pieza.
     * @param name El nombre textual del tipo de pieza.
     */
    Pieces(int i, String name) {
        this.value = i;
        this.name = name;
    }
}
