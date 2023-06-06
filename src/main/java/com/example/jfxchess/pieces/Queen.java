package com.example.jfxchess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public class Queen extends Piece {

    private final boolean isPromote;

    public Queen(int piecePosition, Color pieceColor, boolean firstMove, boolean isPromote) {
        super(PieceName.QUEEN, piecePosition, pieceColor, firstMove);
        this.isPromote = isPromote;
    }

    public boolean isPromote() {
        return this.isPromote;
    }

    private static final int TOP_LEFT = -9;
    private static final int TOP = -8;
    private static final int TOP_RIGHT = -7;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int BOTTOM_LEFT = 7;
    private static final int BOTTOM = 8;
    private static final int BOTTOM_RIGHT = 9;

    int[] movePossible = { LEFT, TOP_RIGHT, TOP, TOP_LEFT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, RIGHT };
    int moveDestination;

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveCurrent : movePossible) {
            moveDestination = this.piecePosition;
            int count = 0;
            while (Board.isValidCoordinate(moveDestination)) {

                if (firstColumnException(moveDestination, moveCurrent)
                        || eightColumnException(moveDestination, moveCurrent)) {
                    break;
                }

                moveDestination += moveCurrent;
                count++;
               
                Move legalMove = tryCreateMove(board, moveDestination);
                if (legalMove != null) {
                    legalMoves.add(legalMove);
                    if (legalMove.isAttack()) {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceName.QUEEN.toString();
    }

    private boolean firstColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.FIRST_COLUMN[moveCurrent]
                && (moveOutOfBounds == LEFT || moveOutOfBounds == TOP_LEFT || moveOutOfBounds == BOTTOM_LEFT);
    }

    private boolean eightColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.EIGHT_COLUMN[moveCurrent]
                && (moveOutOfBounds == RIGHT || moveOutOfBounds == BOTTOM_RIGHT || moveOutOfBounds == TOP_RIGHT);
    }

    @Override
    public Piece getMovedPiece(int position, Color color) {
        return new Queen(position, color, false, false);
    }

}
