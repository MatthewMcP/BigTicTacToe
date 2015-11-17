package com.dev.mcp.matthew.bigtictactoe.Game;

import android.graphics.Point;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Interfaces.IBoard;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer implements IComputerPlayer {
    protected CellState myMark;
    protected CellState[][] board;

    public ComputerPlayer(CellState marker) {
        myMark = marker;
    }

    public Point getMove(IBoard board) {
        this.board = board.getBoard();
        int[] result = minmax(2, myMark, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return new Point(result[1], result[2]);
    }


    //This could probably be improved but I'm not interested in fixing a complex AI.
    //Leaving untouched for now. One day...
    private int[] minmax(int depth, CellState player, int alpha, int beta) {
        List<int[]> nextMoves = generateMoves();
        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Game over or depth reached, evaluate score
            score = evaluate();
            return new int[]{score, bestRow, bestCol};
        } else {
            for (int[] move : nextMoves) {
                // try this move for the current "player"
                board[move[0]][move[1]] = player;
                if (player == myMark) { // mySeed (computer) is maximizing
                    // player
                    if (myMark == CellState.XMark) {
                        score = minmax(depth - 1, CellState.OMark, alpha, beta)[0];
                    } else {
                        score = minmax(depth - 1, CellState.XMark, alpha, beta)[0];
                    }
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else { // oppSeed is minimizing player
                    score = minmax(depth - 1, myMark, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // undo move
                board[move[0]][move[1]] = CellState.Empty;
                // cut-off
                if (alpha >= beta)
                    break;
            }
        }
        return new int[]{(player == myMark) ? alpha : beta, bestRow, bestCol};
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();

        // Search for empty cells and add to the List
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                if (board[row][col] == CellState.Empty) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }

    /**
     * The heuristic evaluation function for the current board
     * <p/>
     * +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer. -100,
     * -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent. 0 otherwise
     */
    private int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2
        // diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2); // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2); // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2); // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0); // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1); // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2); // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2); // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0); // alternate diagonal
        return score;
    }

    /**
     * The heuristic evaluation function for the given line of 3 cells
     * <p/>
     * +100, +10, +1 for 3-, 2-, 1-in-a-line for computer. -100, -10, -1
     * for 3-, 2-, 1-in-a-line for opponent. 0 otherwise
     */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (board[row1][col1] == myMark) {
            score = 1;
        } else if (board[row1][col1] != CellState.Empty) {
            score = -1;
        }

        // Second cell
        if (board[row2][col2] == myMark) {
            if (score == 1) { // cell1 is mySeed
                score = 10;
            } else if (score == -1) { // cell1 is oppSeed
                return 0;
            } else { // cell1 is empty
                score = 1;
            }
        } else if (board[row2][col2] != CellState.Empty) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else { // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (board[row3][col3] == myMark) {
            if (score > 0) { // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) { // cell1 and/or cell2 is oppSeed
                return 0;
            } else { // cell1 and cell2 are empty
                score = 1;
            }
        } else if (board[row3][col3] != CellState.Empty) {
            if (score < 0) { // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) { // cell1 and/or cell2 is mySeed
                return 0;
            } else { // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }

}
