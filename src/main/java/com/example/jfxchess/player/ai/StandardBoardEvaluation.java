package com.example.jfxchess.player.ai;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.pieces.Piece;
import com.example.jfxchess.player.Player;

public class StandardBoardEvaluation implements BoardEvaluator {

    private static final int CHECK_BONUS = 60;
    private static final int CHECK_MATE_BONUS = 100000;
    private static final int DEPTH_BONUS = 150;
    private static final int CASTLE_BONUS = 100;
    private static int evalCount = 0;

    public static StandardBoardEvaluation get() {
        return new StandardBoardEvaluation();
    }

    @Override
    public int evaluate(Board board, int depth) {
        ++evalCount;
        //if ((evalCount % 100) == 0) {
        //    System.out.println("[ Move evaluation numero ] [" + evalCount + "]");
        //}

        return scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(Board board, Player player, int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkmate(player, depth) + castled(player);
    }

    private int castled(Player player) {
        return player.getKing().isCastled() ? CASTLE_BONUS : 0;
    }

    private int checkmate(Player player, int depth) {
        return player.getOpponent().inCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private int check(Player player) {
        return player.getOpponent().inCheck() ? CHECK_BONUS : 0;
    }

    private int mobility(Player player) {
        return player.getLegalMoves().size();
    }

    private int pieceValue(Player player) {
        int pieceValueScore = 0;
        for (Piece piece : player.getPieceOnBoard()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }

}