package com.example.jfxchess.board;

public class MoveCreator {

    private MoveCreator() {}

    public static Move createMove(Board board, int currentCoordinate, int moveDestination) {
        for (Move move : board.getCurrentAllLegalMoves()) {
            if(move.getCurrentCoordinate() == currentCoordinate && move.getPieceDestination() == moveDestination) {
                return move;
            }
        }
        return null;
    }
}
