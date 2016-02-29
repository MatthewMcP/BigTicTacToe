package com.dev.mcp.matthew.bigtictactoe.Interfaces;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;

public interface IComputerPlayer extends IPlayer {

    Point getMove(IBoard board);

    ComputerPlayerType getPlayerType();

    int getComputerMark(CellState mark);

}
