package org.example.Services;

import lombok.Getter;
import org.example.Models.Piece;
import org.example.Models.Pieces;
import org.example.Models.Table;

@Getter
public class TableServices {

    private final Table _table;

    public TableServices() {
        this._table = new Table(new Piece[8][8]);
    }

    public void createAllPieces(){
        Piece[][] table = _table.table();
        for(int i=0; i<2; i++){
            for (int j=0; j<8; j++){
                if(i==0){
                    if(j==0 || j==7){
                        table[0][j] = new Piece(true, Pieces.ROOK);
                        table[7][j] = new Piece(false, Pieces.ROOK);
                    } else if(j==1 || j==6){
                        table[0][j] = new Piece(true, Pieces.BISHOP);
                        table[7][j] = new Piece(false, Pieces.BISHOP);
                    } else if (j==2 || j==5) {
                        table[0][j] = new Piece(true, Pieces.KNIGHT);
                        table[7][j] = new Piece(false, Pieces.KNIGHT);
                    } else if (j==3) {
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

    public void showTable(){
        for(int i=0; i<8; i++){
            if(i == 0){
                System.out.println("   A  B  C  D  E  F  G  H ");
                System.out.println("----------------------------");
            }
            System.out.print((i+1)+" ");
            for(int j=0; j<8; j++){
                Piece[][] table = _table.table();
                if(table[i][j] != null){
                    System.out.print("["+table[i][j].piece().getValue()+"]");
                }else{
                    System.out.print("[ ]");
                }
            }
            System.out.println(" "+(8-i));
            if(i==7){
                System.out.println("----------------------------");
                System.out.println("   A  B  C  D  E  F  G  H ");
            }
        }
    }

    public void movePiece(Piece piece){
        
    }
}
