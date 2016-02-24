package com.dev.mcp.matthew.bigtictactoe.Helpers;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Enums.ComputerPlayerType;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayerEasy;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayerHard;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayerMedium;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayerRandom;

public class ComputerPlayerHelper {

    public static IComputerPlayer CreateComputerPlayer(ComputerPlayerType computerPlayerType) {
        switch (computerPlayerType) {
            case Easy: {
                return new ComputerPlayerEasy(CellState.XMark);
            }
            case Medium: {
                return new ComputerPlayerMedium(CellState.XMark);
            }
            case Hard: {
                return new ComputerPlayerHard(CellState.XMark);
            }
            default:
                return new ComputerPlayerRandom(CellState.XMark);
        }
    }
}
