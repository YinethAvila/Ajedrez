package org.example.Services.Implementations;

import lombok.Getter;
import org.example.Models.Piece;
import org.example.Models.Pieces;
import org.example.Models.Table;
import org.example.Models.Turn;
import org.example.Services.GameServices;
import org.example.Services.TableServices;

import java.util.ArrayList;
import java.util.Arrays;

public class TableServicesImpl implements TableServices {

    private final Table _table = new Table(new Piece[8][8]);
    private final Piece[][] table = _table.table();
    private final GameServices _gameServices = GameServicesImpl.getInstance();
    private final ArrayList<Turn> _turns = _gameServices.getTurns();
    private static TableServices _globalInstance;
    private int _currentTurn = 0;
    private boolean white = true;

    public static TableServices getInstance() {
        if(_globalInstance == null) _globalInstance = new TableServicesImpl();
        return _globalInstance;
    }

    @Override
    public Table getTable() {
        return _table;
    }

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

    //No funciona
    @Override
    public void makePrevMovement() {
        if(white){
            _currentTurn--;
            white = false;
        }else{
            white = true;
        }
        if(white){
            movePiece(_turns.get(_currentTurn).whiteMov());
            white = false;
        }else{
            movePiece(_turns.get(_currentTurn).blackMov());
            _currentTurn++;
            white = true;
        }
    }

    private void movePiece(String move){
        System.out.println(move);
        if(move.equals("O-O")) shortCastling();
        else if(determinePieceType(move) == Pieces.PAWN) movePawn(move);
        else if(determinePieceType(move) == Pieces.KNIGHT) moveKnight(move);
        else if(determinePieceType(move) == Pieces.BISHOP) moveBishop(move);
        else if(determinePieceType(move) == Pieces.ROOK) moveRook(move);
    }

    private void movePawn(String move){
        int[] position = convertMoveToPosition(move);
        int targetRow = 0;
        for(int i=0; i<8; i++){
            if(white){
                Piece piece = table[i][position[1]];
                if(piece == null) continue;
                if(piece.piece().getName().equals("Pawn") && piece.color()){
                    targetRow = i;
                    break;
                }
            }else{
                Piece piece = table[7-i][position[0]];
                if(piece == null) continue;
                if(piece.piece().getName().equals("Pawn") && !piece.color()){
                    targetRow = 7-i;
                    break;
                }
            }
        }
        System.out.println(targetRow+ " " + position[0] + " " + position[1]);
        makeMovement(targetRow, position[0], position[1], position[1]);
    }

