package com.example.jfxchess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public class Bishop extends Piece {

    public Bishop(int piecePosition, Color pieceColor, boolean firstMove) {
        super(PieceName.BISHOP,piecePosition, pieceColor, firstMove);
    }

    private static final int TOP_LEFT = -9;
    private static final int TOP_RIGHT = -7;
    private static final int BOTTOM_LEFT = 7;
    private static final int BOTTOM_RIGHT = 9;

    int[] movePossible = { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT };
    int moveDestination;

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveCurrent : movePossible) {
            moveDestination = this.piecePosition;

            while (Board.isValidCoordinate(moveDestination)) {
                
                if (firstColumnException(moveDestination, moveCurrent) || eightColumnException(moveDestination, moveCurrent)) {
                    break;
                }

                moveDestination += moveCurrent;

                Move legalMove = tryCreateMove(board, moveDestination);
                if(legalMove != null){
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
        return PieceName.BISHOP.toString();
    }

    private boolean firstColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.FIRST_COLUMN[moveCurrent] && (moveOutOfBounds == TOP_LEFT || moveOutOfBounds == BOTTOM_LEFT);
    }

    private boolean eightColumnException(int moveCurrent, int moveOutOfBounds) {
        return Board.EIGHT_COLUMN[moveCurrent] && (moveOutOfBounds == BOTTOM_RIGHT || moveOutOfBounds == TOP_RIGHT);
    }

    @Override
    public Piece getMovedPiece(int position, Color color) {
        return new Bishop(position, color, false);
    }

}
