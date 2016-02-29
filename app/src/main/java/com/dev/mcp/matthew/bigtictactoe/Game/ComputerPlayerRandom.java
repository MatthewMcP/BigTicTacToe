package com.dev.mcp.matthew.bigtictactoe.Game;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Interfaces.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.Helpers.App;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;
import com.dev.mcp.matthew.bigtictactoe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayerRandom implements IComputerPlayer {
    protected CellState myMark;
    protected CellState[][] board;

    public ComputerPlayerRandom(CellState marker) {
        myMark = marker;
    }

    public Point getMove(IBoard board) {
        this.board = board.getBoard();
        List<int[]> emptyCells = GetEmptyCells();

        int[] result = emptyCells.get(new Random().nextInt(emptyCells.size()));

        return new Point(result[0], result[1]);
    }

    public ComputerPlayerType getPlayerType() {
        return ComputerPlayerType.Random;
    }

    @Override
    public int getComputerMark(CellState mark) {
        if (mark == CellState.XMark) {
            return R.drawable.cross_ai_random;
        }
        return R.drawable.circle_ai_random;
    }

    @Override
    public String getName() {
        return App.getContext().getResources().getString(R.string.computername_random);
    }

    private List<int[]> GetEmptyCells() {
        List<int[]> nextMoves = new ArrayList<>();

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                if (board[row][col] == CellState.Empty) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }
}
