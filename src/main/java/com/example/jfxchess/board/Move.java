package com.example.jfxchess.board;

import com.example.jfxchess.board.Board.Builder;
import com.example.jfxchess.pieces.King;
import com.example.jfxchess.pieces.Pawn;
import com.example.jfxchess.pieces.Piece;
import com.example.jfxchess.pieces.Rook;

public abstract class Move {

    Board board;
    int pieceDestination;
    Piece movePiece;

    protected Move(Board board, int pieceDestination, Piece movePiece) {
        this.board = board;
        this.pieceDestination = pieceDestination;
        this.movePiece = movePiece;
    }

    @Override
    public String toString() {
        return Integer.toString(pieceDestination);
    }

    public int getPieceDestination() {
        return this.pieceDestination;
    }

    public Piece getPieceMove() {
        return this.movePiece;
    }

    public int getCurrentCoordinate() {
        return this.getPieceMove().getPiecePosition();
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastledMove() {
        return false;
    }

    public Piece getPieceAttack() {
        return null;
    }

    public Board execute() {
        Builder builder = new Builder();
        for (Piece piece : this.board.currentPlayer().getPieceOnBoard()) {
            if (!this.movePiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : this.board.currentPlayer().getOpponent().getPieceOnBoard()) {
            builder.setPiece(piece);
        }
        builder.setPiece(movePiece.getMovedPiece(this.pieceDestination, this.board.currentPlayer().getColor()));
        builder.setMoveSide(this.board.currentPlayer().getOpponent().getColor());
        return builder.build();
    }

    public static class MovePawn extends Move {

        public MovePawn(Board board, Piece movePiece, int pieceDestination) {
            super(board, pieceDestination, movePiece);
        }
    }

    public static class MovePawnJump extends Move {

        public MovePawnJump(Board board, Piece movePiece, int pieceDestination) {
            super(board, pieceDestination, movePiece);
        }

        @Override
        public Board execute() {
            Builder builder = new Builder();
            for (Piece piece : this.board.currentPlayer().getPieceOnBoard()) {
                if (!this.movePiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (Piece piece : this.board.currentPlayer().getOpponent().getPieceOnBoard()) {
                builder.setPiece(piece);
            }
            Pawn movePawn = (Pawn) this.movePiece;
            builder.setPiece(movePawn.getMovedPiece(this.pieceDestination, this.board.currentPlayer().getColor()));
            builder.setMoveSide(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }
    }

    public static abstract class CastleMove extends Move {

        protected Rook castleRook;
        protected int castleRookPosition;
        protected int castleRookDestination;

        public CastleMove(Board board, Piece movePiece, int pieceDestination, Rook castleRook, int castleRookPosition,
                int castleRookDestination) {
            super(board, pieceDestination, movePiece);
            this.castleRook = castleRook;
            this.castleRookPosition = castleRookPosition;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastledMove() {
            return true;
        }

        @Override
        public Board execute() {
            Builder builder = new Builder();

            for (Piece piece : this.board.currentPlayer().getPieceOnBoard()) {
                if (!this.movePiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (Piece piece : this.board.currentPlayer().getOpponent().getPieceOnBoard()) {
                builder.setPiece(piece);
            }
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceColor(), false));
            builder.setPiece(new King(this.movePiece.getPiecePosition() + stepCastle(), this.movePiece.getPieceColor(), false, true));
            // builder.setPiece(movePiece.getMovedPiece(this.movePiece.getPiecePosition() + stepCastle(), this.movePiece.getPieceColor()));
            builder.setMoveSide(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

        public abstract int stepCastle();
    }

    public static class KingSideCastle extends CastleMove {

        public KingSideCastle(Board board, Piece movePiece, int pieceDestination, Rook castleRook,
                int castleRookPosition, int castleRookDestination) {
            super(board, movePiece, pieceDestination, castleRook, castleRookPosition, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O";
        }

        @Override
        public int stepCastle() {
            return 2;
        }
    }

    public static class QueenSideCastle extends CastleMove {

        public QueenSideCastle(Board board, Piece movePiece, int pieceDestination, Rook castleRook,
                int castleRookPosition, int castleRookDestination) {
            super(board, movePiece, pieceDestination, castleRook, castleRookPosition, castleRookDestination);

        }

        @Override
        public String toString() {
            return "O-O-O";
        }
        @Override
        public int stepCastle() {
            return -2;
        }
    }


    public Board getBoard() {
        return this.board;
    }

}
