package com.example.jfxchess;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.player.BlackPlayer;
import com.example.jfxchess.player.Player;
import com.example.jfxchess.player.WhitePlayer;

public enum Color {
    WHITE {
        @Override
        public int getDirection() {
            // TODO Auto-generated method stub
            return -1;
        }

        @Override
        public Player pickPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            // TODO Auto-generated method stub
            return whitePlayer;
        }

        @Override
        public boolean isTileForPawnPromotion(int position) {
            // TODO Auto-generated method stub
            return Board.FIRST_ROW[position];
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            // TODO Auto-generated method stub
            return 1;
        }

        @Override
        public Player pickPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            // TODO Auto-generated method stub
            return blackPlayer;
        }

        @Override
        public boolean isTileForPawnPromotion(int position) {
            // TODO Auto-generated method stub
            return Board.EIGHT_ROW[position];
        }
    };

    public abstract int getDirection();
    public abstract boolean isTileForPawnPromotion(int position); 

    public abstract Player pickPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

}
