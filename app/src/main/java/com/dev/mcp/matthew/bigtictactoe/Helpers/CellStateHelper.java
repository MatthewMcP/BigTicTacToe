package com.dev.mcp.matthew.bigtictactoe.Helpers;

import android.content.Context;

import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.R;

public class CellStateHelper {

    private String XConstString;
    private String OConstString;

    public CellStateHelper(Context context) {
        XConstString = context.getResources().getString(R.string.XConst);
        OConstString = context.getResources().getString(R.string.OConst);
    }


    public CellState StringToCellState(String text) {
        if (text.equals(XConstString)) {
            return CellState.XMark;
        }
        if (text.equals(OConstString)) {
            return CellState.OMark;
        }
        return CellState.Empty;
    }

    public String CellStateToString(CellState cellState) {
        if (cellState == CellState.XMark) {
            return XConstString;
        }
        if (cellState == CellState.OMark) {
            return OConstString;
        }
        return "";
    }
}
