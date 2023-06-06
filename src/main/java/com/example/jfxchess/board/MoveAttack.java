package com.example.jfxchess.board;

import com.example.jfxchess.pieces.Piece;

public class MoveAttack extends Move {

    Piece attackPiece;

    public MoveAttack(Board board,  Piece movePiece, int pieceDestination, Piece attackPiece) {
        super(board, pieceDestination, movePiece);
        this.attackPiece = attackPiece;
    }

    @Override
    public boolean isAttack() {
        return true;
    }

    @Override
    public Piece getPieceAttack() {
        return this.attackPiece;
    }


    public static class MovePawnAttack extends MoveAttack {

        public MovePawnAttack(Board board, Piece movePiece, int pieceDestination, Piece attackPiece) {
            super(board, movePiece, pieceDestination, attackPiece); 
        }
    }
}
