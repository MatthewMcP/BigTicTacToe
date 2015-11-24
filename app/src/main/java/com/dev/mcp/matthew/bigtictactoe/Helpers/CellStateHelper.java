package com.dev.mcp.matthew.bigtictactoe.Helpers;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;

public class CellStateHelper {

    public static CellState StringToCellState(String text) {
        if (text.equals("O")) {
            return CellState.XMark;
        }
        if (text.equals("X")) {
            return CellState.OMark;
        }
        return CellState.Empty;
    }

    public static String CellStateToString(CellState cellState) {
        if (cellState == CellState.XMark) {
            return "X";
        }
        if (cellState == CellState.OMark) {
            return "O";
        }
        return "";
    }
}
