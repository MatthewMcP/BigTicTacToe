package com.dev.mcp.matthew.bigtictactoe.Core;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public interface IPlayer {

    Point getMove(IBoard board);
}
