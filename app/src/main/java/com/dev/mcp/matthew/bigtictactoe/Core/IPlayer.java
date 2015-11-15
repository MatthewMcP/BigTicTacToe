package com.dev.mcp.matthew.bigtictactoe.Core;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public interface IPlayer {

    int[] move(IBoard board, String marker);

    boolean hasWon(String player);
}
