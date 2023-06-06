package com.example.jfxchess.board;

import com.example.jfxchess.board.Board.Builder;
import com.example.jfxchess.pieces.Pawn;
import com.example.jfxchess.pieces.Piece;
import com.example.jfxchess.pieces.Queen;

public class PawnPromotionMove extends Move {

    Move promotionMove;
    Pawn promotedPawn;

    public PawnPromotionMove(Move promotionMove) {
        super(promotionMove.getBoard(), promotionMove.getPieceDestination(), promotionMove.getPieceMove());
        this.promotionMove = promotionMove;
        this.promotedPawn = (Pawn) getPieceMove();
    }

    @Override
    public Board execute() {
        Builder builder = new Builder();
        for (Piece piece : this.board.currentPlayer().getPieceOnBoard()) {
            if (!this.promotedPawn.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : this.board.currentPlayer().getOpponent().getPieceOnBoard()) {
            builder.setPiece(piece);
        }

       // builder.setPiece(movePiece.getMovedPiece(this.pieceDestination,this.board.currentPlayer().getColor()));
        
        builder.setPiece((Queen) promotedPawn.getPromotedPiece(this.pieceDestination,this.board.currentPlayer().getColor()));
        builder.setMoveSide(this.board.currentPlayer().getOpponent().getColor());
        return builder.build();

    }

    @Override
    public boolean isAttack() {
        return this.promotionMove.isAttack();
    }

    @Override
    public Piece getPieceAttack() {
        return this.promotionMove.getPieceAttack();
    }

    @Override
    public String toString() {
        return "";
    }

}
