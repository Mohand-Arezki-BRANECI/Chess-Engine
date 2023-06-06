package com.example.jfxchess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public class King extends Piece {

    private final boolean isCastled;

    public King(int piecePosition, Color pieceColor, boolean firstMove, boolean isCastled) {
        super(PieceName.KING, piecePosition, pieceColor, firstMove);
        this.isCastled = isCastled;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    private static final int RIGHT = 1;
    private static final int BOTTOM_LEFT = 7;
    private static final int BOTTOM = 8;
    private static final int BOTTOM_RIGHT = 9;
    private static final int TOP_LEFT = -9;
    private static final int TOP = -8;
    private static final int TOP_RIGHT = -7;
    private static final int LEFT = -1;

    int[] movePossible = { LEFT, TOP_RIGHT, TOP, TOP_LEFT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, RIGHT };
    int moveDestination;

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveCurrent : movePossible) {
            moveDestination = this.piecePosition + moveCurrent;

            if (firstColumnException(this.piecePosition, moveCurrent)
                    || eightColumnException(this.piecePosition, moveCurrent)) {
                continue;
            }
            Move legalMove = tryCreateMove(board, moveDestination);
            if (legalMove != null) {
                legalMoves.add(legalMove);

            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceName.KING.toString();
    }

    private boolean firstColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.FIRST_COLUMN[moveCurrent]
                && (moveOutOfBounds == TOP_LEFT || moveOutOfBounds == LEFT || moveOutOfBounds == BOTTOM_LEFT);
    }

    private boolean eightColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.EIGHT_COLUMN[moveCurrent]
                && (moveOutOfBounds == BOTTOM_RIGHT || moveOutOfBounds == RIGHT || moveOutOfBounds == TOP_RIGHT);
    }

    @Override
    public Piece getMovedPiece(int position, Color color) {
        return new King(position, color, false, false);
    }

}
