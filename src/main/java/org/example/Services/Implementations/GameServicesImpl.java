package org.example.Services.Implementations;

import org.example.Models.Metadata;
import org.example.Models.Player;
import org.example.Models.Turn;
import org.example.Services.FileServices;
import org.example.Services.GameServices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementación de la interfaz {@link GameServices} que gestiona la lectura de los metadatos y los turnos
 * de un archivo de partida de ajedrez, y proporciona acceso a estos datos en formato estructurado.
 * Esta clase también implementa el patrón Singleton para garantizar que solo exista una instancia global.
 */
public class GameServicesImpl implements GameServices {

    /** Instancia global de {@link GameServicesImpl} para el patrón Singleton. */
    private static GameServices _globalInstance;

    /** Instancia de {@link FileServices} para leer archivos. */
    private final FileServices _fileServices = FileServicesImpl.getInstance();

    /** Metadatos de la partida. */
    private final Metadata _metadata = new Metadata();

    /** Lista de turnos de la partida. */
    private final ArrayList<Turn> _turns = new ArrayList<>();

    // Singleton

    /**
     * Constructor privado para evitar la creación de instancias externas.
     */
    private GameServicesImpl(){}

    /**
     * Obtiene la instancia global de {@link GameServicesImpl}, implementando el patrón Singleton.
     *
     * @return La instancia única de {@link GameServicesImpl}.
     */
    public static GameServices getInstance(){
        if(_globalInstance == null) _globalInstance = new GameServicesImpl();
        return _globalInstance;
    }

    /**
     * Extrae los metadatos de una línea de texto y los asigna a los atributos correspondientes en {@link Metadata}.
     *
     * @param line La línea de texto que contiene la información de los metadatos.
     */
    @Override
    public void getMetadataFromLine(String line) {
        // Expresión regular para extraer los valores entre comillas
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);
        if(!matcher.find()) return;

        // Asignar el valor extraído a los metadatos según el tipo de información
        if(line.contains("Event")) _metadata.setEvent(matcher.group(1));
        else if (line.contains("Site")) _metadata.setSite(matcher.group(1));
        else if (line.contains("Date")) _metadata.setEventDate(matcher.group(1));
        else if (line.contains("White")) _metadata.setWhite(new Player(matcher.group(1), true));
        else if (line.contains("Black")) _metadata.setBlack(new Player(matcher.group(1), false));
        else if (line.contains("Result")) _metadata.setResult(matcher.group(1));
    }

    /**
     * Lee un archivo de juego de ajedrez desde una ruta especificada, procesando tanto los metadatos como los turnos.
     *
     * @param path La ruta del archivo de juego que se desea leer.
     */
    @Override
    public void getGameFromFile(String path) {
        List<String> file = _fileServices.readFile(path);
        file.forEach(line -> {
            if (line.matches("^[0-9].*")) getTurnFromLine(line);  // Procesar los turnos
            else if (line.startsWith("[")) getMetadataFromLine(line);  // Procesar los metadatos
        });
    }

    /**
     * Extrae los movimientos de las blancas y negras de una línea de texto y los agrega a la lista de turnos.
     * Utiliza una expresión regular para identificar los movimientos de ambos jugadores en cada turno.
     *
     * @param line La línea de texto que contiene los movimientos de los jugadores.
     */
    @Override
    public void getTurnFromLine(String line) {
        // Expresión regular para capturar los movimientos de las blancas y negras
        Pattern pattern = Pattern.compile("\\d+\\.\\s*(\\S+)\\s*(\\S+)?");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String whiteMove = matcher.group(1);  // Movimiento de las blancas
            String blackMove = matcher.group(2);  // Movimiento de las negras (puede ser null)

            // Filtrar movimientos inválidos (de tipo "1-0", "0-1", etc.)
            if (whiteMove.matches("\\d+-\\d+")) whiteMove = null;
            if (blackMove.matches("\\d+-\\d+")) blackMove = null;

            // Crear un objeto Turn y agregarlo a la lista de turnos
            _turns.add(new Turn(whiteMove, blackMove));
        }
    }

    /**
     * Devuelve la lista de turnos de la partida.
     *
     * @return Una lista de objetos {@link Turn} que representan los turnos de la partida.
     */
    @Override
    public ArrayList<Turn> getTurns() {
        return _turns;
    }

}
