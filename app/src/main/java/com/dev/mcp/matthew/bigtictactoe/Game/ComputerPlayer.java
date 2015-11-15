package com.dev.mcp.matthew.bigtictactoe.Game;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public class ComputerPlayer implements IComputerPlayer {
    @Override
    public int[] move(IBoard board, String marker) {
        return new int[0];
    }

    @Override
    public boolean hasWon(String player) {
        return false;
    }
}
