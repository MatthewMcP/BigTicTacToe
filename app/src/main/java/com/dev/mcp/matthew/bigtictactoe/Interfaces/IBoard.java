package com.dev.mcp.matthew.bigtictactoe.Interfaces;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;

public interface IBoard {

    void clear();

    CellState[][] getBoard();

    void placeMark(Point markLoc, CellState mark);

    boolean isWinner();
}
