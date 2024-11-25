package org.example.Models;

/**
 * Clase que representa un turno en una partida de ajedrez.
 * Cada turno incluye el movimiento realizado por las piezas blancas y negras.
 *
 * @param whiteMov Movimiento realizado por el jugador de piezas blancas en este turno.
 * @param blackMov Movimiento realizado por el jugador de piezas negras en este turno.
 */
public record Turn(String whiteMov, String blackMov) {}
