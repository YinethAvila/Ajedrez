package org.example.Models;


/**
 * Clase que representa a un jugador de ajedrez, incluyendo su nombre y el color de las piezas que controla.
 *
 * @param _name  Nombre del jugador.
 * @param _color Color de las piezas que controla el jugador. `true` representa piezas blancas, `false` representa piezas negras.
 */
public record Player(String _name, boolean _color) {
}
