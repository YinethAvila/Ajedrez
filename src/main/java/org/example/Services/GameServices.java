package org.example.Services;

import org.example.Models.Turn;

import java.util.ArrayList;

import java.util.ArrayList;

/**
 * Interfaz que define los servicios relacionados con la carga y gestión de los datos de un juego de ajedrez.
 * Las clases que implementen esta interfaz serán responsables de procesar los datos de un juego, ya sea cargándolos
 * desde un archivo o analizando las jugadas del mismo.
 */
public interface GameServices {

    /**
     * Extrae los metadatos de un archivo de juego o una línea de texto que contiene información sobre el juego.
     * Este método debe interpretar la línea y obtener información relevante, como los detalles de los jugadores,
     * la fecha, o el formato del juego.
     *
     * @param line La línea de texto que contiene los metadatos del juego.
     */
    void getMetadataFromLine(String line);

    /**
     * Carga un juego desde un archivo o una ruta proporcionada, y configura el estado del juego en consecuencia.
     * Este método debe interpretar la información del archivo y preparar el tablero, las piezas, y otros
     * aspectos del juego para que se pueda continuar desde ese punto.
     *
     * @param line La ruta o el nombre del archivo desde el que se cargará el juego.
     */
    void getGameFromFile(String line);

    /**
     * Extrae el turno de un archivo o línea de texto que contiene la información sobre el turno actual.
     * Este método debe procesar la línea para obtener el jugador que realiza el movimiento y el tipo de movimiento realizado.
     *
     * @param line La línea de texto que contiene la información del turno.
     */
    void getTurnFromLine(String line);

    /**
     * Devuelve una lista de todos los turnos realizados durante la partida.
     * Esta lista contiene objetos de tipo {@link Turn}, que representan los movimientos realizados en cada turno.
     *
     * @return Una lista de los turnos de la partida.
     */
    ArrayList<Turn> getTurns();
}
