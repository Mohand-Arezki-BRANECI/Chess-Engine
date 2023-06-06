package com.example.jfxchess.board;

import com.example.jfxchess.pieces.Piece;

public class TileEmpty extends Tile {

    public TileEmpty(int tileCoordinate) {
        super(tileCoordinate);
    }

    @Override
    public boolean isTileOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

    @Override
    public String toString(){
        return "-";
    }
    
}