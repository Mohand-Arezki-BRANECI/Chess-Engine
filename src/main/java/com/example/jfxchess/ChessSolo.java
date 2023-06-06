package com.example.jfxchess;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.player.MoveBoard;
import com.example.jfxchess.player.ai.Minimax;
import com.example.jfxchess.player.ai.Strategy;

import java.util.Objects;

public class ChessSolo {

    public static void main(String[] args) {
        Board board = Board.initialBoard();
        System.out.println(board);
        int count = 0;
        Strategy strategy = new Minimax(3);
        Strategy strategy2 = new Minimax(1);
        while (!board.currentPlayer().inCheckMate()) {
            count++;
            
            Move calculatedMove = strategy.execute(board);
            MoveBoard move = board.currentPlayer().makeMove(calculatedMove);
            board = move.getToBoard();

            System.out.println("Move : " + count +" -----------------");
            System.out.println(board);

            Move calculatedMove2 = strategy2.execute(board);
            MoveBoard move2 = board.currentPlayer().makeMove(calculatedMove2);
            board = move2.getToBoard();

            System.out.println("Move : " + count +" -----------------");
            System.out.println(board);
            
        }

        if (Objects.equals(EvaluateWinner.winnerOfTheGame(board), "Black")) {
            System.out.println("Black won !");
        } else if (Objects.equals(EvaluateWinner.winnerOfTheGame(board), "White")) {
            System.out.println("White won");
        } else if (Objects.equals(EvaluateWinner.winnerOfTheGame(board), "Tie")) {
            System.out.println("Tie");
        }
    }

}
