package com.example.jfxchess.player;

import java.util.Collection;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.pieces.Piece;

public class BlackPlayer extends Player {

    public BlackPlayer(Board board, Collection<Move> whiteMoves, Collection<Move> blackMoves) {
        super(board, blackMoves, whiteMoves);
    }

    @Override
    public Collection<Piece> getPieceOnBoard() {
        return this.board.getBlackPieces();
    }

    @Override
    public String toString() {
        return "Black";
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    public static final int ROOK_TILE_CASTLE = 5;
    public static final int KING_TILE_CASTLE = 6;
    public static final int ROOK_TILE = 7;
    public static final int ROOK_TILE_CASTLE_QUEEN = 3;
    public static final int KING_TILE_CASTLE_QUEEN = 2;
    public static final int VOID_TILE_CASTLE_QUEEN = 1;
    public static final int ROOK_TILE_QUEEN_SIDE = 0;

    @Override
    public int rookTileCastle() {
        return ROOK_TILE_CASTLE;
    }

    @Override
    public int kingTileCastle() {
        return KING_TILE_CASTLE;
    }

    @Override
    public int rookTile() {
        return ROOK_TILE;
    }

    @Override
    public int rookTileCastleQueen() {
        return ROOK_TILE_CASTLE_QUEEN;
    }

    @Override
    public int kingTileCastleQueen() {
        return KING_TILE_CASTLE_QUEEN;
    }

    @Override
    public int voidTileCastleQueen() {
        return VOID_TILE_CASTLE_QUEEN;
    }

    @Override
    public int rookTileQueenSide() {
        return ROOK_TILE_QUEEN_SIDE;
    }
}