package com.example.jfxchess.player.ai;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.player.MoveBoard;
import com.example.jfxchess.player.MoveStatus;

public class Minimax implements Strategy {

    private final BoardEvaluator boardEvaluator;
    private final int depth;

    public Minimax(int depth) {
        this.boardEvaluator = new StandardBoardEvaluation();
        this.depth = depth;
    }

    @Override
    public Move execute(Board board) {

        Move bestMove = null;

        int highestScoreValue = Integer.MIN_VALUE;
        int lowestScoreValue = Integer.MAX_VALUE;
        int currValue;

        for (Move move : board.currentPlayer().getLegalMoves()) {
            MoveBoard moveBoard = board.currentPlayer().makeMove(move);

            if (moveBoard.getMoveStatus() == MoveStatus.DONE) {

                currValue = board.currentPlayer().getColor() == Color.WHITE ? min(moveBoard.getToBoard(), depth - 1)
                        : max(moveBoard.getToBoard(), depth - 1);

                if (board.currentPlayer().getColor() == Color.WHITE && currValue >= highestScoreValue) {
                    highestScoreValue = currValue;
                    bestMove = move;
                } else if (board.currentPlayer().getColor() == Color.BLACK && currValue <= lowestScoreValue) {
                    lowestScoreValue = currValue;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }

    public int min(Board board, int depth) {
        if (depth == 0) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestScoreValue = Integer.MAX_VALUE;
        for (Move move : board.currentPlayer().getLegalMoves()) {
            MoveBoard moveBoard = board.currentPlayer().makeMove(move);

            if (moveBoard.getMoveStatus() == MoveStatus.DONE) {
                int currValue = max(moveBoard.getToBoard(), depth - 1);

                if (currValue <= lowestScoreValue) {
                    lowestScoreValue = currValue;
                }
            }
        }

        return lowestScoreValue;
    }

    public int max(Board board, int depth) {
        if (depth == 0 || isEndGame(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestScoreValue = Integer.MIN_VALUE;
        for (Move move : board.currentPlayer().getLegalMoves()) {
            MoveBoard moveBoard = board.currentPlayer().makeMove(move);

            if (moveBoard.getMoveStatus() == MoveStatus.DONE) {
                int currValue = min(moveBoard.getToBoard(), depth - 1);

                if (currValue >= highestScoreValue) {
                    highestScoreValue = currValue;
                }
            }
        }

        return highestScoreValue;
    }

    private boolean isEndGame(Board board) {
        return board.currentPlayer().inCheckMate() || board.currentPlayer().inStaleMate();
    }

    @Override
    public String toString() {
        return "Minimax";
    }
}
