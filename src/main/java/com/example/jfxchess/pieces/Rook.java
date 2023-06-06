package com.example.jfxchess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public class Rook extends Piece {

    public Rook(int piecePosition, Color pieceColor, boolean firstMove) {
        super(PieceName.ROOK, piecePosition, pieceColor, firstMove);
    }

    private static final int TOP = -8;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int BOTTOM = 8;

    int[] movePossible = { TOP, LEFT, RIGHT, BOTTOM };
    int moveDestination;

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveCurrent : movePossible) {
            moveDestination = this.piecePosition;

            while (Board.isValidCoordinate(moveDestination)) {

                if (firstColumnException(moveDestination, moveCurrent)
                        || eightColumnException(moveDestination, moveCurrent)) {
                    break;
                }

                moveDestination += moveCurrent;

                Move legalMove = tryCreateMove(board, moveDestination);
                if (legalMove != null) {
                    legalMoves.add(legalMove);
                    if (legalMove.isAttack()) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceName.ROOK.toString();
    }

    private boolean firstColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.FIRST_COLUMN[moveCurrent] && (moveOutOfBounds == LEFT);
    }

    private boolean eightColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.EIGHT_COLUMN[moveCurrent] && (moveOutOfBounds == RIGHT);
    }

    @Override
    public Piece getMovedPiece(int position, Color color) {
        return new Rook(position, color, false);
    }

}
