package org.example.Services;

import org.example.Models.Table;
import org.example.Models.Turn;

/**
 * Interfaz que define los servicios relacionados con la gestión de las piezas y movimientos
 * en el tablero de ajedrez.
 * Las clases que implementen esta interfaz deberán proporcionar métodos para crear piezas,
 * realizar movimientos, y visualizar el estado actual del tablero.
 */
public interface TableServices {

    /**
     * Crea todas las piezas en el tablero, colocándolas en sus posiciones iniciales.
     * Este método debe configurar el tablero con las piezas en sus posiciones de inicio
     * al comienzo de una nueva partida.
     */
    void createAllPieces();

    /**
     * Realiza el siguiente movimiento en la partida.
     * Este método debe actualizar el estado del tablero según el próximo movimiento válido,
     * y modificar las piezas en consecuencia.
     */
    void makeNextMovement();

    /**
     * Realiza el movimiento anterior en la partida.
     * Este método deshace el último movimiento realizado y devuelve el tablero al estado anterior.
     */
    void makePrevMovement();

    /**
     * Obtiene el objeto {@link Table} que representa el estado actual del tablero de ajedrez.
     * Este objeto contiene las piezas y su disposición en el tablero.
     *
     * @return El tablero actual con las piezas y su estado.
     */
    Table getTable();

    /**
     * Muestra el estado actual del tablero de ajedrez en la consola o interfaz de usuario.
     * Este método puede usarse para depurar o visualizar la disposición de las piezas en el tablero.
     */
    void seeTable();
}