    private void moveKnight(String move){
        int[] position = convertMoveToPosition(move);
        int targetRow = -1;
        int targetCol = -1;
        if(move.length() == 4){
            targetCol = convertLetterToNumber(move);
        }
        int[][] knightMoves = {
                {-2, -1},
                {-1, -2},
                {-2, 1},
                {-1, 2},
                {1, -2},
                {2, -1},
                {1, 2},
                {2, 1}
        };
        for(int i=0; i<8; i++){
            try{
                Piece piece;
                if(targetCol > -1){
                    piece = table[position[0]+knightMoves[i][0]][targetCol];
                }else{
                    piece = table[position[0] + knightMoves[i][0]][position[1] + knightMoves[i][1]];
                }
                if(piece == null) continue;
                if(piece.piece().getName().equals("Knight") && piece.color() == white){
                    targetRow = position[0] + knightMoves[i][0];
                    if (targetCol <= -1) {
                        targetCol = position[1] + knightMoves[i][1];
                    }
                    break;
                }
            }catch (Exception e){}
        }
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    private void moveBishop(String move){
        int[] position = convertMoveToPosition(move);
        int targetRow = -1;
        int targetCol = -1;
        for(int i=0; i<8; i++){
            try{
                for(int j=0; j<4; j++) {
                    Piece piece;
                    try{
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
                        continue;
                    }
                    if (piece == null) continue;
                    if (piece.piece().getName().equals("Bishop") && piece.color() == white) {
                        throw new RuntimeException("Found");
                    }
                }
            }catch (Exception e){
                break;
            }
        }
        System.out.println(targetRow + " " + targetCol);
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    private void moveRook(String move){
        int[] position = convertMoveToPosition(move);
        System.out.println("ROOK");
        int targetRow = -1;
        int targetCol = -1;
        for(int i=0; i<8; i++){
            try{
                for (int j=0; j<4; j++) {
                    Piece piece;
                    try{
                        if(j==0){
                            piece = table[position[0]][position[1]+i];
                            targetRow = position[0];
                            targetCol = position[1]+i;
                        }else if (j==1){
                            piece = table[position[0]][position[1]-i];
                            targetRow = position[0];
                            targetCol = position[1]-i;
                        }else if (j==2){
                            piece = table[position[0]+i][position[1]];
                            targetRow = position[0]+i;
                            targetCol = position[1];
                        }else{
                            piece = table[position[0]-i][position[1]];
                            targetRow = position[0]-i;
                            targetCol = position[1];
                        }
                    }catch (Exception e){
                        continue;
                    }
                    if (piece == null) continue;
                    if (piece.piece().getName().equals("Rook") && piece.color() == white) {
                        throw new RuntimeException("Found");
                    }
                }
            }catch (Exception e){
                break;
            }
        }
        makeMovement(targetRow, position[0], targetCol, position[1]);
    }

    private void shortCastling(){
        int targetColKing = -1;
        int targetRowKing = -1;
        int targetColRook = -1;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++) {
                Piece piece = table[i][j];
                if(piece==null) continue;
                if(piece.piece().getName().equals("King") && piece.color() == white){
                    targetColKing = j;
                    targetRowKing = i;
                }
            }
        }
        int spaces = 0;
        for (int i=0; i<8; i++) {
            Piece piece = table[targetRowKing][i];
            if(piece==null && targetColRook != -1) spaces++;
            if(piece==null) continue;
            if(piece.piece().getName().equals("Rook") && piece.color() == white){
                targetColRook = i;
            }
            if(piece.piece().getName().equals("King") && piece.color() == white) {
                if(spaces>=2) break;
                else {
                    spaces=0;
                }
            }
        }
        makeMovement(targetRowKing, targetRowKing, targetColKing, (targetColKing + 2));
        makeMovement(targetRowKing, targetRowKing, targetColRook, (targetColRook - 2));
    }

    private void makeMovement(int fromRow, int toRow, int fromCol, int toCol){
        Piece piece = table[fromRow][fromCol];
        if(piece == null) return;
        table[fromRow][fromCol] = null;
        table[toRow][toCol] = piece;
    }

    private int convertLetterToNumber(String move){
        char column = move.charAt(move.length() - 3);

        int colIndex = column - 'a';
        if (colIndex < 0 || colIndex > 8) {
            throw new IllegalArgumentException("Columna fuera de rango: " + column);
        }
        return colIndex;
    }

    private int[] convertMoveToPosition(String move) {
        if (move.length() < 2) {
            throw new IllegalArgumentException("Movimiento inválido: " + move);
        }

        char column = move.charAt(move.length() - 2); // Penúltimo carácter es la columna
        char row = move.charAt(move.length() - 1);    // Último carácter es la fila

        int colIndex = column - 'a';
        if (colIndex < 0 || colIndex > 8) {
            throw new IllegalArgumentException("Columna fuera de rango: " + column);
        }

        int rowIndex =  Character.getNumericValue(row); // Fila 8 → índice 0, fila 1 → índice 7
        if (rowIndex < 0 || rowIndex > 8) {
            throw new IllegalArgumentException("Fila fuera de rango: " + row);
        }
        return new int[] { rowIndex-1, colIndex };
    }

    private Pieces determinePieceType(String move) {
        char pieceChar = move.charAt(0);

        switch (pieceChar) {
            case 'K':
                return Pieces.KING;
            case 'Q':
                return Pieces.QUEEN;
            case 'R':
                return Pieces.ROOK;
            case 'B':
                return Pieces.BISHOP;
            case 'N':
                return Pieces.KNIGHT;
            default:
                // Si no tiene prefijo, es un peón
                return Pieces.PAWN;
        }
    }

}
