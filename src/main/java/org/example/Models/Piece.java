package org.example.Models;
/**
 * Clase que representa una pieza de ajedrez, incluyendo su color y tipo.
 *
 * @param color Indica el color de la pieza. `true` representa las piezas blancas y `false` las negras.
 * @param piece Tipo de la pieza, representado por la enumeración `Pieces`.
 */
public record Piece(boolean color, Pieces piece) {}
