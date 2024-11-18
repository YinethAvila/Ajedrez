package org.example.Services;

import org.example.Models.Table;
import org.example.Models.Turn;

public interface TableServices {
    void createAllPieces();
    void makeNextMovement();
    void makePrevMovement();
    Table getTable();
    void seeTable();
}
