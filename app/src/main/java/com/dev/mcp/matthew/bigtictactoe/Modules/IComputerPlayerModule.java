package com.dev.mcp.matthew.bigtictactoe.Modules;

import com.dev.mcp.matthew.bigtictactoe.Core.IComputerPlayer;
import com.dev.mcp.matthew.bigtictactoe.Enums.CellState;
import com.dev.mcp.matthew.bigtictactoe.Game.ComputerPlayerHard;

import dagger.Module;
import dagger.Provides;

@Module
public class IComputerPlayerModule {

    @Provides
    IComputerPlayer provideComputerPlayer() {
        return new ComputerPlayerHard(CellState.OMark);
    }
}
