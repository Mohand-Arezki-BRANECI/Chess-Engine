package com.example.jfxchess.board;

import com.example.jfxchess.Color;
import com.example.jfxchess.pieces.Piece;

public class TileOccupied extends Tile {

    Piece pieceTile;

    public TileOccupied(int tileCoordinate, Piece piecePosition) {
        super(tileCoordinate);
        this.pieceTile = piecePosition;
    }

    @Override
    public boolean isTileOccupied() {
            return true;

    }

    @Override
    public Piece getPiece() {
        return this.pieceTile;
    }

    @Override
    public String toString() {
        return getPiece().getPieceColor() == Color.BLACK ? getPiece().toString().toLowerCase() : getPiece().toString();
    }

}
