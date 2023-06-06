package com.example.jfxchess.player.ai;

import com.example.jfxchess.board.Board;
import com.example.jfxchess.board.Move;

public interface Strategy {
 
    Move execute(Board board);

}
