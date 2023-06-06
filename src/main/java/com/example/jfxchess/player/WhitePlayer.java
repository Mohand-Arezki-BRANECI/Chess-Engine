package com.example.jfxchess.player;

import java.util.Collection;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

import com.example.jfxchess.pieces.Piece;

public class WhitePlayer extends Player {

    public WhitePlayer(Board board, Collection<Move> whiteMoves, Collection<Move> blackMoves) {
        super(board, whiteMoves, blackMoves);
    }

    @Override
    public String toString() {
        return "White";
    }

    @Override
    public Collection<Piece> getPieceOnBoard() {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }


    public static final int ROOK_TILE_CASTLE = 61;
    public static final int KING_TILE_CASTLE = 62;
    public static final int ROOK_TILE = 63;
    public static final int ROOK_TILE_CASTLE_QUEEN = 59;
    public static final int KING_TILE_CASTLE_QUEEN = 58;
    public static final int VOID_TILE_CASTLE_QUEEN = 57;
    public static final int ROOK_TILE_QUEEN_SIDE = 56;

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