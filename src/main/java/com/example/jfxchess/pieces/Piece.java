package com.example.jfxchess.pieces;

import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.board.MoveAttack;
import com.example.jfxchess.board.MoveNormal;
import com.example.jfxchess.board.Tile;

public abstract class Piece {

    int piecePosition;
    Color pieceColor;
    PieceName pieceName;
    boolean firstMove;

    Piece(PieceName pieceName, int piecePosition, Color pieceColor, boolean firstMove) {
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.pieceName = pieceName;
        this.firstMove = firstMove;
    }

    public abstract List<Move> legalMoves(Board board);

    public Color getPieceColor() {
        return this.pieceColor;
    }

    public Object getPieceType() {
        return this.pieceName;
    }

    public Integer getPiecePosition() {
        return piecePosition;
    }

    public boolean firstMove() {
        return this.firstMove;
    }

    public abstract Piece getMovedPiece(int position, Color color);

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (! (other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return this.piecePosition == otherPiece.piecePosition && this.pieceName == otherPiece.pieceName &&
               this.pieceColor == otherPiece.pieceColor && this.firstMove == otherPiece.firstMove;
    }

    protected Move tryCreateMove(Board board, int moveDestination) {
        if (Board.isValidCoordinate(moveDestination)) {
            Tile moveDestinationTile = board.getTile(moveDestination);
            if (moveDestinationTile.isTileOccupied()) {

                if (this.pieceColor != moveDestinationTile.getPiece().getPieceColor()) {
                    return (new MoveAttack(board, this, moveDestination, moveDestinationTile.getPiece()));
                }
            } else {
                return (new MoveNormal(board, this, moveDestination));
            }
        }
        return null;
    }

    public int getPieceValue() {
        return this.pieceName.getPieceValue();
    }
    public String getPieceName() {
        return this.pieceName.getPieceName();
    }

    public enum PieceName {
        PAWN(120, "P"),
        ROOK(600, "R"),
        KNIGHT(250, "N"),
        BISHOP(300, "B"),
        QUEEN(1000, "Q"),
        KING(10000, "K");

        private final String pieceName;
        private final int pieceValue;

        PieceName(int pieceValue, String pieceName) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getPieceValue() {
            return this.pieceValue;
        }

        public String getPieceName() {
            return this.pieceName;
        }
    }
}
