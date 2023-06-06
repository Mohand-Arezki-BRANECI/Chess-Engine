package com.example.jfxchess.player;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public class MoveBoard {

    private final Board newBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveBoard(Board newBoard, Move move, MoveStatus moveStatus) {
        this.newBoard = newBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getToBoard() {
        return this.newBoard;
   }

}
