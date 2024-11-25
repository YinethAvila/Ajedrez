package org.example.Services.Implementations;

import org.example.Models.Piece;
import org.example.Models.Pieces;
import org.example.Models.Table;
import org.example.Models.Turn;
import org.example.Services.GameServices;
import org.example.Services.TableServices;
import org.example.Services.ViewServices;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que implementa los servicios relacionados con el tablero de ajedrez.
 *
 * Esta clase es responsable de gestionar el estado del tablero, la creación de las piezas, el movimiento de las piezas,
 * la gestión de los turnos y la visualización del tablero. Implementa la interfaz {@link TableServices}, proporcionando
 * las operaciones necesarias para simular un juego de ajedrez.
 *
 * Utiliza una instancia de la clase {@link Table} para almacenar el estado del tablero y las piezas. Además, la clase
 * interactúa con el servicio de turnos de juego para coordinar el movimiento de las piezas entre los jugadores y controlar
 * el progreso del juego.
 *
 * Atributos:
 * - {@link Table} _table: Representa el tablero de ajedrez.
 * - {@link GameServices} _gameServices: Proporciona la gestión de los turnos del juego.
 * - {@link ArrayList} {@link Turn} _turns: Lista de los turnos jugados.
 * - int _currentTurn: El turno actual en el juego.
 * - boolean white: Indica si es el turno de las piezas blancas.
 *
 * Métodos:
 * - {@link TableServicesImpl#getInstance()} - Devuelve la instancia global de la clase {@link TableServicesImpl}.
 * - {@link TableServicesImpl#getTable()} - Retorna el objeto {@link Table} actual.
 * - {@link TableServicesImpl#seeTable()} - Muestra el estado actual del tablero en consola.
 * - {@link TableServicesImpl#createAllPieces()} - Crea las piezas iniciales del juego.
 * - {@link TableServicesImpl#makeNextMovement()} - Realiza el movimiento siguiente según el turno actual.
 * - {@link TableServicesImpl#makePrevMovement()} - Realiza el movimiento anterior según el turno previo.
 * - {@link TableServicesImpl#movePiece(String)} - Mueve una pieza según una notación de movimiento.
 *
 */
public class TableServicesImpl implements TableServices {

    /**
     * Atributos:
     *
     * - {@link Table} _table: Instancia de la clase {@link Table}, que representa el tablero de ajedrez. Se inicializa con un arreglo de piezas de 8x8.
     * - {@link Piece}[][] table: Referencia a la matriz de piezas del tablero, proporcionada por el método {@link Table#table()}.
     * - {@link GameServices} _gameServices: Instancia del servicio de juegos {@link GameServices}, gestionando la lógica de turnos del juego.
     * - {@link ArrayList} {@link Turn} _turns: Lista de los turnos jugados, obtenida del servicio de juegos. Se utiliza para realizar movimientos de acuerdo con el historial.
     * - {@link TableServices} _globalInstance: Instancia estática que sigue el patrón de diseño Singleton. Garantiza que solo exista una instancia de la clase {@link TableServicesImpl}.
     * - int _currentTurn: Representa el turno actual dentro del juego. Se utiliza para determinar qué turno está en progreso.
     * - boolean white: Indica si es el turno de las piezas blancas. Este valor se alterna después de cada movimiento.
     *
     * Métodos:
     *
     * - {@link TableServicesImpl#getInstance()} - Método estático que implementa el patrón Singleton, asegurando que se cree solo una instancia de la clase {@link TableServicesImpl}.
     *   Si la instancia global aún no ha sido creada, se crea una nueva instancia; de lo contrario, se devuelve la instancia existente.
     */
    private final Table _table = new Table(new Piece[8][8]);
    private final Piece[][] table = _table.table();
    private final GameServices _gameServices = GameServicesImpl.getInstance();
    private final ArrayList<Turn> _turns = _gameServices.getTurns();
    private static TableServices _globalInstance;
    private int _currentTurn = 0;
    private boolean white = true;

    /**
     * Obtiene la instancia única de {@link TableServicesImpl}.
     *
     * Si la instancia global (_globalInstance) aún no ha sido creada, se crea una nueva instancia de {@link TableServicesImpl}.
     * Si ya existe, se retorna la instancia previamente creada, garantizando el patrón Singleton.
     *
     * @return La instancia única de {@link TableServicesImpl}.
     */
    public static TableServices getInstance() {
        if(_globalInstance == null) _globalInstance = new TableServicesImpl();
        return _globalInstance;
    }


    /**
     * Métodos implementados de la interfaz {@link TableServices}:
     *
     * - {@link TableServicesImpl#getTable()} - Devuelve el tablero de ajedrez representado por la instancia {@link Table}.
     *
     * - {@link TableServicesImpl#seeTable()} - Imprime el tablero de ajedrez en la consola. Si una casilla está vacía, imprime un "0". Si contiene una pieza, imprime el valor de la pieza (según su tipo).
     *   El valor de la pieza se obtiene a través del método {@link Piece#piece()} y su método {@link Piece#getValue()}.
     *
     * - {@link TableServicesImpl#createAllPieces()} - Inicializa todas las piezas en su posición inicial sobre el tablero. Se crean las piezas de las dos primeras filas (para ambos colores: blanco y negro).
     *   Se colocan torres, caballos, alfiles, reyes y reinas en las posiciones correspondientes, así como los peones en la segunda y séptima fila.
     *   Las piezas se crean usando la clase {@link Piece} y el enum {@link Pieces}.
     */
    @Override
    public Table getTable() {
        return _table;
    }

    /**
     * Imprime el tablero en la consola.
     *
     * Este método recorre cada una de las casillas del tablero de ajedrez (de 8x8) y verifica si la casilla está vacía (si es {@code null}).
     * Si la casilla está vacía, imprime un "0". Si contiene una pieza, imprime el valor de la pieza obteniendo su tipo a través de {@link Piece#piece()} y {@link Piece#getValue()}.
     *
     * El tablero es impreso línea por línea para representar el estado actual del juego.
     */
    @Override
    public void seeTable() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(table[i][j] == null) System.out.print("0");
                else System.out.print(table[i][j].piece().getValue());
            }
            System.out.println();
        }
    }

    /**
     * Crea todas las piezas en su posición inicial en el tablero.
     *
     * Este método configura el tablero colocando las piezas de ambos colores (blanco y negro) en sus posiciones iniciales.
     * Las piezas de las filas 1 y 8 (para blanco y negro respectivamente) incluyen torres, caballos, alfiles, reyes y reinas. Las piezas de la fila 2 y 7 son los peones.
     * El proceso se realiza en dos iteraciones:
     * - En la primera, se colocan las piezas mayores (torres, caballos, alfiles, reinas, reyes).
     * - En la segunda, se colocan los peones.
     *
     * Cada pieza es representada por una instancia de la clase {@link Piece}, y se utiliza el enum {@link Pieces} para definir el tipo de pieza correspondiente.
     */
    @Override
    public void createAllPieces() {
        Piece[][] table = _table.table();
        for(int i=0; i<2; i++){
            for (int j=0; j<8; j++){
                if(i==0){
                    if(j==0 || j==7){
                        table[0][j] = new Piece(true, Pieces.ROOK);
                        table[7][j] = new Piece(false, Pieces.ROOK);
                    } else if(j==2 || j==5){
                        table[0][j] = new Piece(true, Pieces.BISHOP);
                        table[7][j] = new Piece(false, Pieces.BISHOP);
                    } else if (j==1 || j==6) {
                        table[0][j] = new Piece(true, Pieces.KNIGHT);
                        table[7][j] = new Piece(false, Pieces.KNIGHT);
                    } else if (j==4) {
                        table[0][j] = new Piece(true, Pieces.KING);
                        table[7][j] = new Piece(false, Pieces.KING);
                    }else{
                        table[0][j] = new Piece(true, Pieces.QUEEN);
                        table[7][j] = new Piece(false, Pieces.QUEEN);
                    }
                }else{
                    table[1][j] = new Piece(true, Pieces.PAWN);
                    table[6][j] = new Piece(false, Pieces.PAWN);
                }
            }
        }
    }

    /**
     * Realiza el siguiente movimiento de la partida, alternando entre las jugadas de las piezas blancas y negras.
     *
     * Este método toma el siguiente turno de la lista de turnos ({@link Turn}) y mueve la pieza correspondiente a ese turno.
     * El turno se alterna entre las piezas blancas y negras después de cada movimiento, y el contador de turnos (_currentTurn) se incrementa.
     * Si es el turno de las piezas blancas, se mueve la pieza blanca, y si es el turno de las piezas negras, se mueve la pieza negra.
     *
     * Después de cada movimiento, el valor de la variable {@code white} se cambia para reflejar el turno contrario.
     */
    @Override
    public void makeNextMovement(){
        if(white){
            movePiece(_turns.get(_currentTurn).whiteMov());
            white = false;
        }else{
            movePiece(_turns.get(_currentTurn).blackMov());
            _currentTurn++;
            white = true;
        }
    }

    /**
     * Realiza el movimiento anterior de la partida, alternando entre las jugadas de las piezas blancas y negras.
     *
     * Este método mueve el tablero un paso atrás, devolviendo el estado del juego al turno anterior. Si es el turno de las piezas blancas,
     * se deshace el movimiento blanco anterior; si es el turno de las piezas negras, se deshace el movimiento negro anterior.
     * El contador de turnos (_currentTurn) se decrementa, y la variable {@code white} se ajusta para reflejar el turno que corresponde.
     *
     * **Problema:** Actualmente, el método no está funcionando correctamente. El manejo de la variable {@code white} está causando un bucle lógico,
     * lo que hace que el movimiento anterior no se realice correctamente. Debe corregirse para manejar de manera más adecuada los turnos y movimientos.
     */
    @Override
    public void makePrevMovement() {
        // Se debe decrementar el contador de turnos antes de realizar un movimiento en reversa
        if(white){
            _currentTurn--;
            white = false;
        }else{
            white = true;
        }

        // El siguiente bloque de código no debería ejecutarse dos veces y debe corregirse para manejar la reversión del turno
        if(white){
            movePiece(_turns.get(_currentTurn).whiteMov());
            white = false;
        }else{
            movePiece(_turns.get(_currentTurn).blackMov());
            _currentTurn++;
            white = true;
        }
    }

    /**
     * Mueve una pieza en el tablero de ajedrez según el movimiento proporcionado.
     *
     * Este método interpreta el movimiento de una pieza en formato de texto y ejecuta la acción correspondiente,
     * llamando al método adecuado para el tipo de pieza (peón, caballo, alfil, torre, rey o reina). Si el movimiento es un enroque corto (O-O),
     * se llama al método {@link #shortCastling()}.
     * Si el movimiento no es válido o el juego ha terminado, se muestra una alerta.
     *
     * @param move El movimiento de la pieza en formato de texto, como "e2e4", "O-O", etc.
     */
    private void movePiece(String move){
        if(move == null) {
            JOptionPane.showMessageDialog(ViewServices.frame, "El Juego ha terminado", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println(move);
        // Enroque corto
        if(move.equals("O-O")) shortCastling();
            // Mover las piezas según el tipo
        else if(determinePieceType(move) == Pieces.PAWN) movePawn(move);
        else if(determinePieceType(move) == Pieces.KNIGHT) moveKnight(move);
        else if(determinePieceType(move) == Pieces.BISHOP) moveBishop(move);
        else if(determinePieceType(move) == Pieces.ROOK) moveRook(move);
        else if(determinePieceType(move) == Pieces.KING) moveKing(move);
        else if(determinePieceType(move) == Pieces.QUEEN) moveQueen(move);
    }

    /**
     * Mueve un peón en el tablero de ajedrez según el movimiento proporcionado.
     *
     * Este método interpreta el movimiento de un peón en formato de texto y mueve la pieza de acuerdo con las reglas del ajedrez.
     * Si el peón está atacando, el método determina la columna de ataque y mueve la pieza en consecuencia.
     * Si el peón no está atacando, se mueve de acuerdo con las reglas normales de los peones, comprobando si la casilla está libre.
     *
     * @param move El movimiento del peón en formato de texto, como "e2e4" o "e7e5".
     */
    private void movePawn(String move){
        int[] position = convertMoveToPosition(move); // Convierte el movimiento en posiciones de fila y columna
        boolean isAttacking = false;
        int attackerCol = -1;

        // Si el movimiento tiene longitud 4, el peón está atacando
        if(move.length() == 4){
            attackerCol = getAttackerCol(move); // Obtiene la columna de ataque
            isAttacking = true;
        }

        int targetRow = 0;
        int targetCol = position[1];

        // Si el peón está atacando
        if(isAttacking){
            if(white){
                targetRow = position[0]-1; // Movimiento hacia adelante para las blancas
            }else{
                targetRow = position[0]+1; // Movimiento hacia adelante para las negras
            }
            targetCol = attackerCol; // Se mueve a la columna de ataque
        }else {
            // Si no está atacando, mueve hacia adelante, buscando el primer peón de la fila
            for (int i = 0; i < 8; i++) {
                Piece piece = table[i][position[1]];
                if (piece == null) continue; // Ignora si la casilla está vacía
                // Si encuentra un peón del mismo color en la fila, lo mueve
                if (piece.piece().getName().equals("Pawn") && piece.color() == white) {
                    targetRow = i;
                    break;
                }
            }
        }
        makeMovement(targetRow, position[0], targetCol, position[1]); // Realiza el movimiento
    }

    /**
     * Mueve un caballo en el tablero de ajedrez según el movimiento proporcionado.
     *
     * Este método interpreta el movimiento de un caballo en formato de texto y mueve la pieza de acuerdo con las reglas del ajedrez.
     * El caballo se mueve en forma de "L", y el método verifica todos los posibles movimientos según su posición actual.
     *
     * @param move El movimiento del caballo en formato de texto, como "Ng1f3".
     */
    private void moveKnight(String move){
        int[] position = convertMoveToPosition(move); // Convierte el movimiento en posiciones de fila y columna
        int targetRow = -1;
        int targetCol = -1;

        // Si el movimiento tiene una longitud de 4 y no es un ataque ni una jugada con jaque (+)
        if(move.length() == 4 && move.charAt(1) != 'x' && move.charAt(move.length()-1) != '+'){
            targetCol = convertLetterToNumber(move); // Convierte la letra de la columna a número
        }

        // Definición de los movimientos posibles del caballo
        int[][] knightMoves = {
                {-2, -1}, {-1, -2}, {-2, 1}, {-1, 2},
                {1, -2}, {2, -1}, {1, 2}, {2, 1}
        };

        // Recorre los posibles movimientos del caballo
        for(int i = 0; i < 8; i++){
            try{
                Piece piece;
                // Si se ha especificado una columna de destino, usa esa columna
                if(targetCol > -1){
                    piece = table[position[0] + knightMoves[i][0]][targetCol];
                }else{
                    // Si no se ha especificado una columna, se usa la columna actual del caballo
                    piece = table[position[0] + knightMoves[i][0]][position[1] + knightMoves[i][1]];
                }

                // Si la casilla está vacía, continúa con el siguiente movimiento
                if(piece == null) continue;

                // Si la pieza encontrada es un caballo del mismo color, se marca como destino
                if(piece.piece().getName().equals("Knight") && piece.color() == white){
                    targetRow = position[0] + knightMoves[i][0];
                    if (targetCol <= -1) {
                        targetCol = position[1] + knightMoves[i][1];
                    }
                    break; // Sale del bucle si encuentra el destino
                }
            }catch (Exception e){}
        }

        // Realiza el movimiento del caballo
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    /**
     * Mueve un alfil en el tablero de ajedrez según el movimiento proporcionado.
     *
     * Este método interpreta el movimiento de un alfil en formato de texto y mueve la pieza de acuerdo con las reglas del ajedrez.
     * El alfil se mueve diagonalmente en cualquier dirección, y este método verifica todas las posibles casillas a lo largo de las diagonales.
     *
     * @param move El movimiento del alfil en formato de texto, como "Bf1c4".
     */
    private void moveBishop(String move){
        int[] position = convertMoveToPosition(move); // Convierte el movimiento en posiciones de fila y columna
        int targetRow = -1;
        int targetCol = -1;

        // Recorre las 4 diagonales posibles del alfil
        for(int i = 0; i < 8; i++){
            try{
                for(int j = 0; j < 4; j++) {
                    Piece piece;
                    try{
                        // Verifica las 4 direcciones diagonales posibles
                        if (j == 0) {
                            piece = table[position[0] + i][position[1] + i];
                            targetRow = position[0] + i;
                            targetCol = position[1] + i;
                        }
                        else if (j == 1) {
                            piece = table[position[0] - i][position[1] + i];
                            targetRow = position[0] - i;
                            targetCol = position[1] + i;
                        }
                        else if (j == 2) {
                            piece = table[position[0] - i][position[1] - i];
                            targetRow = position[0] - i;
                            targetCol = position[1] - i;
                        } else {
                            piece = table[position[0] + i][position[1] - i];
                            targetRow = position[0] + i;
                            targetCol = position[1] - i;
                        }
                    } catch (RuntimeException e) {
                        continue; // Ignora excepciones por fuera del tablero
                    }

                    // Si encuentra una pieza en la casilla, continúa con la siguiente dirección
                    if (piece == null) continue;
                    // Si encuentra un alfil del mismo color, se lanza una excepción y se termina el bucle
                    if (piece.piece().getName().equals("Bishop") && piece.color() == white) {
                        throw new RuntimeException("Found");
                    }
                }
            }catch (Exception e){
                break; // Sale si el movimiento está fuera del tablero
            }
        }

        // Realiza el movimiento del alfil
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    /**
     * Mueve una torre en el tablero de ajedrez según el movimiento proporcionado.
     *
     * Este método interpreta el movimiento de una torre en formato de texto y mueve la pieza de acuerdo con las reglas del ajedrez.
     * La torre se mueve en líneas rectas horizontales o verticales, y el método verifica todas las posibles casillas a lo largo de esas líneas.
     *
     * @param move El movimiento de la torre en formato de texto, como "Ra1a5".
     */
    private void moveRook(String move){
        int[] position = convertMoveToPosition(move); // Convierte el movimiento en posiciones de fila y columna
        int targetRow = -1;
        int targetCol = -1;

        // Recorre las 4 direcciones posibles de la torre: derecha, izquierda, arriba, abajo
        for(int i = 0; i < 8; i++){
            try{
                // Itera sobre las 4 direcciones de movimiento de la torre
                for (int j = 0; j < 4; j++) {
                    Piece piece;
                    try{
                        // Movimiento hacia la derecha
                        if(j == 0){
                            piece = table[position[0]][position[1] + i];
                            targetRow = position[0];
                            targetCol = position[1] + i;
                        }
                        // Movimiento hacia la izquierda
                        else if (j == 1){
                            piece = table[position[0]][position[1] - i];
                            targetRow = position[0];
                            targetCol = position[1] - i;
                        }
                        // Movimiento hacia abajo
                        else if (j == 2){
                            piece = table[position[0] + i][position[1]];
                            targetRow = position[0] + i;
                            targetCol = position[1];
                        }
                        // Movimiento hacia arriba
                        else{
                            piece = table[position[0] - i][position[1]];
                            targetRow = position[0] - i;
                            targetCol = position[1];
                        }
                    }catch (Exception e){
                        continue; // Ignora las excepciones, por ejemplo si el índice está fuera de los límites del tablero
                    }

                    // Si la casilla está vacía, continúa con la siguiente
                    if(piece == null) continue;

                    // Si se encuentra una torre del mismo color, se lanza una excepción y se termina el bucle
                    if(piece.piece().getName().equals("Rook") && piece.color() == white) {
                        throw new RuntimeException("Found");
                    }
                }
            }catch (Exception e){
                break; // Sale si el movimiento está fuera de los límites del tablero
            }
        }

        // Realiza el movimiento de la torre
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    /**
     * Realiza el enroque corto para el jugador blanco.
     *
     * Este método mueve el rey blanco dos casillas a la derecha y la torre blanca de la esquina a la casilla junto al rey,
     * de acuerdo con las reglas del enroque corto en ajedrez. El enroque solo es posible si el rey y la torre no se han movido
     * previamente y no hay piezas entre ellos.
     */
    private void shortCastling(){
        int targetColKing = -1;
        int targetRowKing = -1;
        int targetColRook = -1;

        // Busca la posición del rey blanco
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++) {
                Piece piece = table[i][j];
                if(piece == null) continue;
                if(piece.piece().getName().equals("King") && piece.color() == white){
                    targetColKing = j;
                    targetRowKing = i;
                }
            }
        }

        // Revisa las casillas para encontrar la torre blanca
        int spaces = 0;
        for (int i = 0; i < 8; i++) {
            Piece piece = table[targetRowKing][i];
            if (piece == null && targetColRook != -1) spaces++;
            if (piece == null) continue;

            // Identifica la torre blanca
            if(piece.piece().getName().equals("Rook") && piece.color() == white){
                targetColRook = i;
            }

            // Verifica si el rey y la torre pueden hacer enroque
            if(piece.piece().getName().equals("King") && piece.color() == white) {
                if(spaces >= 2) break;
                else {
                    spaces = 0;
                }
            }
        }

        // Realiza el movimiento del rey y la torre para enrocar
        makeMovement(targetRowKing, targetRowKing, targetColKing, (targetColKing + 2));
        makeMovement(targetRowKing, targetRowKing, targetColRook, (targetColRook - 2));
    }

    /**
     * Mueve el rey según el movimiento proporcionado.
     *
     * Este método mueve el rey en una de las 8 direcciones posibles en el tablero: arriba, abajo, izquierda, derecha, y las 4 diagonales.
     * El método verifica las casillas adyacentes al rey y se asegura de que la casilla a la que se mueve esté vacía o ocupada por una pieza contraria.
     *
     * @param move El movimiento del rey en formato de texto, como "Ke1f2".
     */
    private void moveKing(String move){
        int[] position = convertMoveToPosition(move); // Convierte el movimiento a posiciones de fila y columna
        int targetRow = -1;
        int targetCol = -1;

        // Direcciones posibles de movimiento para el rey (8 direcciones)
        int[][] directions = {
                {1, 1},   // Abajo derecha
                {-1, -1}, // Arriba izquierda
                {1, -1},  // Abajo izquierda
                {-1, 1},  // Arriba derecha
                {0, 1},   // Derecha
                {0, -1},  // Izquierda
                {1, 0},   // Abajo
                {-1, 0}   // Arriba
        };

        // Recorre todas las direcciones posibles
        for (int[] dir : directions) {
            int newRow = position[0] + dir[0];
            int newCol = position[1] + dir[1];

            // Verificar si la nueva posición está dentro del rango del tablero
            if (newRow >= 0 && newRow < table.length && newCol >= 0 && newCol < table[newRow].length) {
                try {
                    // Verifica si en la nueva posición hay una pieza del mismo color
                    if (table[newRow][newCol].piece().getName().equals("King") && table[newRow][newCol].color() == white) {
                        targetRow = newRow;
                        targetCol = newCol;
                        break; // Si se encuentra un rey blanco, termina la búsqueda
                    }
                } catch (Exception e) {
                    // Si ocurre un error, sigue buscando en las demás direcciones
                    // No hacer nada en el bloque catch
                }
            }
        }

        // Realiza el movimiento del rey a la posición objetivo
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    /**
     * Mueve la reina según el movimiento proporcionado.
     *
     * Este método mueve la reina en las direcciones posibles, similares a la torre y al alfil:
     * en las 4 direcciones diagonales y en las 4 direcciones ortogonales.
     * La reina puede moverse cualquier número de casillas en estas direcciones, pero debe
     * detenerse si encuentra una pieza del mismo color.
     *
     * @param move El movimiento de la reina en formato de texto, como "Qd1d4".
     */
    private void moveQueen(String move) {
        int[] position = convertMoveToPosition(move); // Convierte el movimiento a posiciones de fila y columna
        int targetRow = -1;
        int targetCol = -1;

        // Direcciones posibles de movimiento para la reina (8 direcciones: 4 diagonales y 4 ortogonales)
        int[][] directions = {
                {1, 1},   // Diagonal superior derecha
                {-1, 1},  // Diagonal inferior derecha
                {-1, -1}, // Diagonal inferior izquierda
                {1, -1},  // Diagonal superior izquierda
                {1, 0},   // Vertical hacia abajo
                {-1, 0},  // Vertical hacia arriba
                {0, 1},   // Horizontal hacia la derecha
                {0, -1}   // Horizontal hacia la izquierda
        };

        // Recorre las 8 direcciones posibles
        for (int i = 0; i < 8; i++) {
            try {
                for (int[] dir : directions) {
                    int newRow = position[0] + (dir[0] * i);
                    int newCol = position[1] + (dir[1] * i);

                    try {
                        // Verifica si la casilla está vacía o tiene una pieza del color contrario
                        if (table[newRow][newCol] == null) continue;

                        // Si encuentra una pieza propia, detiene el movimiento
                        if (table[newRow][newCol].piece().getName().equals("Queen")
                                && table[newRow][newCol].color() == white) {
                            targetRow = newRow;
                            targetCol = newCol;
                            throw new RuntimeException("Found");
                        }
                    } catch (Exception e) {
                        if (e.getMessage().equals("Found")) throw new RuntimeException("Found");
                    }
                }
            } catch (Exception e) {
                break;
            }
        }

        // Realiza el movimiento de la reina a la posición calculada
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    /**
     * Realiza un movimiento de una pieza en el tablero.
     *
     * Este método mueve una pieza desde la casilla de origen (fromRow, fromCol)
     * a la casilla de destino (toRow, toCol). Si la casilla de origen está vacía,
     * el movimiento no se realiza.
     *
     * @param fromRow Fila de la casilla de origen.
     * @param toRow Fila de la casilla de destino.
     * @param fromCol Columna de la casilla de origen.
     * @param toCol Columna de la casilla de destino.
     */
    private void makeMovement(int fromRow, int toRow, int fromCol, int toCol){
        Piece piece = table[fromRow][fromCol]; // Obtiene la pieza en la casilla de origen
        if (piece == null) return; // Si no hay pieza, no se mueve
        table[fromRow][fromCol] = null; // Elimina la pieza de la casilla de origen
        table[toRow][toCol] = piece; // Coloca la pieza en la casilla de destino
    }

    /**
     * Convierte una columna representada por una letra a su índice de columna correspondiente en el tablero.
     *
     * Este método toma la letra que representa una columna en la notación estándar de ajedrez (a-h)
     * y devuelve el índice numérico correspondiente de esa columna (0-7).
     *
     * @param move El movimiento de ajedrez en formato de texto, por ejemplo "e2".
     * @return El índice numérico de la columna, donde 'a' es 0, 'b' es 1, ..., 'h' es 7.
     * @throws IllegalArgumentException Si el movimiento tiene una columna no válida.
     */
    private int getAttackerCol(String move){
        char column = move.charAt(0); // Obtiene la primera letra del movimiento (columna)
        int colIndex = column - 'a';  // Convierte la letra de la columna en un índice (0-7)

        if (colIndex < 0 || colIndex >= 8) {
            throw new IllegalArgumentException("Invalid move"); // Si la columna está fuera de rango, lanza una excepción
        }
        return colIndex;
    }

    /**
     * Convierte una letra que representa una columna en la notación estándar de ajedrez
     * a su índice numérico correspondiente en el tablero.
     *
     * @param move El movimiento en notación estándar, por ejemplo "e2" o "e8".
     * @return El índice numérico de la columna, donde 'a' es 0, 'b' es 1, ..., 'h' es 7.
     * @throws IllegalArgumentException Si la columna está fuera del rango de 'a' a 'h'.
     */
    private int convertLetterToNumber(String move){
        char column = move.charAt(move.length() - 3); // Obtiene la columna de la casilla desde la notación

        int colIndex = column - 'a'; // Convierte la letra de la columna en un índice (0-7)
        if (colIndex < 0 || colIndex > 8) {
            throw new IllegalArgumentException("Columna fuera de rango: " + column); // Lanza excepción si el índice es inválido
        }
        return colIndex;
    }

    /**
     * Convierte un movimiento de ajedrez en notación estándar (como "e2", "d4") a un array de índices de fila y columna.
     *
     * @param move El movimiento en notación estándar (por ejemplo, "e2" o "d4").
     * @return Un array con los índices de fila y columna: [fila, columna].
     * @throws IllegalArgumentException Si el movimiento es inválido o fuera de rango.
     */
    private int[] convertMoveToPosition(String move) {
        if (move.length() < 2) {
            throw new IllegalArgumentException("Movimiento inválido: " + move); // Verifica que el movimiento tenga una longitud válida
        }

        char column;
        char row;

        // Si el movimiento contiene '+', se maneja de forma especial
        if (move.charAt(move.length() - 1) == '+'){
            column = move.charAt(move.length() - 3);
            row = move.charAt(move.length() - 2);
        } else {
            column = move.charAt(move.length() - 2); // Penúltimo carácter es la columna
            row = move.charAt(move.length() - 1);    // Último carácter es la fila
        }

        int colIndex = column - 'a'; // Convierte la columna a su índice numérico
        if (colIndex < 0 || colIndex > 8) {
            throw new IllegalArgumentException("Columna fuera de rango: " + colIndex); // Verifica que la columna esté dentro del rango
        }

        int rowIndex = Character.getNumericValue(row); // Convierte la fila a un número
        if (rowIndex < 0 || rowIndex > 8) {
            throw new IllegalArgumentException("Fila fuera de rango: " + row); // Verifica que la fila esté dentro del rango
        }
        return new int[] { rowIndex - 1, colIndex }; // Devuelve un array con los índices (fila y columna)
    }

    /**
     * Determina el tipo de pieza a partir del movimiento en notación estándar.
     *
     * El método interpreta el primer carácter del movimiento para determinar el tipo
     * de pieza. Si no hay un prefijo de pieza, se asume que el movimiento es para un peón.
     *
     * @param move El movimiento en notación estándar (por ejemplo, "Kf1" o "e2").
     * @return El tipo de la pieza (por ejemplo, KING, QUEEN, etc.).
     */
    private Pieces determinePieceType(String move) {
        char pieceChar = move.charAt(0); // Obtiene el primer carácter del movimiento (representa la pieza)

        switch (pieceChar) {
            case 'K':
                return Pieces.KING; // Si es 'K', es un rey
            case 'Q':
                return Pieces.QUEEN; // Si es 'Q', es una reina
            case 'R':
                return Pieces.ROOK; // Si es 'R', es una torre
            case 'B':
                return Pieces.BISHOP; // Si es 'B', es un alfil
            case 'N':
                return Pieces.KNIGHT; // Si es 'N', es un caballo
            default:
                return Pieces.PAWN; // Si no tiene prefijo, se asume que es un peón
        }
    }

}
