package com.dev.mcp.matthew.bigtictactoe.Game;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

public class Board implements IBoard {

    private CellState[][] board = new CellState[3][3];

    public Board() {
        SetBoardAsEmpty();
    }

    public void clear() {
        SetBoardAsEmpty();
    }

    private void SetBoardAsEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = CellState.Empty;
            }
        }
    }

    public CellState[][] getBoard() {
        return board;
    }

    public void placeMark(Point point, CellState mark) {
        if (board[point.x][point.y] == CellState.Empty) {
            board[point.x][point.y] = mark;
        }
    }

    public boolean isWinner() {
        if (RowWinner() || ColumnWinner() || DiagonalWinner()) {
            return true;
        }

        return false;
    }

    private boolean RowWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != CellState.Empty && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    private boolean ColumnWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != CellState.Empty && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean DiagonalWinner() {
        if (board[0][0] != CellState.Empty && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true;
        } else if (board[2][0] != CellState.Empty && board[2][0] == board[1][1] && board[2][0] == board[0][2]) {
            return true;
        }
        return false;
    }
}
