package com.dev.mcp.matthew.bigtictactoe.Core;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public interface IComputerPlayer extends IPlayer {

    Point getMove(IBoard board);

    ComputerPlayerType getPlayerType();

}
