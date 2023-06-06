package com.example.jfxchess.board;

import com.example.jfxchess.pieces.Piece;

public abstract class Tile {

    int tileCoordinate;

    protected Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile newTile(int tileCoordinate, Piece piece) {
        if (piece == null) {
            return new TileEmpty(tileCoordinate);
        } else {
            return new TileOccupied(tileCoordinate, piece);
        }
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }
}
