package org.example.Models;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la metadata de una partida de ajedrez,
 * incluyendo información del evento, jugadores y resultado.
 */
@Data
@NoArgsConstructor
public class Metadata {

    /** Nombre del evento en el que se jugó la partida. */
    private String event;

    /** Lugar donde se llevó a cabo el evento. */
    private String site;

    /** Fecha en la que se jugó la partida. */
    private String eventDate;

    /** Jugador que jugó con las piezas blancas. */
    private Player white;

    /** Jugador que jugó con las piezas negras. */
    private Player black;

    /** Resultado de la partida (por ejemplo, "1-0", "0-1", "1/2-1/2"). */
    private String result;
}
