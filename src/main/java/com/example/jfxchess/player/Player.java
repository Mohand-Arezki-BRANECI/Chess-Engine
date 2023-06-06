package com.example.jfxchess.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.jfxchess.Color;
import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;
import com.example.jfxchess.board.Tile;
import com.example.jfxchess.board.Move.KingSideCastle;
import com.example.jfxchess.board.Move.QueenSideCastle;
import com.example.jfxchess.pieces.King;
import com.example.jfxchess.pieces.Queen;
import com.example.jfxchess.pieces.Piece;
import com.example.jfxchess.pieces.Rook;
import com.example.jfxchess.pieces.Piece.PieceName;

public abstract class Player {
    protected Board board;
    protected King king;
    private final Collection<Move> legalMoves;
    private final boolean inCheck;
    public int countCheck = 0;

    protected Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        List<Move> allLegalMoves = new ArrayList<>();
        this.board = board;
        this.legalMoves = allLegalMoves;
        this.king = findKing();
        this.inCheck = !Player.possiblesAttacksMovesOnPosition(this.king.getPiecePosition(), opponentMoves).isEmpty();
        allLegalMoves.addAll(legalMoves);
        allLegalMoves.addAll(calculateKingCastle(legalMoves, opponentMoves));
        countCheck = this.inCheck() ? countCheck =+ 1 : countCheck;
    }


    protected static Collection<Move> possiblesAttacksMovesOnPosition(Integer piecePosition,
            Collection<Move> opponentMoves) {
        List<Move> attackMoves = new ArrayList<>();

        for (Move move : opponentMoves) {
            if (piecePosition == move.getPieceDestination()) {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    private King findKing() {
        for (Piece piece : getPieceOnBoard()) {
            if (piece.getPieceType() == PieceName.KING) {
                return (King) piece;
            }
        }
        throw new IllegalArgumentException("Board not valid !");
    }

    public King getKing() {
        return this.king;
    }

    public Queen getQueen() {
        for (Piece piece : getPieceOnBoard()) {
            if (piece.getPieceType() == PieceName.QUEEN) {
                return (Queen) piece;
            }
        }
        return null;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public abstract Collection<Piece> getPieceOnBoard();

    public abstract Color getColor();

    public abstract Player getOpponent();

    public abstract int rookTileCastle();

    public abstract int kingTileCastle();

    public abstract int rookTile();

    public abstract int rookTileCastleQueen();

    public abstract int kingTileCastleQueen();

    public abstract int voidTileCastleQueen();

    public abstract int rookTileQueenSide();

    protected Collection<Move> calculateKingCastle(Collection<Move> playerLegalMoves,
            Collection<Move> opponentLegalMoves) {
        List<Move> kingCastle = new ArrayList<>();

        Tile rookTile = this.board.getTile(rookTile());
        if (this.king.firstMove() &&
                !this.inCheck() &&
                rookTile.isTileOccupied() &&
                !this.board.getTile(rookTileCastle()).isTileOccupied() &&
                !this.board.getTile(kingTileCastle()).isTileOccupied() &&
                Player.possiblesAttacksMovesOnPosition(rookTileCastle(), opponentLegalMoves).isEmpty()
                && Player.possiblesAttacksMovesOnPosition(kingTileCastle(), opponentLegalMoves).isEmpty() &&
                rookTile.getPiece().getPieceType() == PieceName.ROOK) {

            kingCastle.add(new KingSideCastle(this.board, this.king, kingTileCastle(), (Rook) rookTile.getPiece(),
                    rookTile.getTileCoordinate(), rookTileCastle()));
        }

        Tile rookTileQueen = this.board.getTile(rookTileQueenSide());
        if (!this.board.getTile(rookTileCastleQueen()).isTileOccupied() &&
                !this.board.getTile(kingTileCastleQueen()).isTileOccupied() &&
                !this.board.getTile(voidTileCastleQueen()).isTileOccupied() &&
                rookTileQueen.isTileOccupied() &&
                rookTileQueen.getPiece().firstMove()) {
            kingCastle.add(
                    new QueenSideCastle(this.board, this.king, kingTileCastleQueen(), (Rook) rookTileQueen.getPiece(),
                            rookTileQueen.getTileCoordinate(), rookTileCastleQueen()));
        }

        return kingCastle;
    }

    public boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean inCheck() {
        return this.inCheck;
    }

    public int countCheck() {
        return this.countCheck;
    }

    public boolean inCheckMate() {
        return this.inCheck && !escapeMoves();
    }

    public boolean inStaleMate() {
        return !this.inCheck && !escapeMoves();
    }

    private boolean escapeMoves() {
        for (Move move : this.legalMoves) {
            MoveBoard boardMove = makeMove(move);
            if (boardMove.getMoveStatus() == MoveStatus.DONE) {
                return true;
            }
        }
        return false;
    }

    public MoveBoard makeMove(Move move) {
        if (move == null || !isMoveLegal(move)) {
            return new MoveBoard(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        Board boardAfterMove = move.execute();

        Collection<Move> kingAttack = Player.possiblesAttacksMovesOnPosition(
                boardAfterMove.currentPlayer().getOpponent().getKing().getPiecePosition(),
                boardAfterMove.currentPlayer().getLegalMoves());

        if (!kingAttack.isEmpty()) {
            return new MoveBoard(boardAfterMove, move, MoveStatus.LEAVE_CHECK);
        }

        return new MoveBoard(boardAfterMove, move, MoveStatus.DONE);
    }

    

}
