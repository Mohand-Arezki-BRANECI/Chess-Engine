package com.example.jfxchess.player.ai;

import com.example.jfxchess.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
