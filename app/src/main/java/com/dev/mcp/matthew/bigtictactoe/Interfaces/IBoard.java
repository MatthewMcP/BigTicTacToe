package com.dev.mcp.matthew.bigtictactoe.Interfaces;

public interface IBoard {

    void clear();

    String[][] getBoard();

    void placeMark(int xloc, int yloc, String mark);

    boolean isWinner();
}
