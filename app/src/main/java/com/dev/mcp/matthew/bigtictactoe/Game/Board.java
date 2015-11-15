package com.dev.mcp.matthew.bigtictactoe.Game;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public class Board implements IBoard {

    //Declare the global variables
    private String[][] theBoard = new String[3][3];

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                theBoard[i][j] = "";
            }
        }
    }

    public void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                theBoard[i][j] = "";
            }
        }
    }

    public String[][] getBoard() {
        return theBoard;
    }

    public void placeMark(int xloc, int yloc, String mark) {
        if (theBoard[xloc][yloc] == "") theBoard[xloc][yloc] = mark;
    }

    public boolean isWinner() {
        if (theBoard[0][0] == theBoard[1][1] && theBoard[0][0] == theBoard[2][2] && theBoard[0][0] != "")
            return true;
        if (theBoard[2][0] == theBoard[1][1] && theBoard[2][0] == theBoard[0][2] && theBoard[2][0] != "")
            return true;
        for (int i = 0; i < 3; i++) {
            if (theBoard[i][0] == theBoard[i][1] && theBoard[i][1] == theBoard[i][2] && theBoard[i][0] != "")
                return true;
            if (theBoard[0][i] == theBoard[1][i] && theBoard[1][i] == theBoard[2][i] && theBoard[0][i] != "")
                return true;
        }
        return false;
    }
}
