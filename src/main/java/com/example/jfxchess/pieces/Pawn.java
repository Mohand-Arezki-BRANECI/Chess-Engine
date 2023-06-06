package com.example.jfxchess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.board.PawnPromotionMove;
import com.example.jfxchess.board.Tile;
import com.example.jfxchess.board.Move.MovePawn;
import com.example.jfxchess.board.Move.MovePawnJump;
import com.example.jfxchess.board.MoveAttack.MovePawnAttack;

public class Pawn extends Piece {

    public Pawn(int piecePosition, Color pieceColor, boolean firstMove) {
        super(PieceName.PAWN, piecePosition, pieceColor, firstMove);
    }

    private static final int FORWARD = 8;
    private static final int JUMP = 16;
    private static final int LEFT_ATTACK_W = 7;
    private static final int RIGHT_ATTACK_W = 9; 

    int[] movePossible = { FORWARD, JUMP, LEFT_ATTACK_W, RIGHT_ATTACK_W };
    int moveDestination;

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveCurrent : movePossible) {
            moveDestination = this.piecePosition + (this.getPieceColor().getDirection() * moveCurrent);

            if (!Board.isValidCoordinate(moveDestination)) {
                continue;
            }

            Tile moveDestinationTile = board.getTile(moveDestination);
            if (moveCurrent == FORWARD && !moveDestinationTile.isTileOccupied()) {

                if (this.pieceColor.isTileForPawnPromotion(moveDestination)) {
                    legalMoves.add(new PawnPromotionMove(new MovePawn(board, this, moveDestination)));
                } else {
                    legalMoves.add(new MovePawn(board, this, moveDestination));
                }

            } else if (moveCurrent == JUMP && firstMove()
                    && ((Board.SECOND_ROW[this.piecePosition] && this.pieceColor == Color.BLACK) ||
                            (Board.SEVEN_ROW[this.piecePosition] && this.pieceColor == Color.WHITE))) {

                int middleMove = this.piecePosition + (this.getPieceColor().getDirection() * FORWARD);
                Tile middleMoveTile = board.getTile(middleMove);

                if (!middleMoveTile.isTileOccupied() && !moveDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MovePawnJump(board, this, moveDestination));
                }

            } else if (moveCurrent == LEFT_ATTACK_W
                    && ((Board.EIGHT_COLUMN[this.piecePosition] && this.pieceColor == Color.WHITE) ||
                            (Board.FIRST_COLUMN[this.piecePosition] && this.pieceColor == Color.BLACK))) {
                if (moveDestinationTile.isTileOccupied()) {
                    if (this.pieceColor !=  pieceColor) {

                        if (this.pieceColor.isTileForPawnPromotion(moveDestination)) {
                            legalMoves.add(new PawnPromotionMove(
                                    new MovePawnAttack(board, this, moveDestination, moveDestinationTile.getPiece())));
                        } else {
                            legalMoves.add(
                                    new MovePawnAttack(board, this, moveDestination, moveDestinationTile.getPiece()));
                        }
                    }
                }

            } else if (moveCurrent == RIGHT_ATTACK_W && ((Board.EIGHT_COLUMN[this.piecePosition] && this.pieceColor == Color.BLACK))
                    ||
                    (Board.FIRST_COLUMN[this.piecePosition] && this.pieceColor == Color.WHITE)) {
                if (moveDestinationTile.isTileOccupied()) {
                    if (this.pieceColor !=  pieceColor) {
                        if (this.pieceColor.isTileForPawnPromotion(moveDestination)) {
                            legalMoves.add(new PawnPromotionMove(
                                    new MovePawnAttack(board, this, moveDestination, moveDestinationTile.getPiece())));
                        } else {
                            legalMoves.add(
                                    new MovePawnAttack(board, this, moveDestination, moveDestinationTile.getPiece()));
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceName.PAWN.toString();
    }

    public Object getPromotedPiece(int position, Color color) {
        return new Queen(position, color, false, true);
    }

    @Override
    public Piece getMovedPiece(int position, Color color) {
        return new Pawn(position, color, false);
    }
}
