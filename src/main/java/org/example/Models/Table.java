package org.example.Models;

/**
 * Clase que representa un tablero de ajedrez.
 *
 * @param table Matriz bidimensional que representa el estado actual del tablero. Cada celda contiene una instancia de {@link Piece} o es null si está vacía.
 */
public record Table(Piece[][] table) {}
